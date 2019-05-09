package dao.PG;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.Entity1;
import dao.mapper.PGMapper;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Connection;

public class PGDAOFactory extends AbstractDAOFactory {
    @Override
    public <K extends Serializable, E extends Entity1<K>> DAO<K, E> createDao(Class<? extends Serializable> s, Class<E> var, Connection connection) {

        try {
            Method method = var.getMethod("getKey", String.class);
            PGMapper<K, E> mapper = new PGMapper(s, var.getClass(), connection);
            return new PGDAO<K, E>(mapper, connection);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
