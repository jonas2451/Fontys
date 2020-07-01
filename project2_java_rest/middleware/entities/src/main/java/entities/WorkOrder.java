package entities;

public class WorkOrder {

    private int jobNumber;
    private OrderProduct product;
    private Driver driver;
    private Trailer trailer;
    private Truck truck;

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Truck getTruck() {
        return truck;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public Driver getDriver() {
        return driver;
    }

    public OrderProduct getProduct() {
        return product;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public void setProduct(OrderProduct product) {
        this.product = product;
    }

    public void setTrailer(Trailer trailer) {
        this.trailer = trailer;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    @Override
    public String toString() {
        return "Job number: " + getJobNumber() + " Product: " + getProduct().getName() + " Truck: " + getTruck().getLicenseNumber()
                + " Driver: " + getDriver().getFirstName() + " " + getDriver().getLastName() + " Trailer: " + getTrailer().getLicenseNumber();

    }
}
