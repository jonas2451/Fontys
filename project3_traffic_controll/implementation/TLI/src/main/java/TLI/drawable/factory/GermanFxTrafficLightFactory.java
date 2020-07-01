package TLI.drawable.factory;

import TLI.drawable.entities.FxTrafficLight;
import TLI.drawable.strategy.FxEuropeanPedestrianLightStrategy;
import TLI.drawable.strategy.FxGermanCarLightStrategy;
import TLI.drawable.strategy.bulbcovers.DonkeyOfWesel;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;

public class GermanFxTrafficLightFactory extends AbstractFxTrafficLightFactory {

    public static synchronized AbstractFxTrafficLightFactory getInstance() {
        if (FACTORY != null) return FACTORY;
        else {
            FACTORY = new GermanFxTrafficLightFactory();
            return FACTORY;
        }
    }

    private GermanFxTrafficLightFactory() {
    }

    @Override
    public FxTrafficLight createFxCarTrafficLight(CarTrafficLight trafficLight, double scale) {
        return new FxTrafficLight(new FxGermanCarLightStrategy(scale), trafficLight);
    }

    @Override
    public FxTrafficLight createFxPedestrianTrafficLight(PedestrianTrafficLight trafficLight, double scale) {
        return new FxTrafficLight(new FxEuropeanPedestrianLightStrategy(scale), trafficLight);
    }
}
