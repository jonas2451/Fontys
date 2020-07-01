package TLI.drawable.entities;

import TLI.drawable.Drawable;
import TLI.intersection.Travelable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FxLane implements Drawable {

    Travelable travelable;

    public FxLane(Travelable travelable) {
        this.travelable = travelable;
    }

    @Override
    public GraphicsContext draw(GraphicsContext gc) {
        //TODO Make Road Markers (White lines on the road)
        gc.setFill(Color.GREY);
        gc.fillRect(travelable.getPosition().getX(), travelable.getPosition().getY(), travelable.getSize().getWidth(), travelable.getSize().getHeight());

        // stripes
        gc.setFill(Color.WHITE);
        gc.fillRect(travelable.getPosition().getX(), travelable.getPosition().addY(2).getY(), travelable.getSize().getWidth(), 2);
        gc.fillRect(travelable.getPosition().getX(), travelable.getPosition().addY(travelable.getSize().getHeight()).addY(-4).getY(), travelable.getSize().getWidth(), 2);
        return gc;
    }

    @Override
    public String toString() {
        return "FxLane{" +
                "travelable=" + travelable +
                '}';
    }
}
