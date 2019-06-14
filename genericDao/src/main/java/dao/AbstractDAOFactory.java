package dao;

import dao.mapper.Mapper;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * An abstract factory class.
 */
public abstract class AbstractDAOFactory {
    protected final HashMap<Class<? extends Entity1>, Mapper<? extends Serializable, ? extends Entity1>> mappers = new HashMap();

    public AbstractDAOFactory() {
    }

    /**
     * Used by the PGDAOFactory to create DAOs.
     *
     * @param s the key type of the PGDAO
     * @param var1 entity class type.
     * @param daoConnection A CONNECTION to a relational database
     * @return a DAO implementation, specified by the implementing class
     */
    public <K extends Serializable, E extends Entity1<K>> DAO<K, E> createDao(Class<? extends Serializable> s, Class<E> var1, DAOConnection daoConnection){
        throw new UnsupportedOperationException("only implemented for the PGDAOFactory");
    };

    /**
     * Used by the RestDAOFactory to create DAOs.
     *
     * @param var1 entity class type
     * @return a DAO implementation, specified by the implementing class
     */
    public <K extends Serializable, E extends Entity1<K>> DAO<K, E> createDao(Class<E> var1){
        throw new UnsupportedOperationException("only implemented for the RestDAOFactory");
    };
}
