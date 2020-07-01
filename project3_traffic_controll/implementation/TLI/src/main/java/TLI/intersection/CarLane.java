package TLI.intersection;

import TLI.drawable.Size;
import TLI.position.CardinalDirection;
import TLI.position.Position;

public class CarLane extends Travelable {

    public CarLane(Position position, Size size, int speedLimit, CardinalDirection facing) {
        super(position, size, speedLimit, facing, false);
    }

    @Override
    public String toString() {
        return "CarLane(" + hashCode() + "){" +
                "position=" + position +
                "facing=" + facing +
                " next lane=" + nextLanes +
                '}';
    }
}