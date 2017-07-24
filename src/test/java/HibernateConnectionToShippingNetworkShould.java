import ManiplateWorkbook.HibernateConnectionToShippingNetwork;
import ManiplateWorkbook.Location;
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
}
