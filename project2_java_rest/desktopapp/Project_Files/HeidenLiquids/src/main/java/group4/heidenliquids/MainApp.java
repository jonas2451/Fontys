package group4.heidenliquids;

import dao.AbstractDAOFactory;
import dao.PG.PGDAOFactory;
import dao.REST.RestDAOFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;


public class MainApp extends Application {

    private Connection conn;

    private static final ControllerFactory CONTROLLER_FACTORY = new ControllerFactory(new RestDAOFactory("http://127.0.0.1:8080/webapi/"));

    @Override
    public void start(Stage stage) throws Exception {

        URL url = getClass().getResource("/fxml/Scene.fxml");

        //load the GUI
        FXMLLoader loader = new FXMLLoader( url );

        loader.setControllerFactory( CONTROLLER_FACTORY );

        Parent root = loader.load();



        //Show the GUI
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("Heiden Liquids Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
