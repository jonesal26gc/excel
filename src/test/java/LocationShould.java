import ManiplateWorkbook.Location;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class LocationShould {

    @Test
    public void
    MatchTwoIdenticalLocations() {
        Location location1 = new Location(1, "STORE", "1", "EXPRESS", "HERTS", "SG4 9HD", "UK", "REGION");
        Location location2 = new Location(1, "STORE", "1", "EXPRESS", "HERTS", "SG4 9HD", "UK", "REGION");
        assertTrue(location1.locationMatches(location2));
    }

    @Test
    public void
    MatchTwoNonIdenticalLocations() {
        Location location1 = new Location(1, "STORE", "1", "EXPRESS", "HERTS", "SG4 9HD", "UK", "REGION");
        Location location2 = new Location(1, "STORE", "1", "METRO", "HERTS", "SG4 9HD", "UK", "REGION");
        assertFalse(location1.locationMatches(location2));
    }
}
