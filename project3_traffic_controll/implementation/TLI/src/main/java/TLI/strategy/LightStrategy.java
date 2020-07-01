package TLI.strategy;

import TLI.trafficlight.TrafficLight;

public interface LightStrategy {

    /**
     * Makes the light change from red to green
     * @param trafficLight to be changed
     * @return changed trafficLight
     */
    TrafficLight go(TrafficLight trafficLight);

    /**
     * Makes the light change from green to red
     * @param trafficLight to be changed
     * @return changed trafficLight
     */
    TrafficLight stop(TrafficLight trafficLight);
}
