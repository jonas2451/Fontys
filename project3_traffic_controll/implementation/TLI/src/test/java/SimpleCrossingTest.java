import TLI.intersection.SimplePedestrianCrossing;
import TLI.intersection.Factory.AbstractIntersectionFactory;
import TLI.intersection.Factory.CarPedIntersectionFactory;
import TLI.trafficlight.ConsoleLight;
import TLI.trafficlight.State;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleCrossingTest {

    SimplePedestrianCrossing intersection;
    Thread thread;

    @Before
    public void setUp() {
        AbstractIntersectionFactory intersectionFactory = CarPedIntersectionFactory.getInstance();
//        this.intersection = (SimplePedestrianCrossing) intersectionFactory.createSimplePedCrossing(DutchTrafficLightFactory.getInstance());
        this.intersection.getWestInboundRoad().getCenterLight().registerObserver(new ConsoleLight());   //todo There are two inboundRoads now...
        thread = new Thread(intersection);
        thread.setDaemon(true);
    }

    @Test
    public void cycleTest() {
        thread.start();

        try {
            Thread.sleep(1000);

            assertEquals(State.GO, intersection.getWestInboundRoad().getCenterLight().getState());
            assertEquals(State.WAIT, intersection.getWestPedTrafficLight().getState());
            assertEquals(State.WAIT, intersection.getEastPedTrafficLight().getState());

            Thread.sleep(4000);

            assertEquals(State.WAIT, intersection.getWestInboundRoad().getCenterLight().getState());
            assertEquals(State.GO, intersection.getWestPedTrafficLight().getState());
            assertEquals(State.GO, intersection.getEastPedTrafficLight().getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @After
    public void kill() {
        try {
            thread.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
