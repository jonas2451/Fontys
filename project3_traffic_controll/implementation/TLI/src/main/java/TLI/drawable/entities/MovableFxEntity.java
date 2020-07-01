package TLI.drawable.entities;

import TLI.drawable.Drawable;
import TLI.movables.MovableEntity;
import TLI.position.CardinalDirection;
import TLI.util.CanvasRotator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class MovableFxEntity implements Drawable {

    private final MovableEntity entity;
    private final Image image;
    private int imgXCorrect = 0;
    private int imgYCorrect = 0;

    public MovableFxEntity(MovableEntity entity, Image image) {
        this.entity = entity;
        this.image = image;
    }

    public MovableFxEntity(MovableEntity entity, Image image, int imgXCorrect, int imgYCorrect) {
        this.entity = entity;
        this.image = image;
        this.imgXCorrect = imgXCorrect;
        this.imgYCorrect = imgYCorrect;
    }

    public void setImgXCorrect(int imgXCorrect) {
        this.imgXCorrect = imgXCorrect;
    }

    public void setImgYCorrect(int imgYCorrect) {
        this.imgYCorrect = imgYCorrect;
    }

    @Override
    public boolean drawDynamic(GraphicsContext gc) {
        if (entity.getData().getTravelingOn() == null) {
            return false;
        } else {
            int globalX = entity.getData().getGlobalPosition().addX(imgXCorrect).getX();
            int globalY = entity.getData().getGlobalPosition().addY(imgYCorrect).getY();

            if (entity.getData().getGlobalPosition().getFacing().equals(CardinalDirection.WEST)) {
                globalX -= 70;
            }

            //FIXME {Set the correct position when rotating a car}
            switch (entity.getData().getGlobalPosition().getFacing()) {
                case NORTH:
                    CanvasRotator.drawRotatedImage(
                            gc,
                            image,
                            270,
                            globalX,
                            globalY
                    );
                    break;
                case NORTHEAST:
                    CanvasRotator.drawRotatedImage(
                            gc,
                            image,
                            320,
                            globalX,
                            globalY
                    );
                    break;
                case EAST:
                    gc.drawImage(
                            image,
                            globalX,
                            globalY);
                    break;
                case SOUTHEAST:
                    CanvasRotator.drawRotatedImageMirroredXY(
                            gc,
                            image,
                            225,
                            globalX,
                            globalY
                    );
                    break;
                case SOUTH:
                    CanvasRotator.drawRotatedImage(
                            gc,
                            image,
                            90,
                            globalX,
                            globalY
                    );
                    break;
                case SOUTHWEST:
                    CanvasRotator.drawRotatedImage(
                            gc,
                            image,
                            135,
                            globalX,
                            globalY
                    );
                    break;
                case WEST:
                    CanvasRotator.drawRotatedImageMirroredY(
                            gc,
                            image,
                            180,
                            globalX,
                            globalY
                    );
                    break;
                case NORTHWEST:
                    CanvasRotator.drawRotatedImage(
                            gc,
                            image,
                            315,
                            globalX,
                            globalY
                    );
                    break;
            }
            return true;
        }
    }

    public MovableEntity getMovable() {
        return this.entity;
    }
}
