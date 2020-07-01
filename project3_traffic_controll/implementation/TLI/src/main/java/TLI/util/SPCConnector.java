package TLI.util;

import TLI.drawable.Size;
import TLI.intersection.*;
import TLI.position.CardinalDirection;
import TLI.position.Position;

import java.util.Objects;

public class SPCConnector {

    /**
     * Connect two {@link SimplePedestrianCrossing}.
     *
     * @param left  the left {@link SimplePedestrianCrossing}
     * @param right the right {@link SimplePedestrianCrossing}
     * @return true if the {@link TLI.intersection.Intersection}s have been connected.
     */
    public static boolean connectIntersectionsHorizontally(SimplePedestrianCrossing left, SimplePedestrianCrossing right, int speedLimit) {

        Objects.requireNonNull(left);
        Objects.requireNonNull(right);

        //<editor-fold defaultstate="collapsed" desc="Bottom lane">
        InboundLane leftBottom = left.getWestInboundRoad().getCenter().get(0);
        InboundLane rightBottom = right.getWestInboundRoad().getCenter().get(0);

        Position bottomLanePosition = leftBottom.getPosition().addX((leftBottom.getSize().getLength()));

        CarLane nextLaneBottom =
                new CarLane(
                        bottomLanePosition,
                        new Size(rightBottom.getPosition().getX() - bottomLanePosition.getX(), leftBottom.getSize().getHeight()),
                        speedLimit,
                        leftBottom.getFacing()
                );

        leftBottom.addNextLane(Direction.STRAIGHT, nextLaneBottom);
        nextLaneBottom.addNextLane(Direction.STRAIGHT, rightBottom);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Top lane">
        InboundLane leftTop = left.getEastInboundRoad().getCenter().get(0);
        InboundLane rightTop = right.getEastInboundRoad().getCenter().get(0);

        Position TopLanePosition = leftTop.getPosition().addX((leftTop.getSize().getLength()));

        CarLane nextLaneTop =
                new CarLane(
                        TopLanePosition,
                        new Size(rightTop.getPosition().getX() - TopLanePosition.getX(), leftTop.getSize().getHeight()),
                        speedLimit,
                        leftTop.getFacing()
                );

        rightTop.addNextLane(Direction.STRAIGHT, nextLaneTop);
        nextLaneTop.addNextLane(Direction.STRAIGHT, leftTop);
        //</editor-fold>

        return true;
    }

    /**
     * @param travelable to connect to
     * @param position where to connect the new lane
     * @param speedLimit of the new lane
     * @return {@link Travelable} that connects to travelable
     */
    public static Travelable connectTravelablesHorizontally(Travelable travelable, Position position, int speedLimit) {
        Travelable lane;
        Size size;
        Position newPosition;

        size = new Size(Math.abs(travelable.getPosition().getX() - position.getX()), travelable.getSize().getHeight());

        //<editor-fold defaultstate="collapsed" desc="positioning and creating of lane">
        if ((travelable.getFacing() == CardinalDirection.EAST) && travelable.getPosition().getY() == position.getY()) {

            if (travelable.getPosition().getX() <= position.getX()) {
                // position right of travelable -> east
                newPosition = travelable.getPosition().addX(travelable.getSize().getLength());
                lane = new CarLane(newPosition, size, speedLimit, travelable.getFacing());
                travelable.addNextLane(Direction.STRAIGHT, lane);

                return lane;
            } else {
                // position left of travelable -> east
                newPosition = position;
            }

        } else if ((travelable.getFacing() == CardinalDirection.WEST) && travelable.getPosition().getY() == position.getY()) {

            if (travelable.getPosition().getX() <= position.getX()) {
                // position right of travelable -> west
                newPosition = travelable.getPosition().addX(travelable.getSize().getLength());
            } else {
                // position left of travelable -> west
                newPosition = position;
                lane = new CarLane(newPosition, size, speedLimit, travelable.getFacing());
                travelable.addNextLane(Direction.STRAIGHT, lane);
                return lane;
            }
        } else {
            return null;
        }

        lane = new CarLane(newPosition, size, speedLimit, travelable.getFacing());

        lane.addNextLane(Direction.STRAIGHT, travelable);
        return lane;
        //</editor-fold>
    }
}
