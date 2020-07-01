package TLI.trafficlight;

import TLI.trafficlight.lightObservable.Observer;
import TLI.trafficlight.lightObservable.Subject;
import TLI.position.Position;
import TLI.strategy.LightStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class TrafficLight implements Subject, Runnable {

    List<Observer> observers = new ArrayList<>();
    protected State state = State.WAIT;
    protected Position position;
    protected LightStrategy strategy;

    protected TrafficLight(Position position, LightStrategy strategy) {
        this.position = position;
        this.strategy = strategy;
    }

    /**
     * Indicates it is safe to cross the road.
     */
    public abstract void go();

    /**
     * Indicates you must not cross the road.
     */
    public abstract void stop();

    /**
     * A Traffic Light cycling from red to green to red.
     */
    @Deprecated
    public abstract void doCycle();

    @Override
    public void registerObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(state);
        }
    }

    public State getState() {
        return state;
    }

    public Position getPosition()
    {
        return position;
    }

    public LightStrategy getStrategy()
    {
        return strategy;
    }

    /**
     *  With this method the Strategy can be exchanged during runtime
     */
    public void setStrategy(LightStrategy strategy)
    {
        this.strategy = strategy;
    }

    /**
     * Sets the state of a TrafficLight.
     *
     * WARNING!!! Do NOT use this method to change the state of the TrafficLight outside of a strategy implementation.
     * @param state to be set.
     */
    public void setState(State state) {
        this.state = state;
        this.notifyObservers();
    }
}
