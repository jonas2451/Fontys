package dao.PG.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * helper Object, to execute Queries
 */
public class PgJDBC {

    /**
     *
     * use this method to execute Queries
     * (will currently print a message, when an update or insert statement is executed)
     *
     * @param conn Connection to the database
     * @param query A String containing the Query
     * @return returns a result set of the query's results. Can be null
     */
    public static ResultSet doQuery(Connection conn, String query){

        try {
            Statement stmt = conn.createStatement();
            ResultSet out = stmt.executeQuery(query);

            return out;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
