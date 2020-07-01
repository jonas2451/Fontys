import TLI.position.Position;
import TLI.strategy.*;
import TLI.trafficlight.ConsoleLight;
import TLI.trafficlight.factory.AbstractTrafficLightFactory;
import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;
import TLI.trafficlight.factory.DutchTrafficLightFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTrafficLightTest {

    TrafficLight dutchCarLight;
    AbstractTrafficLightFactory factory;

    @Before
    public void setUp() {
        this.factory = DutchTrafficLightFactory.getInstance();
        this.dutchCarLight = factory.createCarTrafficLight(new Position(100, 100));
    }

    @Test
    public void initialStateIsWaitTest() {
        assertEquals(State.WAIT, dutchCarLight.getState());
    }

    @Test
    public void changeStateToWalkTest() {
        dutchCarLight.go();
        assertEquals(State.GO, dutchCarLight.getState());
    }

    @Test
    @Ignore //TODO {make separate thread to test yellow phase}
    public void changeStateToWarningTest() {
        dutchCarLight.stop();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        assertEquals(State.WARNING, dutchCarLight.getState());
    }

    /*@Test
    public void doCycleDutchStrategyTest() throws Exception {
        Thread trafficThread = new Thread(dutchCarLight);
        trafficThread.setDaemon(true);
        trafficThread.start();
        Thread.sleep(10);
        assertEquals(State.GO, dutchCarLight.getState());
        Thread.sleep(6010);
        assertEquals(State.WARNING, dutchCarLight.getState());
        Thread.sleep(1010);
        assertEquals(State.WAIT, dutchCarLight.getState());
        trafficThread.stop();
    }*/

    @Test
    public void testExchangeableBehaviour() throws Exception
    {
        int delayGreenToYellow = 1000;
        int delayYellowToRed = 1000;
        int delayYellowRedToGreen = 1000;

        TrafficLight light = factory.createCarTrafficLight(new Position(100,100));

        DutchCarStrategy strategy = (DutchCarStrategy) light.getStrategy();

        Thread testThread = new Thread(light);
        light.go();
        Thread.sleep(10);
        assertEquals(State.GO, light.getState());
        light.stop();
        Thread.sleep(delayGreenToYellow-50);
        assertEquals(State.WARNING, light.getState());  //the test thread gets paused in the strategy so it waits for the state to go to wait
        Thread.sleep(delayYellowToRed-50);
        assertEquals(State.WAIT, light.getState());

        GermanCarTLStrategy germanCarTLStrategy = new GermanCarTLStrategy(delayYellowToRed, delayYellowRedToGreen);
        light.setStrategy(germanCarTLStrategy);

        light.go();
        assertEquals(State.READY, light.getState());
        Thread.sleep(delayYellowRedToGreen+100);
        assertEquals(State.GO, light.getState());
        light.stop();
        Thread.sleep(delayGreenToYellow-50);
        assertEquals(State.WARNING, light.getState());
        Thread.sleep(delayYellowToRed-50);
        assertEquals(State.WAIT, light.getState());
    }

    @Test
    public void observableTest() {
        ConsoleLight consoleLight = new ConsoleLight();
        dutchCarLight.registerObserver(consoleLight);
        dutchCarLight.go();

        assertEquals(dutchCarLight.getState(), consoleLight.getState());

        dutchCarLight.stop();

        assertEquals(dutchCarLight.getState(), consoleLight.getState());
    }
}
