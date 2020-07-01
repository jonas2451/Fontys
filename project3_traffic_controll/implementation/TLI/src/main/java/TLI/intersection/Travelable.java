package TLI.intersection;

import TLI.GUI.Canvas.observer.FxCollectorObserver;
import TLI.drawable.Size;
import TLI.movables.MovableEntity;
import TLI.movables.Progress;
import TLI.position.CardinalDirection;
import TLI.position.Position;
import TLI.trafficlight.TrafficLight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Something that a MovableEntity can travel on. Keeps track of the Movable's position ON the Travelable.
 */
public abstract class Travelable {

//    Queue<MovableEntity> movableEntities = new ConcurrentLinkedDeque<>();
    protected Map<MovableEntity, Progress> movableEntities = new HashMap<>();
    protected final Position position;
    protected Size size;
    protected int speedLimit;
    protected final CardinalDirection facing; /*CardinalDirection.EAST;*/
    protected Map<Direction, Travelable> nextLanes = new HashMap<>();
    protected boolean hasTrafficLight;


    protected Travelable(Position position, Size size, int speedLimit, CardinalDirection cardinalDirection, boolean hasTrafficLight) {
        this.position = position;
        this.size = size;
        this.speedLimit = speedLimit;
        this.facing = cardinalDirection;
        this.hasTrafficLight = hasTrafficLight;
    }


    public Travelable addNextLane(Direction direction, Travelable lane) {
        this.nextLanes.put(direction, lane);
        return this;
    }

    public Map<Direction, Travelable> getNextLanes() {
        return nextLanes;
    }


    /**
     * Should add a Movable to a Queue of Movables.
     * @param toAdd
     */
    public void addEntity(MovableEntity toAdd) {
        movableEntities.put(toAdd, toAdd.getData().getProgress());
    };

    /**
     * Should remove a Movable from the Queue of Movables.
     * @param toRemove
     */
    public void removeEntity(MovableEntity toRemove) {
        movableEntities.remove(toRemove);
    };

    public TrafficLight getTrafficLight() {
        return null;
    }

    public Position getSpawnPosition() {
        return null;
    }

    public CardinalDirection getFacing() {
        return facing;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">

    public Map<MovableEntity, Progress> getMovableEntities() {
        return movableEntities;
    }

    public void setMovableEntities(Map<MovableEntity, Progress> movableEntities) {
        this.movableEntities = movableEntities;
    }

    public Position getPosition() {
        return position;
    }

//    public void setPosition(Position position) {
//        this.position = position;
//    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public boolean isHasTrafficLight() {
        return hasTrafficLight;
    }
//</editor-fold>


    @Override
    public String toString() {
        return "Travelable(" + hashCode() + "){" +
                "position=" + position +
                "facing=" + facing +
                '}';
    }
}