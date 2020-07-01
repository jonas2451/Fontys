package TLI.drawable.entities;

import TLI.drawable.Drawable;
import TLI.drawable.factory.AbstractFxTrafficLightFactory;
import TLI.intersection.InboundRoad;
import TLI.intersection.Intersection;
import TLI.intersection.Travelable;
import TLI.trafficlight.CarTrafficLight;
import TLI.trafficlight.PedestrianTrafficLight;
import TLI.trafficlight.TrafficLight;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashSet;
import java.util.Set;

public class FxIntersection implements Drawable {
    Set<FxLane> fxNextLanes = new HashSet<>();
    Set<FxInboundRoad> fxInboundRoads = new HashSet<>();
    Set<FxTrafficLight> fxTrafficLights = new HashSet<>();

    Intersection intersection;
    AbstractFxTrafficLightFactory factory;
    double scale;

    public FxIntersection(Intersection intersection, AbstractFxTrafficLightFactory factory, double scale) {
        this.intersection = intersection;
        this.factory = factory;
        this.scale = scale;

        for (TrafficLight pedTrafficLight : intersection.getPedTrafficLights()) {
            fxTrafficLights.add(factory.createFxPedestrianTrafficLight((PedestrianTrafficLight) pedTrafficLight, scale));
        }
    }

    @Override
    public GraphicsContext draw(GraphicsContext gc) {
        for (Drawable fxInboundRoad : fxInboundRoads) {
            fxInboundRoad.draw(gc);
        }
        for (Drawable fxTrafficLight : fxTrafficLights) {
            fxTrafficLight.draw(gc);
        }
        return gc;
    }

    @Override
    public boolean drawDynamic(GraphicsContext gc) {
        for (Drawable fxTrafficLight : fxTrafficLights) {
            fxTrafficLight.draw(gc);
        }
        return true;
    }

    public Drawable[] getFxInboundRoads() {

        // crate fx inbound roads lazy
        for (InboundRoad inboundRoad : intersection.getInboundRoads()) {
            fxInboundRoads.add(new FxInboundRoad(inboundRoad, true));
        }

        Drawable[] drawables = new Drawable[fxInboundRoads.size()];

        Object[] array = fxInboundRoads.toArray();

        for (int i = 0; i < fxInboundRoads.size(); i++) {
            drawables[i] = (Drawable) array[i];
        }
//        System.out.print("FxIntersection.getFxInboundRoads ");
//        System.out.println(Arrays.toString(drawables));
        return drawables;
    }

    public Drawable[] getFxTrafficLights() {

        // create fx traffic lights lazy
        for (TrafficLight trafficLight : intersection.getCarTrafficLights()) {
            fxTrafficLights.add(factory.createFxCarTrafficLight((CarTrafficLight) trafficLight, scale));
        }

        Drawable[] drawables = new Drawable[fxTrafficLights.size()];

        Object[] array = fxTrafficLights.toArray();

        for (int i = 0; i < fxTrafficLights.size(); i++) {
            drawables[i] = (Drawable) array[i];
        }
        return drawables;
    }

    public Drawable[] getFxNextLanes() {

        // create next lanes lazy
        for (Travelable t : intersection.getNextLanes()) {
            FxLane lane = new FxLane(t);
//            System.out.println("<FxIntersection> adding next lane : " + lane);
            this.fxNextLanes.add(lane);
        }

        Drawable[] drawables = new Drawable[fxNextLanes.size()];

        Object[] array = fxNextLanes.toArray();

        for (int i = 0; i < fxNextLanes.size(); i++) {
            drawables[i] = (Drawable) array[i];
        }
        return drawables;
    }



    public Intersection getIntersection() {
        return intersection;
    }
}
