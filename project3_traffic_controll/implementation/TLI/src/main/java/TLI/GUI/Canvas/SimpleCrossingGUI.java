package TLI.GUI.Canvas;

import TLI.drawable.entities.FxIntersection;
import TLI.drawable.entities.FxLane;
import TLI.drawable.spawner.AbstractFxSpawner;
import TLI.drawable.spawner.FxCarSpawner;
import TLI.intersection.SimplePedestrianCrossing;
import TLI.intersection.Travelable;
import TLI.drawable.composer.AbstractPedCrossingFxComposer;
import TLI.drawable.composer.GermanPedCrossingFxComposer;
import TLI.position.Position;
import TLI.util.SPCConnector;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class SimpleCrossingGUI extends Application {

    private final static AbstractPedCrossingFxComposer COMPOSER = GermanPedCrossingFxComposer.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Simple Pedestrian Crossing");
        Group root = new Group();
        Canvas backgroundCanvas = new Canvas(1000, 450);
        Canvas tlCanvas = new Canvas(1000, 450);
        Canvas carCanvas = new Canvas(1000, 450);

        int speedLimit = 3;

        //<editor-fold defaultstate="collapsed" desc="Intersection Composer">
        FxIntersection eastIntersection = COMPOSER.createIntersection(new Position(500, 200), speedLimit);
        FxIntersection westIntersection = COMPOSER.createIntersection(new Position(200, 200), speedLimit);

//        System.out.println(((SimplePedestrianCrossing) westIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0));
//        System.out.println(((SimplePedestrianCrossing) eastIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0));

        SPCConnector.connectIntersectionsHorizontally(
                (SimplePedestrianCrossing) westIntersection.getIntersection(),
                (SimplePedestrianCrossing) eastIntersection.getIntersection(),
                speedLimit
        );

        Travelable t1 =
                SPCConnector.connectTravelablesHorizontally(
                        ((SimplePedestrianCrossing) westIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0),
                        new Position(0, ((SimplePedestrianCrossing) westIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0).getPosition().getY()),
                        speedLimit
                );

        FxLane f1 = new FxLane(t1);

        Travelable t2 =
                SPCConnector.connectTravelablesHorizontally(
                        ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0),
                        new Position(980, ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0).getPosition().getY()),
                        speedLimit
                );

//        System.out.println("<MAIN> next in Inbound road: " + ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getWestInboundRoad().getCenter().get(0).getNextLanes());

        FxLane f2 = new FxLane(t2);

        Travelable t3 = SPCConnector.connectTravelablesHorizontally(
                ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0),
                new Position(980, ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0).getPosition().getY()),
                speedLimit
        );

        FxLane f3 = new FxLane(t3);

        Travelable t4 = SPCConnector.connectTravelablesHorizontally(
                ((SimplePedestrianCrossing) westIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0),
                new Position(0, ((SimplePedestrianCrossing) westIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0).getPosition().getY()),
                speedLimit
        );

        FxLane f4 = new FxLane(t4);

//        SPCConnector.connectInboundLane(
//                ((SimplePedestrianCrossing) eastIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0),
//                ((SimplePedestrianCrossing) westIntersection.getIntersection()).getEastInboundRoad().getCenter().get(0)
//        );

        System.out.println("\n <<<All lanes>>>");
        System.out.println(eastIntersection.getIntersection().getInboundRoads());
        System.out.println(westIntersection.getIntersection().getInboundRoads());
        System.out.println(f1);
        System.out.println(f2);
        System.out.println("\n");

        //</editor-fold>


        //<editor-fold defaultstate="collapsed" desc="Instantiate Car">
        AbstractFxSpawner spawnerBottom = new FxCarSpawner(
                t1,
                10000,
                speedLimit,
                "https://img.icons8.com/cotton/64/000000/car.png",
                0,
                -20
        );

        Thread spawnerBottomThread = new Thread(spawnerBottom);
        spawnerBottomThread.setDaemon(true);
        spawnerBottomThread.start();

        AbstractFxSpawner spawnerTop = new FxCarSpawner(
                t3,
                9000,
                speedLimit,
                "https://img.icons8.com/cotton/64/000000/car.png",
                0,
                -20
        );

        Thread spawnerTopThread = new Thread(spawnerTop);
        spawnerTopThread.setDaemon(true);
        spawnerTopThread.start();
        //</editor-fold>


        //<editor-fold defaultstate="collapsed" desc="GraphicsEngine">
        SimulationLayer carLayer = new SimulationLayer(carCanvas, spawnerBottom.getFxEntities()).addDrawable(spawnerTop.getFxEntities());
        carLayer.setDynamic(true);

        spawnerBottom.addObserver(carLayer);
        spawnerTop.addObserver(carLayer);
        carLayer.addObserver(spawnerBottom);
        carLayer.addObserver(spawnerTop);

//        backgroundCanvas.getGraphicsContext2D().fillOval(100, 100, 5,5);
//        System.out.println("next fx lanes = " + Arrays.toString(westIntersection.getFxNextLanes()));
//        System.out.println("next fx lanes = " + Arrays.toString(eastIntersection.getFxNextLanes()));
        SimulationLayer backgroundLayer = new SimulationLayer(backgroundCanvas, eastIntersection.getFxInboundRoads())
                .addDrawable(westIntersection.getFxInboundRoads())
                .addDrawable(westIntersection.getFxNextLanes())
                .addDrawable(eastIntersection.getFxNextLanes())
                .addDrawable(f1)
                .addDrawable(f2)
                .addDrawable(f3)
                .addDrawable(f4);

        SimulationLayer tlLayer = new SimulationLayer(tlCanvas, eastIntersection.getFxTrafficLights()).addDrawable(westIntersection.getFxTrafficLights());
        tlLayer.setDynamic(true);

        FxGraphicsEngine fxGraphicsEngine = new FxGraphicsEngine(root, backgroundLayer, carLayer, tlLayer);

        //</editor-fold>


        primaryStage.setScene(new Scene(root/*, Color.BLACK*/));
        primaryStage.show();

        fxGraphicsEngine.start();


        Thread eastIntersectionThread = new Thread(eastIntersection.getIntersection());
        eastIntersectionThread.setDaemon(true);
        eastIntersectionThread.start();

        Thread westIntersectionThread = new Thread(westIntersection.getIntersection());
        westIntersectionThread.setDaemon(true);
        westIntersectionThread.start();
    }
}
