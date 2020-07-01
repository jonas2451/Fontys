package TLI.trafficlight.factory;

import TLI.trafficlight.lightObservable.Observer;
import TLI.position.Position;
import TLI.strategy.DutchCarTLStrategy;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

/**
 * Creates car traffic lights.
 */
@Deprecated
public class CarTrafficLightFactory extends AbstractTrafficLightFactory {

    private CarTrafficLightFactory() {
    }

    public static synchronized AbstractTrafficLightFactory getInstance() {
        if (FACTORY != null) return FACTORY;
        else {
            FACTORY = new CarTrafficLightFactory();
            return FACTORY;
        }
    };

    @Override
    public CarTrafficLight createCarTrafficLight(Position position) {
        return new CarTrafficLight(position, new DutchCarTLStrategy());
    }

    @Override
    public CarTrafficLight createObservedCarLight(Position position, Observer... observer) {
        CarTrafficLight carTrafficLight = new CarTrafficLight(position, new DutchCarTLStrategy());
        for (int i = 0; i < observer.length; i++) {
            carTrafficLight.registerObserver(observer[i]);
        }
        return carTrafficLight;
    }

    @Override
    public PedestrianTrafficLight createPedestrianTrafficLight(Position position) {
        return null;
    }

    @Override
    public PedestrianTrafficLight createObservedPedestrianTrafficLight(Position position, Observer... observer) {
        return null;
    }
}

