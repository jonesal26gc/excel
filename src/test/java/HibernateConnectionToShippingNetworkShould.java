import ManiplateWorkbook.HibernateConnectionToShippingNetwork;
import ManiplateWorkbook.Location;
import ManiplateWorkbook.Route;
import ManiplateWorkbook.RouteLeg;
import org.hibernate.Query;
import org.junit.Test;

import java.util.List;

public class HibernateConnectionToShippingNetworkShould {

    @Test
    public void
    connect_successfully(){
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
    getTheFirstTenRoutes(){
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();

        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("firstTenRoutes");
        List<Route> firstTenRoutes = namedQuery.list();
        for (Route route : firstTenRoutes) {
            System.out.println(route.getRoute_number() + " = " + route.getLocationCodeStart() + " to " + route.getLocationCodeEnd());
            System.out.println("There are " + route.getRouteLegs().size() + " legs in this route.");
            for (RouteLeg routeLeg:route.getRouteLegs()){
                System.out.println(routeLeg.toString());
            }

            Query namedQuery2 = hc.session.getNamedQuery("allRouteLegs");
            //namedQuery2.setParameter(0,route.getRoute_number());
            namedQuery2.setParameter("routeNumber",route.getRoute_number());
            List<RouteLeg> allRouteLegs = namedQuery2.list();
            System.out.println("There are " + allRouteLegs.size() + " legs in this route.");
            for (RouteLeg routeLeg:allRouteLegs){
                System.out.println(routeLeg.toString());
            }

        }

        hc.endTransaction();
        hc.close();
    }
}
