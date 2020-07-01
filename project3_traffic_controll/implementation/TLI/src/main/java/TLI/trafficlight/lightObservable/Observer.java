package TLI.trafficlight.lightObservable;

import TLI.trafficlight.State;

public interface Observer {
    void update(State s);
}
