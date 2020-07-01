package entities;


import dao.Entity1;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class OrderProduct implements Entity1<String> {

    //@ID
    String id;
    String name;
    boolean hazardous;
    BigDecimal totalWeight;
    BigDecimal pricePerTon;
    String orderid;
    transient ArrayList<Action> actions = new ArrayList<>();

    public OrderProduct(String id, String name, boolean hazardous, BigDecimal totalWeight, BigDecimal pricePerTon, String orderid) {
        this.id = id;
        this.name = name;
        this.hazardous = hazardous;
        this.totalWeight = totalWeight;
        this.pricePerTon = pricePerTon;
        this.orderid = orderid;
        this.actions = new ArrayList<>();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public OrderProduct setActions(ArrayList<Action> actions) {
        this.actions = actions;
        return this;
    }

    public OrderProduct addAction(Action action) {
        this.actions.add(action);
        return this;
    }

//    @Override
//    public String toString() {
//        return "OrderProduct{" +
//                "hazardous=" + hazardous +
//                ", name='" + name + '\'' +
//                ", totalWeight=" + totalWeight +
//                ", pricePerTon=" + pricePerTon +
//                ", actions=" + actions +
//                '}';
//    }


    @Override
    public String toString() {
        return this.name + ": " + this.getTotalWeight() + "Tons, PPT: " + this.pricePerTon;
    }


//    @Override
//    public String toString() {
//        return "OrderProduct{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", hazardous=" + hazardous +
//                ", totalWeight=" + totalWeight +
//                ", pricePerTon=" + pricePerTon +
//                ", orderid='" + orderid + '\'' +
//                ", actions=" + actions +
//                '}';
//    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getNaturalId() {
        return "id";
    }

    public BigDecimal getTotalWeight() {
        return this.totalWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct product = (OrderProduct) o;
        return hazardous == product.hazardous &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(totalWeight, product.totalWeight) &&
                Objects.equals(pricePerTon, product.pricePerTon) &&
                Objects.equals(orderid, product.orderid) &&
                Objects.equals(actions, product.actions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, hazardous, totalWeight, pricePerTon, orderid, actions);
    }
}
