import ManiplateWorkbook.DepotLocationList;
import ManiplateWorkbook.Location;
import ManiplateWorkbook.ShippingDatabaseConnectionForPostgres;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

public class ShippingBibleUpdateShould {

    @Test
    public void updateDepotLocations() {

        DepotLocationList depotLocationList = new DepotLocationList();
        depotLocationList.add(new Location(1,"depot","depot 1","stream"));

        Queue<Location> queue = new LinkedList<Location>(depotLocationList.getLocations());
        while (!queue.isEmpty()){
            System.out.println(queue.poll().toString());
        }

        ShippingDatabaseConnectionForPostgres shippingDatabaseConnectionForPostgres = new ShippingDatabaseConnectionForPostgres();
        shippingDatabaseConnectionForPostgres.establishConnection();

        try {
            Statement stmt = shippingDatabaseConnectionForPostgres.c.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT location_code" +
                            ", location_type_code" +
                            ", name" +
                            ", format" +
                            ", county" +
                            ", postcode" +
                            ", country" +
                            ", reporting_region" +
                            " FROM location" +
                            " where location_type_code = 'DEPOT' " +
                            "  and location_code between 0 and 999;");
            while (rs.next()) {
                int locationCode = rs.getInt("location_code");
                String locationTypeCode = rs.getString("location_type_code");
                String name = rs.getString("name");
                System.out.println(locationCode + " " + locationTypeCode + " " + name);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        shippingDatabaseConnectionForPostgres.terminateConnection();
    }

}
