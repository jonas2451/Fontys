package dao.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.joining;

/**
 *
 * A utility class that creates a local test database that can be used for testing.
 *
 * Please configure the 'CONNECTION.properties' file before you use this class!!!
 * @author Jonas Terschlüsen(j.terschlusen@student.fontys.nl)
 */
public class DbUtil {

    private static Connection ROOT_CONNECTION;
    private static String ERROR_CONNECTION =
            "Root CONNECTION could not be established. " +
                    "Please configure the 'CONNECTION.properties' file!";
    public static Connection TEST_CONNECTION;

    private static String SERVER = PropertiesReader.SERVER;
    private static String PORT = PropertiesReader.PORT;
    private static String DB = PropertiesReader.DB;
    private static String DBUSER = PropertiesReader.DBUSER;
    private static String DBPASSWORD = PropertiesReader.DBPASSWORD;


    public static void buildTestDb(String path) {

        getConnectionProperties();
        connectToRoot();

        String ddl = prepareDDl(path);
//        System.out.println("DDL: " + ddl);

        try {
            dropTestDb();
        } catch (SQLException ex) {
            //test has not run yet -> test db does not exist yet
        }

        try {
            PreparedStatement stmt = ROOT_CONNECTION.prepareStatement("CREATE DATABASE testdb;");
            System.out.println("creating the testDb...");
            stmt.execute();

            try {
                TEST_CONNECTION = DriverManager.getConnection("jdbc:postgresql://" + SERVER + ":" + PORT + "/testdb", DBUSER, DBPASSWORD);

                PreparedStatement pst = TEST_CONNECTION.prepareStatement(ddl);
                System.out.println("loading the testDb ddl-schema...");
                pst.execute();
            } catch (SQLException e) {
                Logger.getLogger("DbUtil").log(Level.SEVERE, "could not connect to created testDb.", e);
            }

            System.out.println("Test db successfully created and loaded! ");
            Logger.getLogger("DbUtil").log(Level.FINE, "Mock DB has been created! ");
        } catch (SQLException e) {
            Logger.getLogger("DbUtil").log(Level.SEVERE, ERROR_CONNECTION, e);
        }
    }

    public static void dropTestDb() throws SQLException{
        PreparedStatement stmt = ROOT_CONNECTION.prepareStatement("DROP DATABASE testdb");
        stmt.execute();
    }

    public static void closeConnection() throws SQLException{
        TEST_CONNECTION.close();
        dropTestDb();
        ROOT_CONNECTION.close();
    }

    /**
     *
     * connects to a running postgres installation.
     */
    static void connectToRoot() {
        try {
            ROOT_CONNECTION = DriverManager.getConnection
                    ("jdbc:postgresql://" + SERVER + ":" + PORT + "/" + DB, DBUSER, DBPASSWORD);
        } catch (SQLException e) {
            Logger.getLogger("DbUtil").log(Level.SEVERE, ERROR_CONNECTION, e);
        }
    }

    /**
     *
     * Reads the CONNECTION credentials from a properties file.
     * default values = root postgres user
     */
    private static void getConnectionProperties() {
        PropertiesReader.read("CONNECTION.properties");
//        Properties properties = new Properties();
//        try {
//            properties.load(new InputStreamReader(Files.newInputStream(Paths.get("CONNECTION.properties"))));
//            SERVER = properties.getProperty("server", "localhost");
//            PORT = properties.getProperty("port", "5432");
//            DB = properties.getProperty("db", "postgres");
//            DBUSER = properties.getProperty("dbuser", "postgres");
//            DBPASSWORD = properties.getProperty("dbpassword", "postgres");
//        } catch (IOException ex) {
//            Logger.getLogger("DbUtil").log(Level.SEVERE, "Connection file could not be read!", ex);
//        }
    }

    /**
     *
     * Reads a ddl-schema from a file.
     *
     * @param path postgres ddl-schema to be read
     * @return ddl as String
     */
    static String prepareDDl(String path) {
        try {
            return Files.lines(Paths.get(path))
                    .filter( l -> !l.startsWith("--"))
                    .collect(joining(System.lineSeparator()));
        } catch (IOException e) {
            Logger.getLogger("DbUtil").log(Level.SEVERE, "Could now resolve DDL script. ", e);
            return null;
        }
    }
}
