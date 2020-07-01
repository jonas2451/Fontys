package TLI.GUI.Canvas.observer;

public interface FxCollectorSubject {
    void addObserver(FxCollectorObserver fxCollectorObserver);
    void removeObserver(FxCollectorObserver fxCollectorObserver);
    void notifyObservers();
}
