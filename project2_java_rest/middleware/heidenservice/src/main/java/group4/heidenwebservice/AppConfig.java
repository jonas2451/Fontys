package group4.heidenwebservice;

import dao.AbstractDAOFactory;
import dao.DAOConnection;
import dao.PG.PGDAOFactory;
import entities.Order;
//import group4.heidenwebservice.util.GsonProvider;
import group4.heidenwebservice.dataservice.PGDbConnection;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.sql.Connection;
import java.util.Map;

/**
 *
 * Configures the dependency injection.
 * Can be overwritten for testing by using the jersey-test framework.
 *
 * @author Jonas Terschlüsen
 */
@ApplicationPath("/webapi")
public class AppConfig extends ResourceConfig {
    public AppConfig() {
        register(AbstractDAOFactory.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(PGDAOFactory.class).to(AbstractDAOFactory.class);
                bind(PGDbConnection.class).to(DAOConnection.class);
            }
        });
//        register(new ClientConfig()
//                .register(GsonProvider.class)
//                .property("jersey.config.client.disableMoxyJson", true)
//        );

    }

}