package exceptions;


/**
 * thrown when the unloading and loading weight of an action don't match up.
 */
public class ActionWeightException extends IllegalArgumentException{
    public ActionWeightException() {
        this("");
    }

    public ActionWeightException(String s) {
        super("The unloading and loading weight of the actions does not match up! Order Product: " + s);
    }
}
