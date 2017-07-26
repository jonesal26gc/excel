import ManiplateWorkbook.HibernateConnectionToShippingNetwork;
import ManiplateWorkbook.RoutePathView;
import org.hibernate.Query;
import org.junit.Test;

import java.util.List;

public class RoutePathViewShould {

    @Test
    public void
    selectRouteOne(){
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();
        hc.open();
        hc.startTransaction();

        Query namedQuery = hc.session.getNamedQuery("routePathSourceToDestination");
        namedQuery.setParameter("routeTypeCode", "FRONT");
        namedQuery.setParameter("locationCodeStart",460);
        namedQuery.setParameter("locationCodeEnd",2055);
        System.out.println("running");
        System.out.println(namedQuery.list().toString());
        List<RoutePathView> routePathViews = namedQuery.list();
        for (RoutePathView routePathView : routePathViews) {
            System.out.println(routePathView.toString());
        }

        hc.endTransaction();
        hc.close();
    }
}
