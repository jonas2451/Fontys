import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MockDbConnection {


    public class DBConnection {

        private Connection dbConnection;

        public void getDBConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:postgres://localhost:5433/heiden", "postgres", "mypassword");
        }

        public int executeQuery(String query) throws ClassNotFoundException, SQLException {
            return dbConnection.createStatement().executeUpdate(query);
        }
    }
}
