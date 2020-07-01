package TLI.drawable.strategy.bulbcovers;

import TLI.position.Position;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class FxBulbCover {

    Image greenBulbCover, yellowBulbCover, redBulbCover;

    public FxBulbCover(Image greenBulbCover, Image yellowBulbCover, Image redBulbCover)
    {
        this.greenBulbCover = greenBulbCover;
        this.yellowBulbCover = yellowBulbCover;
        this.redBulbCover = redBulbCover;
    }

    public void drawGreenBulbCover(GraphicsContext gc, Position position, TrafficLight tl, double scale)
    {
        gc.drawImage(greenBulbCover, position.getX(), position.getY(), 120 * scale, 120 * scale);
    }

    public void drawYellowBulbCover(GraphicsContext gc, Position position, TrafficLight tl, double scale)       //todo delete traffic light if not needed
    {
        gc.drawImage(yellowBulbCover, position.getX(), position.getY(), 120 * scale, 120 * scale);
    }

    public void drawRedBulbCover(GraphicsContext gc, Position position, TrafficLight tl, double scale)
    {
        gc.drawImage(redBulbCover, position.getX(), position.getY(), 120 * scale, 120 * scale);
    }


}
