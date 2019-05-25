package dao;

import java.io.IOException;

/**
 *
 * An exception that can be thrown in a DAO.
 */
public class DAOException extends RuntimeException{

    public DAOException(String message, Throwable exception){
        super(message, exception);
    }

    public DAOException(String message) {
        super(message);
    }
}
