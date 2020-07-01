package TLI.strategy;

import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;

public class EuropeanPedestrianTLStrategy implements LightStrategy {


    /**
     * Switches the TrafficLight from red to green
     * @param trafficLight is the TrafficLight that is to be switched
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight go(TrafficLight trafficLight) {
        trafficLight.setState(State.GO);
        return trafficLight;
    }

    /**
     * Switches the TrafficLight from red to green
     * @param trafficLight is the TrafficLight that is to be switched
     * @return changed TrafficLight
     */
    @Override
    public TrafficLight stop(TrafficLight trafficLight) {
        trafficLight.setState(State.WAIT);
        return trafficLight;
    }
}
