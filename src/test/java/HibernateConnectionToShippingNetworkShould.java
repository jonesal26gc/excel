import ManiplateWorkbook.HibernateConnectionToShippingNetwork;
import ManiplateWorkbook.Location;
import ManiplateWorkbook.Route;
import ManiplateWorkbook.RouteLeg;
import org.hibernate.Query;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class HibernateConnectionToShippingNetworkShould {

    @Test
    public void
    connect_successfully() {
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();

        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("allDepotLocations");
        List<Location> allLocationsList = namedQuery.list();
        for (Location location : allLocationsList) {
            System.out.println(location.getLocationCode() + " = " + location.getName());
        }

        hc.endTransaction();
        hc.close();
    }

    @Test
    public void
    getTheFirstTenRoutes() {
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();

        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("firstTenRoutes");
        List<Route> firstTenRoutes = namedQuery.list();
        for (Route route : firstTenRoutes) {
            System.out.println(route.getRoute_number() + " = " + route.getLocationCodeStart() + " to " + route.getLocationCodeEnd());
            System.out.println("There are " + route.getRouteLegs().size() + " legs in this route.");
            for (RouteLeg routeLeg : route.getRouteLegs()) {
                System.out.println(routeLeg.toString());
            }

//            Query namedQuery2 = hc.session.getNamedQuery("allRouteLegs");
//            //namedQuery2.setParameter(0,route.getRoute_number());
//            namedQuery2.setParameter("routeNumber", route.getRoute_number());
//            List<RouteLeg> allRouteLegs = namedQuery2.list();
//            System.out.println("There are " + allRouteLegs.size() + " legs in this route.");
//            for (RouteLeg routeLeg : allRouteLegs) {
//                System.out.println(routeLeg.toString());
//            }

        }

        hc.endTransaction();
        hc.close();
    }

    @Test
    public void
    getNextRouteNumber() {
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();

        hc.open();
        hc.startTransaction();
        Query q = hc.session.createSQLQuery("select nextval('route_number_sequence')");
        BigInteger result = (BigInteger) q.uniqueResult();
        System.out.println(result);

        hc.endTransaction();
        hc.close();
    }

    @Test
    public void
    insertRoute() {
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();

        hc.open();
        hc.startTransaction();
        Route route1 = new Route(0, 460, 2055, "FRONT", null, new Date(2017, 01, 02), new Date());
        hc.session.saveOrUpdate(route1);

        hc.endTransaction();
        hc.close();
    }
}
