package TLI.strategy;

import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;

import java.util.Timer;
import java.util.TimerTask;

public class GermanCarTLStrategy implements LightStrategy {

    Timer timer;

    int warningToWaitTime = 1000;
    int readyToGoTime = 1000;

    @Deprecated
    public GermanCarTLStrategy() {
        this.timer = new Timer();
    }

    /**
     * Initiates the GermanCarStrategy
     *
     * @param warningToWaitTime is the warningtime it takes to go from orange to red
     * @param readyToGoTime is the ReadyTime to go from Ready from red/orange to green
     */
    public GermanCarTLStrategy(int warningToWaitTime, int readyToGoTime) {
        this.warningToWaitTime = warningToWaitTime;
        this.readyToGoTime = readyToGoTime;
        this.timer = new Timer();
    }
    /**
     * Switches the TrafficLight from red to green
     *
     * @param trafficLight is the TrafficLight that is to be switched
     * @since timer.schedule is used to delay changing the state from Waring to Wait
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight go(TrafficLight trafficLight) {
        trafficLight.setState(State.READY);

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        trafficLight.setState(State.GO);
                    }
                },
                this.readyToGoTime
        );

        return trafficLight;
    }
    /**
     * Switches the TrafficLight from green to red
     *
     * @param trafficLight is the TrafficLight that is to be switched
     * @since timer.schedule is used to delay changing the state from Waring to Wait
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight stop(TrafficLight trafficLight) {
        trafficLight.setState(State.WARNING);

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        trafficLight.setState(State.WAIT);
                    }
                },
                warningToWaitTime
        );
        return trafficLight;
    }
}
