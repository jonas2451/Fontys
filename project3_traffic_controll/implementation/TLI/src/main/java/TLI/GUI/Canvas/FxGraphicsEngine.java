package TLI.GUI.Canvas;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Erases a GraphicsContext and reprints all the elements in its list at a certain framerate.
 */
public class FxGraphicsEngine extends AnimationTimer {

    Set<SimulationLayer> layers = new HashSet<>();

    public FxGraphicsEngine(Group root, SimulationLayer... layers) {
        // add all layers to set
        this.layers.addAll(Arrays.stream(layers).collect(Collectors.toList()));
        // adds all canvases to the root group, making them visible in the GUI
        Arrays.stream(layers).forEach(c -> root.getChildren().add(c.getCanvas()));
        // prints all simulation layers once
        for (SimulationLayer layer : layers) {
            layer.drawAllDrawables();
        }
    }

    @Override
    public void handle(long now) {
        for (SimulationLayer layer : layers) {
            if (layer.isDynamic()) layer.drawAllDrawables();
        }
    }
}
