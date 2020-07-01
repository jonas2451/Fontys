package helper;

import dao.DAOConnection;
import dao.Entity1;
import dao.util.DbUtil;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestPGDbConnection extends DAOConnection {

    public TestPGDbConnection() {

        DbUtil.buildTestDb("src/test/java/pg/testDbSchema.sql");
    }

    @Override
    public DAOConnection connect() throws SQLException{
        CONNECTION = DriverManager.getConnection("jdbc:postgresql://" + SERVER + ":" + PORT + "/testdb", DBUSER, DBPASSWORD);
        return this;
    }

    public ResultSet doDirectQuery(String query) throws SQLException {
        this.connect();
        PreparedStatement pst = CONNECTION.prepareStatement(query);
        return pst.executeQuery();
    }
}

