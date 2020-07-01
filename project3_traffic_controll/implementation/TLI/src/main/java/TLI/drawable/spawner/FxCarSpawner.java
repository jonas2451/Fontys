package TLI.drawable.spawner;

import TLI.drawable.entities.FxCar;
import TLI.intersection.Travelable;
import TLI.movables.*;

public class FxCarSpawner extends AbstractFxSpawner {

    private int carSpeed;
    private String imgLocation;
    private int imgXCorrect;
    private int imgYCorrect;

    public FxCarSpawner(Travelable travelable, int spawnRate, int carSpeed, String imgLocation) {
        super(spawnRate, travelable);
        this.carSpeed = carSpeed;
        this.imgLocation = imgLocation;
    }

    public FxCarSpawner(Travelable travelable, int spawnRate, int carSpeed, String imgLocation, int imgXCorrect, int imgYCorrect) {
        super(spawnRate, travelable);
        this.carSpeed = carSpeed;
        this.imgLocation = imgLocation;
        this.imgXCorrect = imgXCorrect;
        this.imgYCorrect = imgYCorrect;
    }

    @Override
    public void spawn() {
        //FIXME the travelable object is modified when modifying the travelable of the car (same reference)

        MovableData data = new MovableData(
                super.travelable,
                new Speed(carSpeed, Pace.ACCELERATING),
                new Progress(carSpeed, travelable.getSize())
        );

        Car car = new Car(data);
        travelable.addEntity(car);

        FxCar fxCar = new FxCar(car, imgLocation);
        fxCar.setImgYCorrect(imgYCorrect);
        fxCar.setImgXCorrect(imgXCorrect);
        super.entities.add(fxCar);

        System.out.println("Spawned = " + fxCar + "; on " + travelable + "; at " + fxCar.getMovable().getData().getGlobalPosition());

        Thread carThread = new Thread(car);
        carThread.start();
        super.notifyObservers();
    }
}
