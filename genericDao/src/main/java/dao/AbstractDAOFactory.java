package dao;

import dao.mapper.Mapper;

import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

public abstract class AbstractDAOFactory {
    protected final HashMap<Class<? extends Entity1>, Mapper<? extends Serializable, ? extends Entity1>> mappers = new HashMap();

    public AbstractDAOFactory() {
    }

    public abstract <K extends Serializable, E extends Entity1<K>> DAO<K, E> createDao(Class<? extends Serializable> s, Class<E> var1, Connection connection);
}
