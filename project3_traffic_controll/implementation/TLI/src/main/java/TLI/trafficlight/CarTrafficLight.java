package TLI.trafficlight;

import TLI.position.Position;
import TLI.strategy.LightStrategy;

/**
 *
 */
public class CarTrafficLight extends TrafficLight {

    public CarTrafficLight(Position position, LightStrategy strategy) {
        super(position, strategy);
        this.strategy = strategy;
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
    public void doCycle(){
//        try
//        {
//            strategy.cycle(this);
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }


    @Override
    public String toString() {
        return "CarTrafficLight{" +
                "observers=" + observers +
                ", state=" + state +
                ", position=" + position +
                ", strategy=" + strategy +
                '}';
    }

    @Override
    public void run()
    {
    }
}
