import ManiplateWorkbook.HibernateConnectionToShippingNetwork;
import ManiplateWorkbook.RoutePathView;
import org.hibernate.Query;
import org.junit.Test;

import java.util.List;

public class RoutePathViewShould {

    @Test
    public void
    selectRoutePathSourceToDestination(){
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();
        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("routePathSourceToDestination");
        namedQuery.setParameter("routeTypeCode", "FRONT");
        namedQuery.setParameter("locationCodeStart",460);
        namedQuery.setParameter("locationCodeEnd",2055);

        @SuppressWarnings("unchecked")
        List<RoutePathView> routePathViews = namedQuery.list();
        for (RoutePathView routePathView : routePathViews) {
            System.out.println(routePathView.toString());
        }

        hc.endTransaction();
        hc.close();
    }

    @Test
    public void
    selectRoutePathToDestination(){
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();
        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("routePathToDestination");
        namedQuery.setParameter("routeTypeCode", "FRONT");
        namedQuery.setParameter("locationCodeEnd",2055);

        @SuppressWarnings("unchecked")
        List<RoutePathView> routePathViews = namedQuery.list();
        for (RoutePathView routePathView : routePathViews) {
            System.out.println(routePathView.toString());
        }

        hc.endTransaction();
        hc.close();
    }
}
