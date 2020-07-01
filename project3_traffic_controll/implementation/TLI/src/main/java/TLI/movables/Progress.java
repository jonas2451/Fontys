package TLI.movables;

import TLI.drawable.Size;

/**
 * An abstract datatype to model the progress of a MovableEntity on a Travelable.
 */
public class Progress {

    int progress = 0;
    double relativeProgress = 0;

    public Progress(int progress, Size size) {
        this.progress = progress;
        this.relativeProgress = size.getLength() / progress;
    }

    public Progress() {
    }

    public Progress proceed(Speed speed, Size size) {
        this.progress += speed.getPpt();
        this.relativeProgress = ((double) this.progress / (double)size.getLength()) * 100;
//        System.out.println("size = " + size.getLength());
//        System.out.println("progress = " + progress);
//        System.out.println(relativeProgress);
        return this;
    }

    public int getProgress() {
        return progress;
    }

    /**
     *
     * @param progress
     * @param size Of a Travelable. (Required to calculate the relative progress on a path.)
     */
    public void setProgress(int progress, Size size) {
        this.progress = progress;
    }

    public double getRelativeProgress() {
        return relativeProgress;
    }

    public void setRelativeProgress(int relativeProgress) {
        this.relativeProgress = relativeProgress;
    }
}