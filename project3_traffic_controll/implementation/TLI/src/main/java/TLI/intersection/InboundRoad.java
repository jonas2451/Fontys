package TLI.intersection;

import TLI.position.CardinalDirection;
import TLI.position.Position;
import TLI.trafficlight.TrafficLight;

import java.util.*;

/**
 * An object that models the entering of a crossing.
 * <p>
 * By default always three lanes are on an InboundLane.
 * If there is only one physical lane, the same Object will be included in all fields.
 */
public class InboundRoad {

    private List<InboundLane> left = new ArrayList<>();
    private List<InboundLane> center = new ArrayList<>();
    private List<InboundLane> right = new ArrayList<>();

    /**
     * Creates an InboundLane that doesn't have multiple lanes going in the same direction.
     *
     * @param left   CarLane to be added.
     * @param center CarLane to be added.
     * @param right  CarLane to be added.
     */
    public InboundRoad(InboundLane left, InboundLane center, InboundLane right) {
        if (Objects.nonNull(left)) this.left.add(left);
        if (Objects.nonNull(center)) this.center.add(center);
        if (Objects.nonNull(right)) this.right.add(right);
    }

    /**
     * @param center If only adding one {@link InboundLane} to this inbound road.
     */
    public InboundRoad(InboundLane center) {
        if (Objects.nonNull(center)) this.center.add(center);
    }

    public void addLeftLane(InboundLane lane) {
        this.left.add(lane);
    }

    public void addCenterLane(InboundLane lane) {
        this.center.add(lane);
    }

    public void addRightLane(InboundLane lane) {
        this.right.add(lane);
    }

    public List<InboundLane> getLeft() {
        return left;
    }

    public void setLeft(List<InboundLane> left) {
        this.left = left;
    }

    public List<InboundLane> getCenter() {
        return center;
    }

    public void setCenter(List<InboundLane> center) {
        this.center = center;
    }

    public List<InboundLane> getRight() {
        return right;
    }

    public void setRight(List<InboundLane> right) {
        this.right = right;
    }

    public TrafficLight getLeftLight() {
        return this.left.get(0).getTrafficLight();
    }

    public TrafficLight getCenterLight() {
        return this.center.get(0).getTrafficLight();
    }

    public TrafficLight getRightLight() {
        return this.right.get(0).getTrafficLight();
    }

    /**
     * @return the cardinal direction of one of the inboundLanes; null if the inboundRoad has no inboundLanes
     */
    public CardinalDirection getFacing() {
        if (!getLanes().isEmpty()) {
            return getLanes().get(0).getFacing();
        }
        return null;
    }

    /**
     *
     * @return the position of the most top/left element. (Smallest possible)
     */
    public Position getPosition() {
        if (!getLanes().isEmpty()) {
            Optional<InboundLane> o =  this.getLanes().stream()
                    .min(Comparator.comparing(Travelable::getPosition));
            if (o.isPresent()) {
                return o.get().getPosition();
            }
        }
        return null;
    }

    public List<InboundLane> getLanes() {
        List<InboundLane> lanes = new ArrayList<>(left);
        lanes.addAll(center);
        lanes.addAll(right);
        return lanes;
    }

    public Set<TrafficLight> getTrafficLights() {

        Set<TrafficLight> lights = new HashSet<>();

        for (InboundLane inboundLane : left) {
            if (Objects.nonNull(left)) {
                lights.add(inboundLane.getTrafficLight());
            }
        }

        for (InboundLane inboundLane : center) {
            if (Objects.nonNull(inboundLane)) {
                lights.add(inboundLane.getTrafficLight());
            }
        }

        for (InboundLane inboundLane : right) {
            if (Objects.nonNull(inboundLane)) {
                lights.add(inboundLane.getTrafficLight());
            }
        }


        return lights;
    }

    @Override
    public String toString() {
        return "InboundRoad{" +
                "\n left=" + left +
                ",\n center=" + center +
                ",\n right=" + right +
                "\n}\n";
    }
}
