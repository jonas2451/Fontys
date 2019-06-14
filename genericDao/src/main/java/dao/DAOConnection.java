package dao;

import dao.util.PropertiesReader;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * A Connection type, accepted by the dao classes.
 */
public abstract class DAOConnection implements AutoCloseable {

    public static String SERVER = PropertiesReader.SERVER;
    public static String PORT = PropertiesReader.PORT;
    public static String DB = PropertiesReader.DB;
    public static String DBUSER = PropertiesReader.DBUSER;
    public static String DBPASSWORD = PropertiesReader.DBPASSWORD;
    public static Connection CONNECTION;

    /**
     * Default constructor that expects a CONNECTION.properties file in the root project direction.
     */
    protected DAOConnection() {
        this("CONNECTION.properties");
    }

    protected DAOConnection(String path) {
        PropertiesReader.read(path);
        SERVER = PropertiesReader.SERVER;
        PORT = PropertiesReader.PORT;
        DB = PropertiesReader.DB;
        DBUSER = PropertiesReader.DBUSER;
        DBPASSWORD = PropertiesReader.DBPASSWORD;
    }

    /**
     * connects to the given database.
     */
    public abstract DAOConnection connect() throws SQLException;

    public Connection getConnection() {
        return CONNECTION;
    };

    /**
     * closes the existing db CONNECTION.
     *
     * @throws SQLException when the CONNECTION is already closed
     * @since V3.9
     */
    @Override
    public void close() throws SQLException {this.getConnection().close();};
}
