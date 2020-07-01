package exceptions;


/**
 *  Thrown when the loading weight of all actions for one orderProduct and the total weight of the orderProduct mismatch.
 */
public class OrderProductWeightException extends IllegalArgumentException{
    public OrderProductWeightException() {
        this("");
    }

    public OrderProductWeightException(String s) {
        super("The loading weight of the actions and the total weight of the orderProduct mismatch! Order Product: " + s);
    }
}

