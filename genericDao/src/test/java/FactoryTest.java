import dao.AbstractDAOFactory;
import dao.DAO;
import dao.DAOConnection;
import dao.PG.PGDAOFactory;
import dao.REST.RestDAOFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class FactoryTest {
    @InjectMocks
    DAOConnection conn;

    @Before
    public void createConnection(){
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    @Test
    public void pgDaoFactoryTest() {
        AbstractDAOFactory factory = new PGDAOFactory();

        DAO dao = factory.createDao(String.class, Address.class, conn);
        Assert.assertNotNull("Dao should not be null!", dao);
    }

    @Ignore
    @Test
    public void restDaoFactoryTest() {
        AbstractDAOFactory factory = new RestDAOFactory("localhost:8080");

        DAO dao = factory.createDao(Address.class);
        Assert.assertNotNull(dao);
    }
}
