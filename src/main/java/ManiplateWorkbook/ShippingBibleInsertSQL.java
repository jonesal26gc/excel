package ManiplateWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ShippingBibleInsertSQL {

    public static void main(String[] args) throws Exception {
        DepotLocationsList depotLocationsList;
        StoreLocationsList storeLocationsList;
        DepotToStoreRouteListsList depotToStoreRouteListsList;
        PrintWriter printWriter = new PrintWriter("ShippingNetworkInsertSQL.txt");

        try {
            FileInputStream fileIn = new FileInputStream("ShippingBibleExtract.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            depotLocationsList = (DepotLocationsList) in.readObject();
            storeLocationsList = (StoreLocationsList) in.readObject();
            depotToStoreRouteListsList = (DepotToStoreRouteListsList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            printWriter.println("class not found");
            c.printStackTrace();
            return;
        }

        //printWriter.println(depotToStoreRouteListsList.getDepotToStoreRouteLists().size() + " route lists were found.");

        for (Location location : storeLocationsList.getLocations()) {
            printWriter.println("insert into location " +
                    "(location_code,location_type_code,name,format,county,postcode,country,reporting_region) " +
                    "values (" + location.getLocationCode() +
                    ",'" + location.getLocationTypeCode() + "'"+
                    ",'" + location.getName().replaceAll("'","") + "'"+
                    ",'" + location.getFormat() + "'"+
                    ",'" + location.getCounty() + "'"+
                    ",'" + location.getPostcode() + "'"+
                    ",'" + location.getCountry() + "'"+
                    ",'" + location.getReportingRegion() + "');");
        }

        for (Location location : depotLocationsList.getLocations()) {
            printWriter.println("insert into location " +
                    "(location_code,location_type_code,name,format) " +
                    "values (" + location.getLocationCode() +
                    ",'" + location.getLocationTypeCode() + "'"+
                    ",'" + location.getName() + "'"+
                    ",'" + location.getFormat() + "');");
        }

        DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
        int routeNumber = 0;
        for (DepotToStoreRouteList depotToStoreRouteList : depotToStoreRouteListsList.getDepotToStoreRouteLists()) {
            for (DepotToStoreRoute depotToStoreRoute : depotToStoreRouteList.getDepotToStoreRoutes()) {
                routeNumber++;
                printWriter.println("insert into route " +
                        "(route_number, location_code_start, location_code_end, route_type_code, start_date, end_date) " +
                        "values (" + String.valueOf(routeNumber) +
                        "," + depotToStoreRoute.getDepot() +
                        "," + depotToStoreRoute.getStore() +
                        ",'" + depotToStoreRoute.getRouteTypeCode() + "'" +
                        ",'" + isoDate.format(depotToStoreRoute.getStartDate()) + "'" +
                        ",'" + isoDate.format(depotToStoreRoute.getEndDate()) + "'" +
                        ");");
                if (depotToStoreRoute.getDepotList() == null) {
                    printWriter.println("insert into route_leg " +
                            "(route_number, leg_number, location_code_from, location_code_to) " +
                            "values (" + String.valueOf(routeNumber) +
                            ",1" +
                            "," + depotToStoreRoute.getDepot() +
                            "," + depotToStoreRoute.getStore() +
                            ");");
                } else {
                    int legNumber = 0;
                    String fromDepot = depotToStoreRoute.getDepot();
                    for (String depot : depotToStoreRoute.getDepotList()) {
                        legNumber++;
                        printWriter.println("insert into route_leg " +
                                "(route_number, leg_number, location_code_from, location_code_to) " +
                                "values (" + String.valueOf(routeNumber) +
                                "," + String.valueOf(legNumber) +
                                "," + fromDepot +
                                "," + depotToStoreRoute.getDepotList()[legNumber - 1] +
                                ");");
                        fromDepot = depotToStoreRoute.getDepotList()[legNumber - 1];
                    }
                    legNumber++;
                    printWriter.println("insert into route_leg " +
                            "(route_number, leg_number, location_code_from, location_code_to) " +
                            "values (" + String.valueOf(routeNumber) +
                            "," + String.valueOf(legNumber) +
                            "," + fromDepot +
                            "," + depotToStoreRoute.getStore() +
                            ");");
                }
            }
        }
    }
}
