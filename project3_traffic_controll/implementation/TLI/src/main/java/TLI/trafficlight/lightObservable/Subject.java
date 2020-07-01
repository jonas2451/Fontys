package TLI.trafficlight.lightObservable;

public interface Subject {

    /**
     * Adds an Observer to the list of observers.
     *
     * @param observer to be added
     */
    void registerObserver(Observer observer);

    /**
     * Removes an Observer from a list of observers.
     *
     * @param observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all the observers that the Subject has changed.
     */
    void notifyObservers();
}
