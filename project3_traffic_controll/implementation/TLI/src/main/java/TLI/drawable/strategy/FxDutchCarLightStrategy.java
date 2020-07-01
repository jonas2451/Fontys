package TLI.drawable.strategy;

import TLI.position.Position;
import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FxDutchCarLightStrategy extends FxLightStrategy {

    public FxDutchCarLightStrategy(double scale) {
        super.scale = scale;
    }

    @Override
    public void draw(GraphicsContext gc, TrafficLight trafficLight) {
        Position position = trafficLight.getPosition();

        //<editor-fold defaultstate="collapsed" desc="">
        /*Position position = trafficLight.getPosition();

        Image carLightRed = new Image("images/TrafficLightRed.png");
        Image carLightGreen = new Image("images/TrafficLightGreen.png");
        Image carLightYellow = new Image("images/TrafficLightOrange.png");

        double scale = 0.2;

        double width = carLightGreen.getWidth() * scale;
        double height = carLightRed.getHeight() * scale;

        if (trafficLight.getState().equals(State.GO)) {
            gc.drawImage(carLightGreen, position.getX(), position.getY(), (int) width, (int) height);

        } else if (trafficLight.getState().equals(State.WARNING)) {
            gc.drawImage(carLightYellow, position.getX(), position.getY(), (int) width, (int) height);

        } else {
            gc.drawImage(carLightRed, position.getX(), position.getY(), (int) width, (int) height);
        }*/
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Jonas implementation">
        switch (trafficLight.getState()) {
            case READY:
            case GO:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.LIGHTGREEN);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 280 * scale, 120 * scale, 120 * scale);

                drawEmptyTrafficLight(gc, trafficLight);
                break;

            case WARNING:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.YELLOW);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 160 * scale, 120 * scale, 120 * scale);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
            default:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.RED);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 35 * scale, 120 * scale, 120 * scale);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
        }
        //</editor-fold>
    }
    private void drawEmptyTrafficLight(GraphicsContext gc, TrafficLight trafficLight) {
        gc.drawImage(carLight, trafficLight.getPosition().getX(), trafficLight.getPosition().getY(), (int) carLight.getWidth() * scale, (int) carLight.getHeight() * scale);
    }
}
