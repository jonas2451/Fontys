package TLI.drawable;

/**
 * An abstract datatype to keep track of the size of drawables;
 */
public class Size {

    int height;
    int width;

    /**
     *
     * @param width in px
     * @param height in px
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @return the length of a drawable in px.
     */
    public int getLength() {
        return Math.max(height, width);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "Size{" +
                "height=" + height +
                ", width=" + width +
                '}';
    }
}