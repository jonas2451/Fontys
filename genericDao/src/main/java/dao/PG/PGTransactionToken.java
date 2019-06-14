package dao.PG;

import dao.TransactionToken;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PGTransactionToken implements TransactionToken {

    private final Connection connection;

    public PGTransactionToken(Connection connection) {
        this.connection = connection;
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Could not disable auto commit", ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void commit() throws Exception {
        this.connection.commit();
    }

    @Override
    public void rollback() throws Exception {
        this.connection.rollback();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
