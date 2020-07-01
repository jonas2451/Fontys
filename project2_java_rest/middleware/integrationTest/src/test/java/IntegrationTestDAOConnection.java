import dao.DAOConnection;
import dao.PG.connection.PgJDBC;
import dao.util.DbUtil;
import dao.util.PropertiesReader;

import java.sql.*;

public class IntegrationTestDAOConnection extends DAOConnection {

    public IntegrationTestDAOConnection() {
        PropertiesReader.read("connection.properties");
        DbUtil.buildTestDb("src/test/java/heidenliquids.sql");
    }

    @Override
    public DAOConnection connect() throws SQLException {
        CONNECTION = DriverManager.getConnection("jdbc:postgresql://" + SERVER + ":" + PORT + "/testdb", DBUSER, DBPASSWORD);
        return this;
    }

    public ResultSet executeQuery(String query) throws SQLException{
        if (CONNECTION.isClosed()) {
            this.connect();
        }
        try (Connection connection = CONNECTION) {
            return PgJDBC.doQuery(CONNECTION, query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
