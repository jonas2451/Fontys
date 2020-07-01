package TLI.movables;

import TLI.trafficlight.lightObservable.Observer;

/**
 * An Entity that should be able to move.
 */
public abstract class MovableEntity implements Observer, Runnable {

    protected MovableData data;

    protected MovableEntity(MovableData data) {
        this.data = data;
    }

    public MovableData getData() {
        return data;
    }

    public void setData(MovableData data) {
        this.data = data;
    }

    //TODO{implement default methods of a movable entity}
}