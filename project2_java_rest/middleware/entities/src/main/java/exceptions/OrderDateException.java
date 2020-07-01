package exceptions;


/**
 * Thrown when the order date is invalid.
 */
public class OrderDateException extends IllegalArgumentException {
    public OrderDateException() {
        this("");
    }

    public OrderDateException(String s) {
        super("The order date cannot be in the past!" + s);
    }
}
