package TLI.drawable.strategy;

import TLI.drawable.strategy.bulbcovers.FxBulbCover;
import TLI.position.Position;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FxGermanCarLightStrategy extends FxLightStrategy {

//    private double scale = 0.2;
//    private Image carLight = new Image("images/EmptyTrafficLight_transparent.png");
//    private FxBulbCover fxBulbCover;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public FxGermanCarLightStrategy() { }

    public FxGermanCarLightStrategy(double scale) {
        this.scale = scale;
    }

    public FxGermanCarLightStrategy(double scale, FxBulbCover fxBulbCover)
    {
        this.scale = scale;
        this.fxBulbCover = fxBulbCover;
    }

    public FxGermanCarLightStrategy(FxBulbCover fxBulbCover)
    {
        this.fxBulbCover = fxBulbCover;
    }
    //</editor-fold>

    @Override
    public void draw(GraphicsContext gc, TrafficLight trafficLight) {
        Position position = trafficLight.getPosition();

        //<editor-fold defaultstate="collapsed" desc="Jonas implementation">
        switch (trafficLight.getState()) {
            case GO:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.LIGHTGREEN);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 280 * scale, 120 * scale, 120 * scale);
                drawGreenCover(gc, position, trafficLight);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
            case READY:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.YELLOW);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 160 * scale, 120 * scale, 120 * scale);
                drawYellowCover(gc, position, trafficLight);

                gc.setFill(Color.RED);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 35 * scale, 120 * scale, 120 * scale);
                drawRedCover(gc, position, trafficLight);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
            case WARNING:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.YELLOW);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 160 * scale, 120 * scale, 120 * scale);
                drawYellowCover(gc, position, trafficLight);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
            default:
                paintAllBulbsGrey(position, gc, scale);

                gc.setFill(Color.RED);
                gc.fillOval(position.getX() + (35 * scale), position.getY() + 35 * scale, 120 * scale, 120 * scale);
                drawRedCover(gc, position, trafficLight);

                drawEmptyTrafficLight(gc, trafficLight);
                break;
        }
        //</editor-fold>
    }

    private void drawEmptyTrafficLight(GraphicsContext gc, TrafficLight trafficLight) {
        gc.drawImage(carLight, trafficLight.getPosition().getX(), trafficLight.getPosition().getY(), (int) carLight.getWidth() * scale, (int) carLight.getHeight() * scale);
    }


    //<editor-fold defaultstate="collapsed" desc="delegate drawing to the fxBulbcover class + checking that it is not null">
    private void drawGreenCover(GraphicsContext gc, Position position, TrafficLight tl)
    {
        if(fxBulbCover!=null) fxBulbCover.drawGreenBulbCover(gc, position, tl, scale);
    }

    private void drawYellowCover(GraphicsContext gc, Position position, TrafficLight tl)
    {
        if(fxBulbCover!=null) fxBulbCover.drawYellowBulbCover(gc, position, tl, scale);
    }

    private void drawRedCover(GraphicsContext gc, Position position, TrafficLight tl)
    {
        if(fxBulbCover!=null) fxBulbCover.drawRedBulbCover(gc, position, tl, scale);
    }
    //</editor-fold>
}
