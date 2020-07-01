package TLI.strategy;

import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;

import java.util.Timer;
import java.util.TimerTask;

public class DutchCarStrategy implements LightStrategy {

    Timer timer;
    int warningToWaitTime = 1000;
//    int readToGoTime = 1000;
    @Deprecated
    public DutchCarStrategy() { this.timer = new Timer();}

    /**
     * initiates the DutchCarStrategy
     *
     * @param warningToWaitTime the time it takes from the warning to stop / orange to red
     */
    public DutchCarStrategy(int warningToWaitTime){
        this.warningToWaitTime= warningToWaitTime;
        this.timer = new Timer();
    }
    /**
     * Switches the TrafficLight from red to green
     *
     * @param trafficLight is the TrafficLight that is to be switched
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight go(TrafficLight trafficLight) {
        trafficLight.setState(State.GO);
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
