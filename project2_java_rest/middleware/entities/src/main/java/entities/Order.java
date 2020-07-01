package entities;

import dao.Entity1;
import exceptions.ActionAmountException;
import exceptions.ActionWeightException;
import exceptions.OrderDateException;
import exceptions.OrderProductWeightException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor

public class Order implements Entity1<String> {

    String id;
    LocalDate orderDate;
    String customerid;
    transient ArrayList<OrderProduct> orderProducts = new ArrayList<>();

    public Order(String id, LocalDate orderDate, String customerid) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerid = customerid;
        this.orderProducts = new ArrayList<>();
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public ArrayList<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Order setOrderProducts(ArrayList<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
        return this;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }

    public Order addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        return this;
    }

    @Override
    public String toString() {
        return orderDate + ", customerId = " + customerid + ", " + orderProducts + " | id: " + id;
    }


//    @Override
//    public String toString() {
//        return "Order{" +
//                "id='" + id + '\'' +
//                ", orderDate=" + orderDate +
//                ", customerid='" + customerid + '\'' +
//                ", orderProducts=" + orderProducts +
//                '}';
//    }

    /**
     * validates an order, its products, its actions.
     */
    public Order validateOrder(){
        Order order = this;
        if (order.getOrderDate().isBefore(LocalDate.now())) {
            throw new OrderDateException();
        } else if (order.getOrderDate().isAfter(LocalDate.now().plusYears(1))) {
            throw new OrderDateException("The orderDate should not be more than a year in the future!");
        }
        if (!order.getOrderProducts().isEmpty())
            this.validateOrderProducts(order.getOrderProducts());
        return this;
    }

    /**
     * called by validateOrder().
     * @param orderProducts to be validated.
     */
    private void validateOrderProducts(ArrayList<OrderProduct> orderProducts) {

        for (OrderProduct o : orderProducts) {
            BigDecimal loadingWeight = BigDecimal.ZERO;
            BigDecimal  unloadingWeight = BigDecimal.ZERO;


            for (Action a : o.getActions()) {
                if (!a.getIsUnloading()) {
                    loadingWeight = loadingWeight.add(a.getExpectedWeight());
                } else {
                    unloadingWeight = unloadingWeight.add(a.getExpectedWeight());
                }
            }


            if (!o.getTotalWeight().equals(loadingWeight))
                throw new OrderProductWeightException(o.name);

            if (loadingWeight.equals(BigDecimal.ZERO) || unloadingWeight.equals(BigDecimal.ZERO))
                throw new ActionAmountException(o.name);

            if (!loadingWeight.abs().equals(unloadingWeight.abs()))
                throw new ActionWeightException(o.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(customerid, order.customerid) &&
                Objects.equals(orderProducts, order.orderProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, customerid, orderProducts);
    }
}
