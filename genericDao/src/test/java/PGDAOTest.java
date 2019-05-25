import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import dao.DAOException;
import dao.PG.PGDAOFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

public class PGDAOTest {

    @Mock DAOConnection conn;
    @Mock Connection mockConn;
    DAO addressDao;

    @Before
    public void createConnection(){

        when(conn.getConnection()).thenReturn(mockConn);


        MockitoAnnotations.initMocks(this);
        AbstractDAOFactory factory = new PGDAOFactory();
        this.addressDao = factory.createDao(String.class, Address.class, conn);
    }

    @Ignore
    @Test
    public void daoSaveTest() {
        Address address = new Address(1, "Germany", "Moers", "Schillerstraße", 1);

        try {

        } catch (DAOException e) {
            fail("Exception thrown during saving!" + e.getMessage());
        }
    }
}
