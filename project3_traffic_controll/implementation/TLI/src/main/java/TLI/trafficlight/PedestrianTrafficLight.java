package TLI.trafficlight;

import TLI.position.Position;
import TLI.strategy.LightStrategy;

public class PedestrianTrafficLight extends TrafficLight {

    public PedestrianTrafficLight(Position position, LightStrategy strategy) {
        super(position, strategy);
    }

    @Override
    public void go() {
        strategy.go(this);
    }

    @Override
    public void stop() {
        strategy.stop(this);
    }

    @Override
    public void doCycle() {
//        try {
//            strategy.cycle(this);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
    }
}
