package TLI.movables;

import TLI.drawable.Drawable;
import TLI.drawable.entities.FxCar;
import TLI.intersection.Travelable;

import java.util.HashSet;
import java.util.Set;

@Deprecated
public class FxCarComposer {

    static FxCarComposer COMPOSER;
    Set<FxCar> fxCars = new HashSet<>();

    public static synchronized FxCarComposer getInstance() {
        if (COMPOSER != null) return COMPOSER;
        else {
            COMPOSER = new FxCarComposer();
            return COMPOSER;
        }
    }

    private FxCarComposer() {
    }

    public Drawable createCar(Travelable travelable, String imagePath) {
        MovableData data = new MovableData(
                travelable,
                travelable.getSpawnPosition(),
                new Speed(0, Pace.ACCELERATING),
                new Progress()
        );
        Car car = new Car(data);
        FxCar fxCar = new FxCar(car, imagePath);

        this.fxCars.add(fxCar);

        return fxCar;
    }

    public Drawable[] getCars() {
        return (Drawable[]) fxCars.toArray();
    }
}
