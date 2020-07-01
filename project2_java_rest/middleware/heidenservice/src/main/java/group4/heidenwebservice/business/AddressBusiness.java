package group4.heidenwebservice.business;

import dao.DAO;
import group4.heidenwebservice.Controller;

import java.util.Collection;

public class AddressBusiness implements Business {

    private DAO addressDao;
    private Controller controller;

    public AddressBusiness(DAO addressDao) {
        this.addressDao = addressDao;
    }

    public void saveAddress() {

    }

    @Override
    public void addController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Collection getDao() {
        return null;
    }

    @Override
    public void setDao(DAO... dao) {

    }
}
