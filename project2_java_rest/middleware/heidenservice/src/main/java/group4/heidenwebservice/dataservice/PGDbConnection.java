package group4.heidenwebservice.dataservice;


import dao.DAOConnection;
import dao.util.PropertiesReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * A connection class for a postgres database on ports 5432 or 5433.
 */
public class PGDbConnection extends DAOConnection {

    public PGDbConnection() {
        super("connection.properties");
    }

    @Override
    public DAOConnection connect() throws SQLException {
        CONNECTION = DriverManager.getConnection("jdbc:postgresql://" + SERVER + ":" + PORT + "/" + DB + "", DBUSER, DBPASSWORD);
        return this;
    }
}
