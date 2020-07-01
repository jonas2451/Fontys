package TLI.movables;

import TLI.intersection.Direction;
import TLI.intersection.InboundLane;
import TLI.intersection.Travelable;
import TLI.trafficlight.State;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Car extends MovableEntity {

    Timer timer;
    State stateOfCurrentTrafficLight;

    public Car(MovableData data) {
        super(data);
        this.timer = new Timer();
    }

    /**
     * Makes the car proceed into its direction on a lane.
     */
    public void drive() {

//        System.out.println("progress of (" + this.hashCode() + ")" + this.data.getProgress().getProgress());

        if (!(data.getProgress().getProgress() >= data.getTravelingOn().getSize().getLength())) {
//            System.out.println("Car progress: " + data.getProgress().getRelativeProgress());
//            System.out.println("Car traveling on: " + data.getTravelingOn());
            if (data.travelingOn.getTrafficLight() == null) {
                this.carAlong();
            } else {
                //TODO Keep track of Traffic Light. DON'T CROSS RED!
                //FIXME {nullPointer by switching null state} How do we get a state?
                if (Objects.nonNull(stateOfCurrentTrafficLight)) {
                    switch (stateOfCurrentTrafficLight) {
                        case WAIT:
//                            System.out.println("\nstopping car on: " + this.getData().getTravelingOn());
//                            System.out.println("data.getProgress().getProgress() = " + data.getProgress().getProgress());
//                            System.out.println("data.getTravelingOn().getSize().getLength() = " + data.getTravelingOn().getSize().getLength());
                            if (this.data.getProgress().getRelativeProgress() >= 90) {
                                this.data.getSpeed().setPace(Pace.DECELERATING);
                            }
                            break;
                        case WARNING:
                            break;
                        case READY:
                            break;
                        case GO:
                            this.carAlong();
                            break;
                    }
                } else {
                    this.carAlong();
                }
            }
        } else {
            Travelable wasOn = data.getTravelingOn();

            if (Objects.nonNull(data.getTravelingOn().getNextLanes().get(Direction.STRAIGHT))) {
//                System.out.println("<Car> nextLane = " + data.getTravelingOn().getNextLanes().get(Direction.STRAIGHT));
                //FIXME Assigning Car to null lane at the end of the map
                super.data.setTravelingOn(data.getTravelingOn().getNextLanes().get(Direction.STRAIGHT));
                this.data.setProgress(new Progress(1, this.data.getTravelingOn().getSize()));

                if (data.getTravelingOn().isHasTrafficLight() && data.getTravelingOn().getClass().isAssignableFrom(InboundLane.class)) {
                    this.stateOfCurrentTrafficLight = data.getTravelingOn().getTrafficLight().getState();
                } else {
                    this.stateOfCurrentTrafficLight = null;
                }

                wasOn.removeEntity(this);
                data.getTravelingOn().addEntity(this);
            } else {
//                System.out.println("\n <Car> removing it ->  was on = " + this.data.getTravelingOn());
//                System.out.println(" <Car> removing car lane cause -> nextLane = " + data.getTravelingOn().getNextLanes().get(Direction.STRAIGHT) + "\n");

                this.timer.purge();
                this.timer.cancel();
                wasOn.removeEntity(this);
                this.data.setTravelingOn(null);
                //TODO Remove Car when map has ended

            }

        }

    }

    private void carAlong() {
        switch (data.getSpeed().getPace()) {
            case ACCELERATING:

            case DECELERATING:

            case FLOATING:
                this.data.getSpeed().setPpt(data.getTravelingOn().getSpeedLimit());
                this.data.proceedOnTravelable();
                break;
            case STOPPED:
                if (this.data.getProgress().getRelativeProgress() >= 98) {
                    this.data.getSpeed().setPpt(0);
                    this.data.proceedOnTravelable();
                } else {
                    this.data.getSpeed().setPpt(data.getTravelingOn().getSpeedLimit());
                    this.data.proceedOnTravelable();
                }
                break;
        }
    }

    private void collisionDetection() {
        //TODO Collision detection of cars
    }

    @Override
    public void update(State s) {
        this.stateOfCurrentTrafficLight = s;
    }

    @Override
    public void run() {

        TimerTask drive = new TimerTask() {
            @Override
            public void run() {
                drive();
            }
        };

        timer.scheduleAtFixedRate(drive, 0, 20);
    }

    @Override
    public String toString() {
        return "Car{" +
                "data=" + data +
                '}';
    }
}