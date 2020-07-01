package TLI.drawable.strategy;

import TLI.drawable.Drawable;
import TLI.drawable.strategy.bulbcovers.FxBulbCover;
import TLI.position.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class FxLightStrategy implements Drawable {

    protected double scale = 0.2;
    protected Image carLight = new Image("images/EmptyTrafficLight_transparent.png");
    protected FxBulbCover fxBulbCover;

    protected FxLightStrategy(double scale) {
        this.scale = scale;
    }

    protected FxLightStrategy() {
    }

    void paintAllBulbsGrey(Position position, GraphicsContext gc, double scale) {
        gc.setFill(Color.GREY);
        gc.fillOval(position.getX() + (35 * scale), position.getY() + 280 * scale, 120 * scale, 120 * scale);
        gc.fillOval(position.getX() + (35 * scale), position.getY() + 160 * scale, 120 * scale, 120 * scale);
        gc.fillOval(position.getX() + (35 * scale), position.getY() + 35 * scale, 120 * scale, 120 * scale);
    }
}