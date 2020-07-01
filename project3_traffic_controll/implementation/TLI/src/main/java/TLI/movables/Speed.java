package TLI.movables;

/**
 * Abstract datatype to describe the movement of a movable entity.
 */
public class Speed {

    private int ppt;
    private Pace pace;

    /**
     *
     * @param ppt pixels per tick
     * @param pace of the moving object
     */
    public Speed(int ppt, Pace pace) {
        this.ppt = ppt;
        this.pace = pace;
    }

    public int getPpt() {
        return ppt;
    }

    public void setPpt(int ppt) {
        this.ppt = ppt;
    }

    public Pace getPace() {
        return pace;
    }

    public void setPace(Pace pace) {
        this.pace = pace;
    }
}
