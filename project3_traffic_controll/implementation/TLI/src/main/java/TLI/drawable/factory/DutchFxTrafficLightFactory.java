package TLI.drawable.factory;

import TLI.drawable.entities.FxTrafficLight;
import TLI.drawable.strategy.FxDutchCarLightStrategy;
import TLI.drawable.strategy.FxEuropeanPedestrianLightStrategy;
import TLI.drawable.strategy.FxGermanCarLightStrategy;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

public class DutchFxTrafficLightFactory extends AbstractFxTrafficLightFactory {

    public static synchronized AbstractFxTrafficLightFactory getInstance() {
        if (FACTORY != null) return FACTORY;
        else {
            FACTORY = new DutchFxTrafficLightFactory();
            return FACTORY;
        }
    }

    private DutchFxTrafficLightFactory() {
    }

    @Override
    public FxTrafficLight createFxCarTrafficLight(CarTrafficLight trafficLight, double scale) {
        return new FxTrafficLight(new FxDutchCarLightStrategy(scale), trafficLight);
    }

    @Override
    public FxTrafficLight createFxPedestrianTrafficLight(PedestrianTrafficLight trafficLight, double scale) {
        return new FxTrafficLight(new FxEuropeanPedestrianLightStrategy(scale), trafficLight);
    }
}
