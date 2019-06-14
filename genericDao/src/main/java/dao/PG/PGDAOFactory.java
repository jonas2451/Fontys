package dao.PG;

import dao.AbstractDAOFactory;
import dao.DAO;
import dao.Entity1;
import dao.DAOConnection;
import dao.mapper.PGMapper;

import java.io.Serializable;

/**
 *
 * A Factory to create PGDAOs.
 */
public class PGDAOFactory extends AbstractDAOFactory {
    @Override
    public <K extends Serializable, E extends Entity1<K>> DAO<K, E> createDao(Class<? extends Serializable> s, Class<E> var, DAOConnection daoConnection) {
        PGMapper<K, E> mapper = new PGMapper(s, var, daoConnection);
        System.out.println("\n<<<Created new PGDAO for " + var + ">>> \n");
        return new PGDAO<K, E>(mapper, daoConnection);
    }
}
