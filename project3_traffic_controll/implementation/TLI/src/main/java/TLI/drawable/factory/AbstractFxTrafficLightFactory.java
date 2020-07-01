package TLI.drawable.factory;

import TLI.drawable.entities.FxTrafficLight;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

public abstract class AbstractFxTrafficLightFactory {

    protected static AbstractFxTrafficLightFactory FACTORY;


    /**
     * Creates fxCar traffic light.
     *
     * @return drawable car trafficLight
     */
    public FxTrafficLight createFxCarTrafficLight(CarTrafficLight trafficLight) {
        return this.createFxCarTrafficLight(trafficLight, 0.2);
    };

    public abstract FxTrafficLight createFxCarTrafficLight(CarTrafficLight trafficLight, double scale);

    /**
     * Creates fxPedestrian traffic light.
     *
     * @return drawable pedestrian trafficLight
     */
    public FxTrafficLight createFxPedestrianTrafficLight(PedestrianTrafficLight trafficLight) {
        return this.createFxPedestrianTrafficLight(trafficLight, 0.2);
    };

    public abstract FxTrafficLight createFxPedestrianTrafficLight(PedestrianTrafficLight trafficLight, double scale);

}
