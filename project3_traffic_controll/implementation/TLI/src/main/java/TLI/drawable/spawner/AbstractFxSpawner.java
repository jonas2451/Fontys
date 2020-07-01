package TLI.drawable.spawner;

import TLI.GUI.Canvas.observer.FxCollectorObserver;
import TLI.GUI.Canvas.observer.FxCollectorSubject;
import TLI.drawable.Drawable;
import TLI.drawable.entities.MovableFxEntity;
import TLI.intersection.Travelable;

import java.util.*;

public abstract class AbstractFxSpawner implements Runnable, FxCollectorSubject, FxCollectorObserver {

    protected List<FxCollectorObserver> observers = new ArrayList<>();

    private int spawnRate;
    private Timer timer;
    private TimerTask spawn;
    protected final Travelable travelable;
    protected Set<MovableFxEntity> entities = new HashSet<>();

    public AbstractFxSpawner(int spawnRate, Travelable travelable) {
        this.spawnRate = spawnRate;
        this.timer = new Timer();
        this.travelable = travelable;

        this.spawn = new TimerTask() {
            @Override
            public void run() {
                spawn();
            }
        };
    }

    public abstract void spawn();

    @Override
    public void run() {
        timer.scheduleAtFixedRate(spawn, 0, spawnRate);
    }

    public MovableFxEntity[] getFxEntities() {
        MovableFxEntity[] movableFxEntities = new MovableFxEntity[entities.size()];
        Object[] array = entities.toArray();
        for (int i = 0; i < entities.size(); i++) {
            movableFxEntities[i] = (MovableFxEntity) array[i];
        }
        return movableFxEntities;
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
            observer.updateDrawables(new ArrayList<>(this.entities));
        }
    }

    @Override
    public void updateDrawables(Collection<Drawable> drawables) {
        List<Drawable> toBeRemoved = new ArrayList<>();
        for (MovableFxEntity entity : this.entities) {
            if (!drawables.contains(entity)) toBeRemoved.add(entity);
        }
        this.entities.removeAll(toBeRemoved);
    }
}
