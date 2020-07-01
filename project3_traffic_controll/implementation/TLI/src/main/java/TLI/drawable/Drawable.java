package TLI.drawable;

import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    default GraphicsContext draw(GraphicsContext gc) {
        throw new UnsupportedOperationException();
    }

    /**
     * This method is invoked by the {@link TLI.GUI.Canvas.FxGraphicsEngine} when being on a
     * {@link TLI.GUI.Canvas.SimulationLayer}.
     *
     * Use this method to specify how dynamic (or moving) object are displayed on a {@link javafx.scene.canvas.Canvas}
     *
     * @param gc to be drawn on
     * @return true if an Object should be drawn continuously. False if an Object should not be drawn anymore.
     */
    default boolean drawDynamic(GraphicsContext gc){
        throw new UnsupportedOperationException();
    }

    /**
     *
     *  @param gc           to draw on
     * @param trafficLight information about the position and the state
     */
    default void draw(GraphicsContext gc, TrafficLight trafficLight) {
        throw new UnsupportedOperationException();
    }
}