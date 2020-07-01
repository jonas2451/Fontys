import dao.DAO;
import dao.DAOConnection;
import dao.DAOException;
import dao.PG.PGDAOFactory;
import dao.util.DbUtil;
import helper.TestPGDbConnection;
import junit.extensions.TestDecorator;
import org.junit.*;
import testEntities.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class PGDAOTest {

//    @Mock DAOConnection CONNECTION;
    TestPGDbConnection CONNECTION;
    PGDAOFactory factory = new PGDAOFactory();

    @Before
    public void createConnection(){
        CONNECTION = new TestPGDbConnection();
    }

    @Test
    public void connectionTest() {
        try(Connection connection = CONNECTION.connect().getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("select * from addresss;");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            System.out.println(rs.getString("id"));
        } catch (SQLException ex) {
            fail("CONNECTION could not be established!");
        }
    }

    @Test
    public void get_retrieves_object_from_the_database() {
        DAO<String, Address> dao = factory.createDao(String.class, Address.class, CONNECTION);

        Optional address = dao.get("123einszweidrei");
        assertTrue("No object has been retrieved from the database!", address.isPresent());
    }

    @Test
    public void save_returns_object_from_the_database_Test() {

        DAO<String, Address> dao = factory.createDao(String.class, Address.class, CONNECTION);

        Address address = new Address("456vierfünfsechs", "Germany", "Moers", "Schillerstraße", 1);
        try {
            Address newAddress = (Address) dao.save(address);
            assertEquals("The returned address should contain the same information like the saved address!", address.toString(), newAddress.toString());
            assertNotEquals("The returned address should not be the same object, like the saved address!", address, newAddress);
        } catch (DAOException ex) {
            fail("An exception occured." + ex);
        }
    }

    @Test
    public void saved_object_is_actually_in_the_database_Test() {
        DAO<String, Address> dao = factory.createDao(String.class, Address.class, CONNECTION);

        Address address = new Address("123456789", "Fantisy Land", "Fenlo", "Fantysstreet", 255);

        try {
            dao.save(address);
        } catch (DAOException ex) {
            fail("Exception has bee thrown! Exception: " + ex.getMessage());
        }

        try(TestPGDbConnection daoConnection = CONNECTION) {
            ResultSet rs = daoConnection.doDirectQuery("select * from addresss where id = '123456789';");
            rs.next();
            Address databaseAddress = new Address(
                    rs.getString("id"),
                    rs.getString("country"),
                    rs.getString("city"),
                    rs.getString("street"),
                    rs.getInt("number"));
            assertEquals("To-be-saved address != address in the database", databaseAddress.getId(), address.getId());

        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "An SQLError has occured. ", ex);
        }
    }

    @Test
    public void delete_deletes_object_actually_from_the_database() {

        DAO<String, Address> dao = factory.createDao(String.class, Address.class, CONNECTION);

        Address address = new Address("123einszweidrei", "Germany", "Moers", "DbStreet", 1);

        dao.delete(address);

        try (TestPGDbConnection daoConnection = CONNECTION){
            ResultSet rs = daoConnection.doDirectQuery("select * from addresss where id = '123einszweidrei';");
            if (rs.next()) {
                fail("Object should be deleted!");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() throws SQLException{
        DbUtil.closeConnection();
    }
}
