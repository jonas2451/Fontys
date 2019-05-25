package dao;

/**
 *
 * A Connection type, accepted by the dao classes.
 */
public interface DAOConnection {
    java.sql.Connection getConnection();
}
