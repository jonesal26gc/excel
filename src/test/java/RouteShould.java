import ManiplateWorkbook.Route;
import ManiplateWorkbook.RouteLeg;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class RouteShould {



    @Test
    public void
    matchTwoRoutes() {
        Set<RouteLeg> routeLegs = new LinkedHashSet<RouteLeg>();
        routeLegs.add(new RouteLeg(1, 460, 315));
        routeLegs.add(new RouteLeg(2, 315, 2055));
        Route route1 = new Route(1,
                460,
                2055,
                "FRONT",
                routeLegs,
                new Date(),
                new Date());
        Route route2 = route1;
        routeLegs.add(new RouteLeg(3, 315, 2055));
        assertTrue(route1.routeMatches(route2));
    }


    @Test
    public void
    notMatchTwoRoutes() {
        Set<RouteLeg> routeLegs = new LinkedHashSet<RouteLeg>();
        routeLegs.add(new RouteLeg(1, 460, 315));
        routeLegs.add(new RouteLeg(2, 315, 2055));
        Route route1 = new Route(1,
                460,
                2055,
                "FRONT",
                routeLegs,
                new Date(),
                new Date());
        Set<RouteLeg> routeLegs2 = new LinkedHashSet<RouteLeg>();
        routeLegs2.add(new RouteLeg(1, 460, 2055));
        Route route2 = new Route(1,
                460,
                2055,
                "FRONT",
                routeLegs2,
                new Date(),
                new Date());
        assertTrue(!route1.routeMatches(route2));
    }
}
