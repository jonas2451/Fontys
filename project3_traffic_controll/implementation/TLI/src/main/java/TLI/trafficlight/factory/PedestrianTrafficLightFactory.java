package TLI.trafficlight.factory;

import TLI.trafficlight.lightObservable.Observer;
import TLI.position.Position;
import TLI.strategy.EuropeanPedestrianTLStrategy;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

@Deprecated
public class PedestrianTrafficLightFactory extends AbstractTrafficLightFactory {

    private PedestrianTrafficLightFactory() {
    }

    public static synchronized AbstractTrafficLightFactory getInstance() {
        if (FACTORY != null) return FACTORY;
        else {
            FACTORY = new PedestrianTrafficLightFactory();
            return FACTORY;
        }
    }

    @Override
    public CarTrafficLight createCarTrafficLight(Position position) {
        return null;
    }

    @Override
    public CarTrafficLight createObservedCarLight(Position position, Observer... observer) {
        return null;
    }

    @Override
    public PedestrianTrafficLight createPedestrianTrafficLight(Position position) {
        return new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
    }

    @Override
    public PedestrianTrafficLight createObservedPedestrianTrafficLight(Position position, Observer... observer) {
        PedestrianTrafficLight light = new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
        for (int i = 0; i < observer.length; i++) {
            light.registerObserver(observer[i]);
        }
        return light;
    }
}