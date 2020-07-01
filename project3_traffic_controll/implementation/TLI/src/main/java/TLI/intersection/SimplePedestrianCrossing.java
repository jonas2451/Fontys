package TLI.intersection;

import TLI.trafficlight.PedestrianTrafficLight;
import TLI.trafficlight.State;
import TLI.trafficlight.TrafficLight;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimplePedestrianCrossing extends Intersection {

    private InboundRoad westInboundRoad, eastInboundRoad;
    private PedestrianTrafficLight westPedTrafficLight, eastPedTrafficLight;

    public SimplePedestrianCrossing(InboundRoad westInboundRoad, InboundRoad eastInboundRoad, PedestrianTrafficLight westPedTrafficLight, PedestrianTrafficLight eastPedTrafficLight) {
        this.westInboundRoad = westInboundRoad;
        this.eastInboundRoad = eastInboundRoad;
        this.westPedTrafficLight = westPedTrafficLight;
        this.eastPedTrafficLight = eastPedTrafficLight;
    }

    //<editor-fold> "Logic"
    @Override
    public void cycle() {
        System.out.println("\n--- new trafficlight cycle --- ");
        try {
            makePedestriansRed();
            makeCarsGreen();

//            System.out.println("sleepy?");
            Thread.sleep(5000);
//            System.out.println("awake...");

            makeCarsRed();
            Thread.sleep(1500);
            makePedestriansGreen();

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makePedestriansRed() {

//        System.out.println(westInboundRoad.getCenterLight().getState().toString());

        this.westPedTrafficLight.stop();
        this.eastPedTrafficLight.stop();
    }

    private void makePedestriansGreen() {

//        System.out.println(westInboundRoad.getCenterLight().getState().toString());

        if (westInboundRoad.getCenterLight().getState().equals(State.WAIT) &&
                eastInboundRoad.getCenterLight().getState().equals(State.WAIT)) {

//            System.out.println("If statement has been (b)reached");

        this.westPedTrafficLight.go();
        this.eastPedTrafficLight.go();
        }


    }

    private void makeCarsRed() {
        for (TrafficLight trafficLight : this.eastInboundRoad.getTrafficLights()) {
//            System.out.println("stopping: " + trafficLight);
            trafficLight.stop();

//            System.out.println(westInboundRoad.getCenterLight().getState().toString());

        }
        for (TrafficLight trafficLight : this.westInboundRoad.getTrafficLights()) {
//            System.out.println("stopping: " + trafficLight);
            trafficLight.stop();
        }
    }

    private void makeCarsGreen() {
        for (TrafficLight trafficLight : this.eastInboundRoad.getTrafficLights()) {
            trafficLight.go();
        }
        for (TrafficLight trafficLight : this.westInboundRoad.getTrafficLights()) {
            trafficLight.go();
        }
    }

    @Override
    public List<InboundRoad> getInboundRoads() {
        List<InboundRoad> lights = new ArrayList<>();

        lights.add(westInboundRoad);
        lights.add(eastInboundRoad);
        return lights;
    }

    @Override
    public List<Travelable> getNextLanes() {
        List<Travelable> nextLanes = new ArrayList<>();

        //FIXME ¿Que Pasa?

        for (InboundLane lane : westInboundRoad.getLanes()) {
            for (Direction d : lane.getNextLanes().keySet()) {
                nextLanes.add(lane.getNextLanes().get(d));
            }
        }

        for (InboundLane lane : eastInboundRoad.getLanes()) {
            for (Direction d : lane.getNextLanes().keySet()) {
                nextLanes.add(lane.getNextLanes().get(d));
            }
        }

        return nextLanes;
    }

    @Override
    public Set<TrafficLight> getCarTrafficLights() {
        Set<TrafficLight> lights = new HashSet<TrafficLight>();

        lights.addAll(eastInboundRoad.getTrafficLights());
        lights.addAll(westInboundRoad.getTrafficLights());
        return lights;
    }

    @Override
    public Set<TrafficLight> getPedTrafficLights() {
        Set<TrafficLight> lights = new HashSet<TrafficLight>();

        lights.add(westPedTrafficLight);
        lights.add(eastPedTrafficLight);
        return lights;
    }

    @Override
    public void run() {
        while (true) {
            this.cycle();
        }
    }

    //</editor-fold>

    @Override
    public String toString() {
        return "SimplePedestrianCrossing{" +
                "westInboundRoad=" + westInboundRoad +
                ", eastInboundRoad=" + eastInboundRoad +
                ", westPedTrafficLight=" + westPedTrafficLight +
                ", eastPedTrafficLight=" + eastPedTrafficLight +
                '}';
    }


    //<editor-fold> Getters and Setters

    public InboundRoad getWestInboundRoad() {
        return westInboundRoad;
    }

    public void setWestInboundRoad(InboundRoad westInboundRoad) {
        this.westInboundRoad = westInboundRoad;
    }

    public InboundRoad getEastInboundRoad() {
        return eastInboundRoad;
    }

    public void setEastInboundRoad(InboundRoad eastInboundRoad) {
        this.eastInboundRoad = eastInboundRoad;
    }

    public PedestrianTrafficLight getWestPedTrafficLight() {
        return westPedTrafficLight;
    }

    public void setWestPedTrafficLight(PedestrianTrafficLight westPedTrafficLight) {
        this.westPedTrafficLight = westPedTrafficLight;
    }

    public PedestrianTrafficLight getEastPedTrafficLight() {
        return eastPedTrafficLight;
    }

    public void setEastPedTrafficLight(PedestrianTrafficLight eastPedTrafficLight) {
        this.eastPedTrafficLight = eastPedTrafficLight;
    }


    //</editor-fold>
}
