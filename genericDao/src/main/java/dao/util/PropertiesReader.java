package dao.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertiesReader {

    public static String SERVER;
    public static String PORT;
    public static String DB;
    public static String DBUSER;
    public static String DBPASSWORD;

    public static void read(String propertiesPath) {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(Files.newInputStream(Paths.get(propertiesPath))));
            SERVER = properties.getProperty("server", "localhost");
            PORT = properties.getProperty("port", "5432");
            DB = properties.getProperty("db", "postgres");
            DBUSER = properties.getProperty("dbuser", "postgres");
            DBPASSWORD = properties.getProperty("dbpassword", "postgres");
        } catch (IOException ex) {
            Logger.getLogger(PropertiesReader.class.getName()).log(Level.SEVERE, "Connection file could not be read!", ex);
        }
    }

}
