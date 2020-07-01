package TLI.drawable.composer;

import TLI.drawable.factory.AbstractFxTrafficLightFactory;
import TLI.drawable.factory.GermanFxTrafficLightFactory;
import TLI.trafficlight.factory.AbstractTrafficLightFactory;
import TLI.trafficlight.factory.GermanTrafficLightFactory;

public class GermanPedCrossingFxComposer extends AbstractPedCrossingFxComposer {

    public static synchronized AbstractPedCrossingFxComposer getInstance() {
        if (COMPOSER != null) return COMPOSER;
        else {
            COMPOSER = new GermanPedCrossingFxComposer();
            return COMPOSER;
        }
    }

    @Override
    public AbstractTrafficLightFactory getTlFactory() {
        return GermanTrafficLightFactory.getInstance();
    }

    @Override
    public AbstractFxTrafficLightFactory getFxTrafficLightFactory() {
        return GermanFxTrafficLightFactory.getInstance();
    }

    private GermanPedCrossingFxComposer() {
    }
}
