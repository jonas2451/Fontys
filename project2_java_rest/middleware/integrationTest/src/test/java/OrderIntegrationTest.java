import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AbstractDAOFactory;
import dao.DAOConnection;
import dao.PG.PGDAOFactory;
import dao.REST.LocalDateJsonAdapter;
import dao.util.DbUtil;
import entities.Action;
import entities.Address;
import entities.Order;
import entities.OrderProduct;
import group4.heidenliquids.ControllerFactory;
import group4.heidenliquids.order.OrderController;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.*;
import org.mockito.internal.matchers.Or;

import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

public class OrderIntegrationTest {

    static DAOConnection conn;

    private final static Order ORDER =
            new Order("3", LocalDate.now().plusMonths(4), "1").addOrderProduct(
                new OrderProduct("3", "Milk", false, BigDecimal.valueOf(15), BigDecimal.valueOf(1), "3")
                    .addAction(new Action("1", 0, true, BigDecimal.valueOf(10), "3", "1"))
                    .addAction(new Action("2", 0, false, BigDecimal.valueOf(10), "3", "2"))
                    .addAction(new Action("3", 0, false, BigDecimal.valueOf(5), "3", "3"))
                    .addAction(new Action("4", 0, true, BigDecimal.valueOf(5), "3", "4"))
                );

    final static Gson GSON =
            new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
                    .excludeFieldsWithModifiers(Modifier.STATIC)
                    .create();

    @BeforeClass
    public static void setUp() {
        conn = new IntegrationTestDAOConnection();
        baseURI = "http://localhost/webapi";
        port = 8080;
    }

    @Test
    public void saveTest() {

        String requestBody = GSON.toJson(ORDER);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("order");

        assertEquals("Status code was not okay.", 200, response.getStatusCode());
        Order newOrder = GSON.fromJson(response.asString(), Order.class);
        assertEquals("Returned order is not equal to the saved order!", ORDER, newOrder);
    }

    @Test
    public void getAllTest() {
        final String uri = baseURI + "/address";

        Address address = new Address("1", "Germany", "Dortmund", "44143", "Klönnestraße", "89");

        Response response = RestAssured.given().get(uri);
        Assert.assertEquals("Status code was not OK.", 200, response.getStatusCode());

        RestAssured.given()
                .get(uri)
                .then()
                .body("country", hasItem(address.getCountry()));
//                .assertThat().content(hasItem(address));
    }

    @Test
    public void getTest() {
        Response response = RestAssured.given().get("order/3");
        assertEquals("Response code was not OK. \n" + response.asString(), 200, response.getStatusCode());
        Order newOrder = GSON.fromJson(response.asString(), Order.class);
        assertEquals("Did not return the associated order!", newOrder, ORDER);
    }

    @Test
    public void deleteTest() {
        final String uri = baseURI;

        String responseBody = GSON.toJson(ORDER);

        Response deleteResponse = RestAssured.given().delete("order/3");
        assertEquals("Status code was not OK.", 200, deleteResponse.getStatusCode());

        Response getResponse = RestAssured.given().get("order/3");
        assertEquals("Should not be found!", 404, getResponse.getStatusCode());
    }

    @AfterClass
    public static void tearDown() throws SQLException, InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        DbUtil.closeConnection();
    }
}
