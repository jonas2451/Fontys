package TLI.drawable.entities;

import TLI.drawable.Drawable;
import TLI.intersection.Direction;
import TLI.intersection.InboundLane;
import TLI.intersection.InboundRoad;
import TLI.intersection.Travelable;
import TLI.position.CardinalDirection;
import TLI.position.Position;
import TLI.util.CanvasRotator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class FxInboundRoad implements Drawable {

    InboundRoad inboundRoad;
    Image image;
    boolean hasPedestrianCrossing = true;

    public FxInboundRoad(InboundRoad inboundRoad, boolean hasPedestrianCrossing) {
        if (hasPedestrianCrossing) {
            image = new Image("images/lane_withPedCrossing.png");
        } else {
            image = new Image("images/CarLane.png");
        }
        this.inboundRoad = inboundRoad;
        this.hasPedestrianCrossing = hasPedestrianCrossing;
    }

    @Override
    public GraphicsContext draw(GraphicsContext gc) {
        this.drawNextLanes(gc)
                .drawInboundLane(gc);
//            .drawPedestrianCrossing(gc)
        return gc;
    }

//    private FxInboundRoad drawPedestrianCrossing(GraphicsContext gc) {
//
//        int widthOfAllLanes = 0;
//        System.out.print("FxInboundRoad.drawPedestrianCrossing ");
//        for (Travelable lane : inboundRoad.getLanes()) {
//            widthOfAllLanes += lane.getSize().width;
//            System.out.println(" " + widthOfAllLanes);
//        }
//
//        Travelable mostLeftLane = inboundRoad.getLeft().get(0);
//
//        Position initialPosition = inboundRoad.getLeft().get(0).getPosition();
//        initialPosition = initialPosition.addX(mostLeftLane.getSize().getLength() - 20);
//
//        for (int i = widthOfAllLanes; i > 0; i -= 20) {
//            System.out.println("i = " + i);
//            gc.setFill(Color.WHITE);
//            gc.fillRect(initialPosition.getX(), initialPosition.getY(), 20, 4);
//            initialPosition = initialPosition.addY(10);
//        }
//        return this;
//    }

    private FxInboundRoad drawNextLanes(GraphicsContext gc) {
        for (InboundLane lane : inboundRoad.getLanes()) {
            for (Direction direction : lane.getNextLanes().keySet()) {
//                System.out.println("Making new NextLane");
                new FxLane(lane.getNextLanes().get(direction)).draw(gc);
            }
        }
        return this;
    }

    private FxInboundRoad drawInboundLane(GraphicsContext gc) {
        //TODO {Draw Inbound Lane}
//        System.out.println("\n<Drawing inbound lanes>\n");
        for (Travelable leftGoingLane : inboundRoad.getLeft()) {
            drawFacing(leftGoingLane.getFacing(), gc, leftGoingLane.getPosition());
            gc.setFill(Color.WHITE);
            gc.fillRect(leftGoingLane.getPosition().getX(), leftGoingLane.getPosition().addY(2).getY(), leftGoingLane.getSize().getWidth(), 2);
            gc.fillRect(leftGoingLane.getPosition().getX(), leftGoingLane.getPosition().addY(leftGoingLane.getSize().getHeight()).addY(-4).getY(), leftGoingLane.getSize().getWidth(), 2);
//            new FxLane(leftGoingLane).draw(gc);
        }
        for (InboundLane inboundLane : inboundRoad.getCenter()) {
            drawFacing(inboundLane.getFacing(), gc, inboundLane.getPosition());
            gc.setFill(Color.WHITE);
            gc.fillRect(inboundLane.getPosition().getX(), inboundLane.getPosition().addY(2).getY(), inboundLane.getSize().getWidth(), 2);
            gc.fillRect(inboundLane.getPosition().getX(), inboundLane.getPosition().addY(inboundLane.getSize().getHeight()).addY(-4).getY(), inboundLane.getSize().getWidth(), 2);

//            new FxLane(inboundLane).draw(gc);
        }
        for (InboundLane rightLane : inboundRoad.getRight()) {
            drawFacing(rightLane.getFacing(), gc, rightLane.getPosition());
            gc.setFill(Color.WHITE);
            gc.fillRect(rightLane.getPosition().getX(), rightLane.getPosition().addY(2).getY(), rightLane.getSize().getWidth(), 2);
            gc.fillRect(rightLane.getPosition().getX(), rightLane.getPosition().addY(rightLane.getSize().getHeight()).addY(-4).getY(), rightLane.getSize().getWidth(), 2);
//            new FxLane(rightLane).draw(gc);
        }
        return this;
    }

    private void drawFacing(CardinalDirection facing, GraphicsContext gc, Position position) {
        double angle = 0;

        switch (facing) {
            case NORTH:
                angle = 270;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case NORTHEAST:
                angle = 320;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle, //FIXME angle ist fast gleich mit northwest
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case EAST:
                gc.drawImage(image, position.getX(), position.getY());
//                this.drawPedestrianCrossing(gc);
                break;
            case SOUTHEAST:
                angle = 225;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case SOUTH:
                angle = 90;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case SOUTHWEST:
                angle = 135;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case WEST:
                angle = 180;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        180,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
            case NORTHWEST:
                angle = 315;
                CanvasRotator.drawRotatedImage(
                        gc,
                        image,
                        angle,
                        position.getX(),
                        position.getY()
                );
                this.drawRotatedPedCrossing(gc, angle, position);
                break;
        }
    }

    private void drawRotatedPedCrossing(GraphicsContext gc, double angle, Position position) {
        CanvasRotator.rotate(gc, angle, position.getX(), position.getY());
//        this.drawPedestrianCrossing(gc);
        gc.restore();
    }

    @Override
    public String toString() {
        return "FxInboundRoad{" +
                "inboundRoad=" + inboundRoad +
                ", hasPedestrianCrossing=" + hasPedestrianCrossing +
                '}';
    }
}