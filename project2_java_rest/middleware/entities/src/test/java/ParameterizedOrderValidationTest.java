import entities.Action;
import entities.Order;
import entities.OrderProduct;
import exceptions.ActionAmountException;
import exceptions.ActionWeightException;
import exceptions.OrderDateException;
import exceptions.OrderProductWeightException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class ParameterizedOrderValidationTest {

    final TD myData;
    final

    static class TD {
        final Class<? extends Throwable> exception;
        final Order order;

        TD(Class<? extends Throwable> exception, Order order) {
            this.exception = exception;
            this.order = order;
        }
    }

//<editor-fold defaultstate="collapsed" desc="Test-Data">

    static TD[] exceptionTestData = {

            // Order date is in the past.
            // OrderProducts are correct.
            // Action weight is not needed, since the order date test should fail!
            new TD(OrderDateException.class,
                    new Order("1", LocalDate.now().minusDays(1), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(10), BigDecimal.valueOf(1), "1")
                                    .addAction(
                                            new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1")
                                    )
                    )),

            // Order date is over a year in the future.
            // OrderProducts are correct.
            // Action weight is not needed, since the order date test should fail!
            new TD(OrderDateException.class,
                    new Order("1", LocalDate.now().plusYears(1).plusDays(1), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(10), BigDecimal.valueOf(1), "1")
                                    .addAction(
                                            new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1")
                                    )
                    )),

            // Order date is valid.
            // OrderProduct weight does not match the total action weight.
            // Action weights match up
            // Actions have unloading and loading
            new TD(OrderProductWeightException.class,
                    new Order("1", LocalDate.now(), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(5), BigDecimal.valueOf(1), "1")
                                    .addAction(
                                            new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1")
                                    )
                                    .addAction(
                                            new Action("2", 0, true, BigDecimal.valueOf(10), "1", "2")
                                    )
                    )),

            // Order date is valid.
            // OrderProduct weight is valid.
            // Action weights do not match up
            // Actions have unloading and loading
            new TD(ActionWeightException.class,
                    new Order("1", LocalDate.now(), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(10), BigDecimal.valueOf(1), "1")
                                    .addAction(
                                            new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1")
                                    )
                                    .addAction(
                                            new Action("2", 0, true, BigDecimal.valueOf(5), "1", "2")
                                    )
                    )),

            // Order date is valid.
            // OrderProduct weight is valid.
            // Action weights do not match up
            // Actions have unloading only
            new TD(ActionAmountException.class,
                    new Order("1", LocalDate.now(), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(15), BigDecimal.valueOf(1), "1")
                                    .addAction(
                                            new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1")
                                    )
                                    .addAction(
                                            new Action("2", 0, false, BigDecimal.valueOf(5), "1", "2")
                                    )
                    )),

            //Valid examples:

            // Order date is exactly a year in the future.
            // OrderProducts are correct.
            // Action weight is not needed, since the order date test should fail!
            new TD(null,
                    new Order("1", LocalDate.now().plusYears(1), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(10), BigDecimal.valueOf(1), "1")
                                    .addAction(new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1"))
                                    .addAction(new Action("1", 0, true, BigDecimal.valueOf(10), "1", "1"))
                    )),

            // Order date is valid.
            // OrderProduct weight is valid.
            // Action weights are valid
            // Actions are valid
            new TD(null,
                    new Order("1", LocalDate.now().plusMonths(4), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(15), BigDecimal.valueOf(1), "1")
                                    .addAction(new Action("1", 0, true, BigDecimal.valueOf(10), "1", "1"))
                                    .addAction(new Action("2", 0, false, BigDecimal.valueOf(10), "1", "2"))
                                    .addAction(new Action("3", 0, false, BigDecimal.valueOf(5), "1", "3"))
                                    .addAction(new Action("4", 0, true, BigDecimal.valueOf(5), "1", "4"))
                    )),

            // Order date is valid.
            // OrderProduct weight is valid.
            // Action weights are valid
            // Actions are valid, multiple uneven actions
            new TD(null,
                    new Order("1", LocalDate.now().plusMonths(4), "1").addOrderProduct(
                            new OrderProduct("1", "Milk", false, BigDecimal.valueOf(10), BigDecimal.valueOf(1), "1")
                                    .addAction(new Action("1", 0, false, BigDecimal.valueOf(10), "1", "1"))
                                    .addAction(new Action("2", 0, true, BigDecimal.valueOf(5), "1", "2"))
                                    .addAction(new Action("3", 0, true, BigDecimal.valueOf(5), "1", "3"))
                    )),
    };

//</editor-fold>

    @Parameterized.Parameters
    public static TD[] data() {
        return exceptionTestData;
    }

    public ParameterizedOrderValidationTest(TD exceptionData) {
        this.myData = exceptionData;
    }

    @Test
    public void exceptionTest() {
        try {
            System.out.println(myData.order);
            Assertions.assertThrows(myData.exception, myData.order::validateOrder);
        } catch (NullPointerException ex) {
            myData.order.validateOrder();
            if (myData.exception != null) {
                fail("Something went wrong. \n" + ex);
            }
        }
    }
}
