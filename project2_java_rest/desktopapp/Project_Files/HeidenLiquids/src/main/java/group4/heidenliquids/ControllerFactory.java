package group4.heidenliquids;


import dao.AbstractDAOFactory;
import dao.PG.PGDAOFactory;
import dao.REST.RestDAOFactory;
import group4.heidenliquids.address.AddressController;
import group4.heidenliquids.customer.BlockCustomerController;
import group4.heidenliquids.customer.RegisterCustomerController;
import group4.heidenliquids.driver.RegisterDriverController;
import group4.heidenliquids.executeOrder.ExecuteOrderBusiness;
import group4.heidenliquids.executeOrder.ExecuteOrderController;
import group4.heidenliquids.invoice.InvoiceController;
import group4.heidenliquids.order.OrderController;
import group4.heidenliquids.order.OrderHomeController;
import group4.heidenliquids.orderProduct.OrderProductController;
import group4.heidenliquids.planning.PlanningController;
import group4.heidenliquids.trailer.RegisterTrailerController;
import group4.heidenliquids.truck.RegisterTruckController;
import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object> {

    private static AbstractDAOFactory factory;

    public ControllerFactory(AbstractDAOFactory abstractDAOFactory) {
        factory = abstractDAOFactory;
    }

    @Override
    public Object call(Class<?> c) {
//        System.out.println("c = " + c);

        String packagePath = "group4.heidenliquids.";

        switch (c.getName().substring(packagePath.length())){
            case "SceneController":
                return new SceneController();
            case "address.AddressController":
                return new AddressController(factory);
            case "trailer.RegisterTrailerController":
                return new RegisterTrailerController();
            case "truck.RegisterTruckController":
                return new RegisterTruckController();
            case "driver.RegisterDriverController":
                return new RegisterDriverController();
            case "customer.RegisterCustomerController":
                return new RegisterCustomerController(factory, this);
            case "customer.BlockCustomerController":
                return new BlockCustomerController(factory, this);
            case "order.OrderHomeController":
                return new OrderHomeController(factory, this);
            case "order.OrderController":
                return new OrderController(factory, this);
            case "orderProduct.OrderProductController":
                return new OrderProductController(factory, this);
            case "executeOrder.ExecuteOrderController":
                return new ExecuteOrderController(factory);
            case "invoice.InvoiceController":
                return new InvoiceController(factory, this);
            case "planning.PlanningController":
                return new PlanningController(factory);
            default:
                System.out.println("controller not found. Please add in ControllerFactory.java");
                return null;
        }
    }
}
