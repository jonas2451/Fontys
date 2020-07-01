package TLI.util;

import TLI.drawable.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * @author https://stackoverflow.com/a/18262938
 * @author Jonas Terschlüsen - 423121
 */
public class CanvasRotator {

    /**
     * Sets the transform for the GraphicsContext to rotate around a pivot point.
     *
     * @param gc the graphics context the transform to applied to.
     * @param angle the angle of rotation.
     * @param px the x pivot co-ordinate for the rotation (in canvas co-ordinates).
     * @param py the y pivot co-ordinate for the rotation (in canvas co-ordinates).
     */
    public static void rotate(GraphicsContext gc, double angle, double px, double py) {
        gc.save(); // saves the current state on stack, including the current transform
        Rotate r;
        r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (tlpx, tlpy) rotated by angle pivoted around the point:
     *   (tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2)
     *
     * @param gc the graphics context the image is to be drawn on.
     * @param angle the angle of rotation.
     * @param tlpx the top left x co-ordinate where the image will be plotted (in canvas co-ordinates).
     * @param tlpy the top left y co-ordinate where the image will be plotted (in canvas co-ordinates).
     */
    public static void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }

    /**
     * Does the same but mirrored.
     * @param gc
     * @param image
     * @param angle
     * @param tlpx
     * @param tlpy
     */
    public static void drawRotatedImageMirroredY(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy+image.getHeight(), image.getHeight(), -image.getWidth());
        gc.restore(); // back to original state (before rotation)
    }

    public static void drawRotatedImageMirroredX(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy+image.getHeight(), -image.getHeight(), image.getWidth());
        gc.restore(); // back to original state (before rotation)
    }

    public static void drawRotatedImageMirroredXY(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx+image.getWidth(), tlpy+image.getHeight(), -image.getHeight(), -image.getWidth());
        gc.restore(); // back to original state (before rotation)
    }

    public static void drawRotated(GraphicsContext gc, Drawable drawable, double angle, double tlpx, double tlpy) {
        rotate(gc, angle, tlpx, tlpy);
        drawable.draw(gc);
        gc.restore();
    }
}
