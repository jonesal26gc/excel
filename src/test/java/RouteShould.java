import ManiplateWorkbook.Route;
import ManiplateWorkbook.RouteLeg;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RouteShould {

    @Test
    public void
    MatchTwoRoutes() {
        Set<RouteLeg> routeLegs = new LinkedHashSet<RouteLeg>();
        routeLegs.add(new RouteLeg(1, 460, 315));
        routeLegs.add(new RouteLeg(2, 315, 2055));
        Route route1 = new Route(1, 460, 2055, "FRONT", routeLegs, new Date(), new Date());
        Route route2 = route1;
        assertTrue(route1.routeMatches(route2));
    }

    @Test
    public void
    FailToMatchTwoRoutes() {
        Set<RouteLeg> routeLegs = new LinkedHashSet<RouteLeg>();
        routeLegs.add(new RouteLeg(1, 460, 315));
        routeLegs.add(new RouteLeg(2, 315, 2055));
        Route route1 = new Route(1, 460, 2055, "FRONT", routeLegs, new Date(), new Date());
        Set<RouteLeg> routeLegs2 = new LinkedHashSet<RouteLeg>();
        routeLegs2.add(new RouteLeg(1, 460, 2055));
        Route route2 = new Route(1, 460, 2055, "FRONT", routeLegs2, new Date(), new Date());
        assertTrue(!route1.routeMatches(route2));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstLocationCodeStartLower() {
        Route route1 = new Route(1, 460, 2055, "FRONT", null, new Date(), new Date());
        Route route2 = new Route(1, 461, 2055, "FRONT", null, new Date(), new Date());
        assertThat(route1.compareTo(route2), is(-1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstLocationCodeEndLower() {
        Route route1 = new Route(1, 460, 2054, "FRONT", null, new Date(), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(), new Date());
        assertThat(route1.compareTo(route2), is(-1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstRouteTypeCodeLower() {
        Route route1 = new Route(1, 460, 2055, "AAAAA", null, new Date(), new Date());
        Route route2 = new Route(1, 460, 2055, "BBBBB", null, new Date(), new Date());
        assertThat(route1.compareTo(route2), is(-1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstStartDateLower() {
        Route route1 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 02), new Date());
        assertThat(route1.compareTo(route2), is(-1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstLocationCodeStartHigher() {
        Route route1 = new Route(1, 461, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        assertThat(route1.compareTo(route2), is(1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstLocationCodeEndHigher() {
        Route route1 = new Route(1, 460, 2056, "FRONT", null, new Date(2017, 01, 01), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        assertThat(route1.compareTo(route2), is(1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstRouteTypeCodeHigher() {
        Route route1 = new Route(1, 460, 2055, "CCCCC", null, new Date(2017, 01, 01), new Date());
        Route route2 = new Route(1, 460, 2055, "BBBBB", null, new Date(2017, 01, 01), new Date());
        assertThat(route1.compareTo(route2), is(1));
    }

    @Test
    public void
    CompareTwoRoutesWithFirstStartDateHigher() {
        Route route1 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 02), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        assertThat(route1.compareTo(route2), is(1));
    }

    @Test
    public void
    CompareTwoRoutesWithEqualValues() {
        Route route1 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        Route route2 = new Route(1, 460, 2055, "FRONT", null, new Date(2017, 01, 01), new Date());
        assertThat(route1.compareTo(route2), is(0));
    }

}
