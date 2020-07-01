package TLI.movables;

import TLI.intersection.Travelable;
import TLI.position.CardinalDirection;
import TLI.position.Position;

public class MovableData {

    protected Travelable travelingOn;
    protected Position globalPosition;
    protected Speed speed;
    protected Progress progress;


    public MovableData(Travelable travelingOn, Position globalPosition, Speed speed, Progress progress) {
        this.travelingOn = travelingOn;
        this.globalPosition = new Position(travelingOn.getPosition());
        this.speed = speed;
        this.progress = progress;
    }

    public MovableData(Travelable travelingOn, Speed speed, Progress progress) {
        this.travelingOn = travelingOn;
        this.globalPosition = new Position(travelingOn.getPosition());
        this.globalPosition.setFacing(travelingOn.getFacing()); ////FIXME Travelable has facing and position with facing
        if (globalPosition.getFacing().equals(CardinalDirection.WEST)) {
            System.out.println("globalPosition before = " + globalPosition);
            globalPosition = globalPosition.addX(travelingOn.getSize().getLength());
            System.out.println("travelingOn.getSize().getLength() = " + travelingOn.getSize().getLength());
            System.out.println("<Car> global position " + globalPosition);
        }
        this.speed = speed;
        this.progress = progress;
    }

    MovableData proceedOnTravelable() {
        this.progress.proceed(this.speed, this.travelingOn.getSize());
        this.globalPosition.increase(this.speed.getPpt());
        return this;
    }

    public Position getGlobalPosition() {
        return globalPosition;
    }

    public void setGlobalPosition(Position globalPosition) {
        this.globalPosition = globalPosition;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Progress getProgress() {
        return progress;
    }

    public void getProgress(Progress progress) {
        this.progress = progress;
    }

    public Travelable getTravelingOn() {
        return travelingOn;
    }

    public void setTravelingOn(Travelable travelingOn) {
        this.travelingOn = travelingOn;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }
}
