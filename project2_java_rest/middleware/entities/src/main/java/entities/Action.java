package entities;

import dao.Entity1;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Action implements Entity1<String> {

    String  trackingNumber;
    int     orderingNumber;
    boolean isUnloading;
    BigDecimal expectedWeight;
    String productId;
    String addressId;
    String receiptNumber;
    transient String addressAsString;

    public Action(String trackingNumber, int orderingNumber, boolean isUnloading, BigDecimal expectedWeight, String productId, String addressId) {
        this.trackingNumber = trackingNumber;
        this.orderingNumber = orderingNumber;
        this.isUnloading = isUnloading;
        this.expectedWeight = expectedWeight;
        this.productId = productId;
        this.addressId = addressId;
    }

    public Action(String trackingNumber, int orderingNumber, boolean isUnloading, BigDecimal expectedWeight, Address address) {
        this.trackingNumber = trackingNumber;
        this.orderingNumber = orderingNumber;
        this.isUnloading = isUnloading;
        this.expectedWeight = expectedWeight;
        this.addressId = address.getId();
        this.addressAsString = address.toString();
    }

    public Action(String trackingNumber, int orderingNumber, boolean isUnloading, BigDecimal expectedWeight, String productId, String addressId, String receiptNumber) {
        this.trackingNumber = trackingNumber;
        this.orderingNumber = orderingNumber;
        this.isUnloading = isUnloading;
        this.expectedWeight = expectedWeight;
        this.productId = productId;
        this.addressId = addressId;
        this.receiptNumber = receiptNumber;
    }

    public boolean getIsUnloading() {
        return isUnloading;
    }

    public void setIsUnloading(boolean unloading) {
        isUnloading = unloading;
    }

    @Override
    public String getId() {
        return trackingNumber;
    }

    @Override
    public String getNaturalId() {
        return "trackingno";
    }

    public BigDecimal getExpectedWeight() {
        return this.expectedWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return orderingNumber == action.orderingNumber &&
                isUnloading == action.isUnloading &&
                Objects.equals(trackingNumber, action.trackingNumber) &&
                Objects.equals(expectedWeight, action.expectedWeight) &&
                Objects.equals(productId, action.productId) &&
                Objects.equals(addressId, action.addressId) &&
                Objects.equals(receiptNumber, action.receiptNumber) &&
                Objects.equals(addressAsString, action.addressAsString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber, orderingNumber, isUnloading, expectedWeight, productId, addressId, receiptNumber, addressAsString);
    }
}
