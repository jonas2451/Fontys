package TLI.intersection.Factory;

@Deprecated
public class CarPedIntersectionFactory extends AbstractIntersectionFactory {

    private CarPedIntersectionFactory() {
    }

    public static synchronized CarPedIntersectionFactory getInstance() {
        if (FACTORY != null) return (CarPedIntersectionFactory) FACTORY;
        else {
            FACTORY = new CarPedIntersectionFactory();
            return (CarPedIntersectionFactory) FACTORY;
        }
    };

//    public Intersection createSimplePedCrossing(Position westCarLanePosition, Position eastCarLanePosition, TrafficLight leftCarLight, TrafficLight rightCarLight, Position northPedestrianLight, Position southPedestrianLight, DutchTrafficLightFactory dutchTLFactory ) {
//        InboundLane leftLane = new InboundLane(westCarLanePosition, new Size(50, 100), 50, leftCarLight);
//        InboundRoad westInboundRoad = new InboundRoad(null, leftLane, null, leftCarLight);
//
//        InboundLane rightLane = new InboundLane(eastCarLanePosition, new Size(50, 100), 50, rightCarLight);
//        InboundRoad eastInboundRoad = new InboundRoad(null, rightLane, null, rightCarLight);
//
//        return new SimplePedestrianCrossing(westInboundRoad,eastInboundRoad, dutchTLFactory.createPedestrianTrafficLight(northPedestrianLight), dutchTLFactory.createPedestrianTrafficLight(southPedestrianLight));
//    }
}
