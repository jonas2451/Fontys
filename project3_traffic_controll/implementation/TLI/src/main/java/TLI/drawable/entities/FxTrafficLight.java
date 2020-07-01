package TLI.drawable.entities;

import TLI.drawable.Drawable;
import TLI.drawable.strategy.FxLightStrategy;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;

public class FxTrafficLight implements Drawable {

    private FxLightStrategy fxLightStrategy;
    private TrafficLight trafficLight;

    public FxTrafficLight(FxLightStrategy fxLightStrategy, TrafficLight trafficLight) {
        this.fxLightStrategy = fxLightStrategy;
        this.trafficLight = trafficLight;
    }

    public FxLightStrategy getFxLightStrategy() {
        return fxLightStrategy;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    @Override
    public boolean drawDynamic(GraphicsContext gc) {
        fxLightStrategy.draw(gc, trafficLight);
        return true;
    }
}
