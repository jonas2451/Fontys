package dao.PG.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * creates a CONNECTION to a postgres database
 */
@Deprecated
public class Connect {

    /**
     *
     * creates a new Connection to a postgres Database
     * @return Connection please save the returned CONNECTION into a local variable
     * @throws SQLException when the CONNECTION fails
     */
    @Deprecated
    public static Connection createConnection() throws SQLException{
        try {
            String dbURL = "jdbc:postgresql://localhost:5432/heiden";
            String userName = "postgres";
            String password = "mypassword";

            Connection dbConnection = DriverManager.getConnection(dbURL, userName, password);

            System.out.println("Connected!");

            return dbConnection;
        } catch (SQLException e) {
            //<editor-fold defaultstate="collapsed" desc="Alternative Connection">
//            try {
//
//                Connection dbConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/heiden", "postgres", "mypassword");
//
//                System.out.println("Connected!");
//
//                return dbConnection;
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
            //</editor-fold>
            System.out.println(e.getMessage());
        }

        return null;
    }
}
