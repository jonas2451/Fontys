package TLI.trafficlight.factory;

import TLI.trafficlight.lightObservable.Observer;
import TLI.position.Position;
import TLI.strategy.DutchCarStrategy;
import TLI.strategy.EuropeanPedestrianTLStrategy;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

/**
 * Creates Dutch TrafficLights.
 */
public class DutchTrafficLightFactory extends AbstractTrafficLightFactory {

    private DutchTrafficLightFactory() {
    }

    public static synchronized DutchTrafficLightFactory getInstance() {
        if (FACTORY != null) return (DutchTrafficLightFactory) FACTORY;
        else {
            FACTORY = new DutchTrafficLightFactory();
            return (DutchTrafficLightFactory) FACTORY;
        }
    };

    @Override
    public CarTrafficLight createCarTrafficLight(Position position) {
        return new CarTrafficLight(position, new DutchCarStrategy(1000));
    }

    @Override
    public CarTrafficLight createObservedCarLight(Position position, Observer... observer) {
        CarTrafficLight light = new CarTrafficLight(position, new DutchCarStrategy(1000));
        for (Observer o : observer) {
            light.registerObserver(o);
        }
        return light;
    }

    @Override
    public PedestrianTrafficLight createPedestrianTrafficLight(Position position) {
        return new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
    }

    @Override
    public PedestrianTrafficLight createObservedPedestrianTrafficLight(Position position, Observer... observer) {
        PedestrianTrafficLight light = new PedestrianTrafficLight(position, new EuropeanPedestrianTLStrategy());
        for (Observer o : observer) {
            light.registerObserver(o);
        }
        return light;
    }
}
