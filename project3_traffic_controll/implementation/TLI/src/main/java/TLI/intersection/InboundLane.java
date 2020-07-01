package TLI.intersection;

import TLI.drawable.Size;
import TLI.movables.MovableEntity;
import TLI.position.CardinalDirection;
import TLI.position.Position;
import TLI.trafficlight.TrafficLight;

public class InboundLane extends Travelable {

    private TrafficLight trafficLight;

    public InboundLane(Position position, Size size, int speedLimit, TrafficLight trafficLight, CardinalDirection facing) {
        super(position,size, speedLimit, facing, true);
        this.trafficLight = trafficLight;
        System.out.println(trafficLight);
    }

    @Override
    public void addEntity(MovableEntity toAdd) {
        super.movableEntities.put(toAdd, toAdd.getData().getProgress());
        trafficLight.registerObserver(toAdd);
    }

    @Override
    public void removeEntity(MovableEntity toRemove) {
        super.movableEntities.remove(toRemove);
        trafficLight.removeObserver(toRemove);
    }

    @Override
    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public String toString() {
        return "InboundLane(" + hashCode() + "){" +
                "position=" + position +
                "facing=" + facing +
                " next lanes=" + nextLanes +
                '}';
    }
}