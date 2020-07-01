package group4.heidenwebservice.business;

import dao.DAO;
import group4.heidenwebservice.Controller;

import java.util.Collection;

public interface Business {

    void addController(Controller controller);

    Collection getDao();

    void setDao(DAO... dao);
}
