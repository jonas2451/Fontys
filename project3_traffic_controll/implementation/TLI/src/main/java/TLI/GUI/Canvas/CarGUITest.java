package TLI.GUI.Canvas;

import TLI.drawable.Size;
import TLI.drawable.entities.FxCar;
import TLI.intersection.CarLane;
import TLI.movables.*;
import TLI.position.CardinalDirection;
import TLI.position.Position;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class CarGUITest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Cars");
        Group root = new Group();
        Canvas backgroundCanvas = new Canvas(600, 250);
        Canvas carCanvas = new Canvas(600, 250);

        GraphicsContext backgroundGC = backgroundCanvas.getGraphicsContext2D();
        GraphicsContext carGC = carCanvas.getGraphicsContext2D();

        //Instantiate

        CarLane lane = new CarLane(
                new Position(110, 110),
                new Size(100, 20),
                50,
                CardinalDirection.EAST
        );

        Car car = new Car(new MovableData(
                lane,
                new Position(100, 100),
                new Speed(3, Pace.FLOATING),
                new Progress()
        ));

        car.getData().getGlobalPosition().setFacing(CardinalDirection.EAST);

        FxCar fxCar = new FxCar(car, "https://img.icons8.com/cotton/64/000000/car.png");

        backgroundCanvas.getGraphicsContext2D().fillOval(100, 100, 5,5);
        SimulationLayer backgroundLayer = new SimulationLayer(backgroundCanvas);

        SimulationLayer carLayer = new SimulationLayer(carCanvas, fxCar);
        carLayer.setDynamic(true);

        FxGraphicsEngine fxGraphicsEngine = new FxGraphicsEngine(root, backgroundLayer, carLayer);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        fxGraphicsEngine.start();

        Thread thread = new Thread(car);
        thread.setDaemon(true);
        thread.start();

    }
}
