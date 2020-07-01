package exceptions;

/**
 * Thrown when there are loading / unloading actions only.
 */
public class ActionAmountException extends IllegalArgumentException {
    public ActionAmountException() {
        this("");
    }

    public ActionAmountException(String s) {
        super("All of orderProducts should contain at least one loading and unloading action! Order Product: " + s);
    }
}
