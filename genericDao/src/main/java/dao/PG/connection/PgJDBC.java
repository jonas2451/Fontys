package dao.PG.connection;

import java.sql.*;

/**
 *
 * helper class to execute Queries
 */
public class PgJDBC {

    /**
     *
     * use this method to execute Queries
     * (will currently print a message, when an update or insert statement is executed)
     *
     * @param conn DAOConnection to the database
     * @param query A String containing the Query
     * @return returns a result set of the query's results. Can be null
     */
    public static ResultSet doQuery(Connection conn, String query){

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet out = stmt.executeQuery();
            System.out.println("<PgJDBC> DAOConnection: " + conn);
            return out;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
