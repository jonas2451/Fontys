package TLI.drawable.entities;

import TLI.movables.Car;
import javafx.scene.image.Image;

public class FxCar extends MovableFxEntity {

    static int id = 0;

    public FxCar(Car car, String imageLocation) {
        super(car, new Image(imageLocation));
        id++;
    }

    @Override
    public String toString() {
        return "FxCar{"+id+"}";
    }
}