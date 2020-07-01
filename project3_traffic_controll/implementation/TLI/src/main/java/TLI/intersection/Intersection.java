package TLI.intersection;

import TLI.trafficlight.TrafficLight;

import java.util.List;
import java.util.Set;

public abstract class Intersection implements Runnable {

    public abstract void cycle();

    public abstract List<InboundRoad> getInboundRoads();

    public abstract List<Travelable> getNextLanes();

    public abstract Set<TrafficLight> getCarTrafficLights();

    public abstract Set<TrafficLight> getPedTrafficLights();

}
