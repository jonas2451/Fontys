package TLI.drawable.strategy;

import TLI.position.Position;
import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class FxEuropeanPedestrianLightStrategy extends FxLightStrategy {

    public FxEuropeanPedestrianLightStrategy() {
    }

    public FxEuropeanPedestrianLightStrategy(double scale) {
        super(scale);
    }

    @Override
    public void draw(GraphicsContext gc, TrafficLight trafficLight) {
        Position position = trafficLight.getPosition();

        Image pedestrianLightRed = new Image("images/PedestrianLightRed.png");
        Image pedestrianLightGreen = new Image("images/PedestrianLightGreen.png");


        double width = pedestrianLightGreen.getWidth() * scale;
        double height = pedestrianLightRed.getHeight() * scale;

        if (trafficLight.getState().equals(State.GO)) {
            gc.drawImage(pedestrianLightGreen, position.getX(), position.getY(), (int) width, (int) height);
        } else {
            gc.drawImage(pedestrianLightRed, position.getX(), position.getY(), (int) width, (int) height);
        }
    }
}
