package TLI.drawable.composer;

import TLI.drawable.Size;
import TLI.drawable.entities.FxIntersection;
import TLI.drawable.factory.AbstractFxTrafficLightFactory;
import TLI.intersection.InboundLane;
import TLI.intersection.InboundRoad;
import TLI.intersection.Intersection;
import TLI.intersection.SimplePedestrianCrossing;
import TLI.position.CardinalDirection;
import TLI.position.Position;
import TLI.trafficlight.PedestrianTrafficLight;
import TLI.trafficlight.factory.AbstractTrafficLightFactory;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPedCrossingFxComposer /*implements IntersectionComposer*/ {

    static AbstractPedCrossingFxComposer COMPOSER;
    Set<FxIntersection> fxIntersections = new HashSet<>();

    /**
     *
     * @param position central position of the crossing.
     * @return an Intersection.
     */
    public FxIntersection createIntersection(Position position, int speedLimit) {
        AbstractTrafficLightFactory FACTORY = this.getTlFactory();

        //<editor-fold defaultstate="collapsed" desc="Create Intersection">

        InboundLane laneEast = new InboundLane(
                position.addX(80).addY(-50),
                new Size(100, 50),
                speedLimit,
                FACTORY.createCarTrafficLight(position.addX(100).addY(-100)),
                CardinalDirection.WEST
        );

        InboundRoad eastInboundRoad = new InboundRoad(laneEast);

        InboundLane laneWest = new InboundLane(
                position,
                new Size(100, 50),
                speedLimit,
                FACTORY.createCarTrafficLight(position.addX(60).addY(35)),
                CardinalDirection.EAST
        );

        InboundRoad westInboundRoad = new InboundRoad(laneWest);

        PedestrianTrafficLight pedestrianTrafficLightNorth = FACTORY.createPedestrianTrafficLight(
                position.addX(60).addY(-85)
        );

        PedestrianTrafficLight pedestrianTrafficLightSOOOOS = FACTORY.createPedestrianTrafficLight(
                position.addX(100).addY(20)
        );

        Intersection spc = new SimplePedestrianCrossing( //FIXME Fixed wrong orientation
                westInboundRoad,
                eastInboundRoad,
                pedestrianTrafficLightNorth,
                pedestrianTrafficLightSOOOOS
        );

        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Create FxIntersection">

        FxIntersection fxIntersection = new FxIntersection(spc, this.getFxTrafficLightFactory(), 0.1);
        //</editor-fold>

        fxIntersections.add(fxIntersection);

//        System.out.println("fxIntersections = " + fxIntersections);

        return fxIntersection;
    }

    public abstract AbstractTrafficLightFactory getTlFactory();

    public abstract AbstractFxTrafficLightFactory getFxTrafficLightFactory();

}
