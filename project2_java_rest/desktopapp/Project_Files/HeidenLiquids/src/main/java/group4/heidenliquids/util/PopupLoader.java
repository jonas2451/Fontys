package group4.heidenliquids.util;

import dao.AbstractDAOFactory;
import dao.Entity1;
import group4.heidenliquids.orderProduct.OrderProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

public class PopupLoader {

    public void load(String uri, Window parent, Callback<Class<?>, Object> factory) throws IOException{
        Parent root;

        URL url = getClass().getResource(uri);

        FXMLLoader loader = new FXMLLoader(url);



        loader.setControllerFactory(factory);

        root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("popup");

        // Specifies the modality for new scene.
        stage.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new scene
        stage.initOwner(parent);

        // Set position of second scene, related to primary scene.
        stage.setX(parent.getX() + 50);
        stage.setY(parent.getY() + 50);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
