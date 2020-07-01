import TLI.strategy.EuropeanPedestrianTLStrategy;
import TLI.trafficlight.PedestrianTrafficLight;
import org.junit.Test;

public class EuropeanPedestrianTLStrategyTest {


    EuropeanPedestrianTLStrategy europeanPedestrianTLStrategy;
    PedestrianTrafficLight pedestrianTrafficLight;

    @Test
    public void test() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        t1.start();
    }
}

