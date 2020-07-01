package TLI.trafficlight;

import TLI.trafficlight.lightObservable.Observer;

public class ConsoleLight implements Observer {
    State state;
    @Override
    public void update(State s) {
        this.state = s;
        System.out.println(s);
    }

    public State getState() {
        return state;
    }
}
