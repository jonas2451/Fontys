package TLI.drawable.composer;

import TLI.drawable.factory.AbstractFxTrafficLightFactory;
import TLI.drawable.factory.DutchFxTrafficLightFactory;
import TLI.trafficlight.factory.AbstractTrafficLightFactory;
import TLI.trafficlight.factory.DutchTrafficLightFactory;

public class DutchPedCrossingFxComposer extends AbstractPedCrossingFxComposer {

    public static synchronized AbstractPedCrossingFxComposer getInstance() {
        if (COMPOSER != null) return COMPOSER;
        else {
            COMPOSER = new DutchPedCrossingFxComposer();
            return COMPOSER;
        }
    }

    @Override
    public AbstractTrafficLightFactory getTlFactory() {
        return DutchTrafficLightFactory.getInstance();
    }

    @Override
    public AbstractFxTrafficLightFactory getFxTrafficLightFactory() {
        return DutchFxTrafficLightFactory.getInstance();
    }

    private DutchPedCrossingFxComposer() {
    }
}
