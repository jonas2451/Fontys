package dao;

public interface TransactionToken extends AutoCloseable {

    void commit() throws Exception;

    void rollback() throws Exception;

    @Override
    void close() throws Exception;
}
