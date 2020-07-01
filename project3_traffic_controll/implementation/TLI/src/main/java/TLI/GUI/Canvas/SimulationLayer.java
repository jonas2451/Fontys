package TLI.GUI.Canvas;

import TLI.GUI.Canvas.observer.FxCollectorObserver;
import TLI.GUI.Canvas.observer.FxCollectorSubject;
import TLI.drawable.Drawable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.stream.Collectors;

public class SimulationLayer implements FxCollectorObserver, FxCollectorSubject {

    Set<Drawable> drawables = new HashSet<>();
    Canvas canvas;
    boolean dynamic = false;
    List<FxCollectorObserver> observers = new ArrayList<>();

    SimulationLayer(Canvas canvas, Drawable ...drawables) {
        this.canvas = canvas;
        this.drawables.addAll(Arrays.stream(drawables).collect(Collectors.toList()));
    }

    public SimulationLayer addDrawable(Drawable... drawables) {
        this.drawables.addAll(Arrays.asList(drawables));
        return this;
    }

    void drawAllDrawables() {

        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        if (this.isDynamic()) {
            List<Drawable> toBeRemoved = new ArrayList<>();
            for (Drawable drawable : drawables) {
                if (!drawable.drawDynamic(gc)) {
                    toBeRemoved.add(drawable);
                }
            }
            for (Drawable drawable : toBeRemoved) {
                System.out.println("removing: " + drawable);
                drawables.remove(drawable);
            }
            this.notifyObservers();
        } else {
            for (Drawable drawable : drawables) {
                drawable.draw(gc);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="">
    public Set<Drawable> getDrawables() {
        return drawables;
    }

    public void setDrawables(Set<Drawable> drawables) {
        this.drawables = drawables;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public void updateDrawables(Collection<Drawable> drawables) {
        this.drawables.addAll(drawables);
    }

    @Override
    public void addObserver(FxCollectorObserver fxCollectorObserver) {
        this.observers.add(fxCollectorObserver);
    }

    @Override
    public void removeObserver(FxCollectorObserver fxCollectorObserver) {
        this.observers.remove(fxCollectorObserver);
    }

    @Override
    public void notifyObservers() {
        for (FxCollectorObserver observer : observers) {
            observer.updateDrawables(this.drawables);
        }
    }

//    @Override
//    public vocid updateRemove(Collection<Drawable> drawables) {
//        this.drawables.removeAll(drawables);
//    }
    //</editor-fold>
}
