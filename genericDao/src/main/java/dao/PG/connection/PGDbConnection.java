package dao.PG.connection;


import dao.DAOConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * A connection class for a postgres database on ports 5432 or 5433.
 */
public class PGDbConnection implements DAOConnection {

    Connection connection;

    public PGDbConnection() {

        String dbURL = "jdbc:postgresql://localhost:5432/heiden";
        String userName = "postgres";
        String password = "mypassword";

        try {
            this.connection = DriverManager.getConnection(dbURL, userName, password);
            Logger.getLogger( PGDbConnection.class.getName() ).log( Level.FINE, "Connected to: " + dbURL);
        } catch (SQLException ex) {
            Logger.getLogger(PGDbConnection.class.getName()).log(Level.WARNING, "Failed to connect to " + dbURL, ex);
        }
    }

    /**
     *
     * @return an SQLConnection
     */
    public Connection getConnection() {
        return connection;
    }
}
