package ManiplateWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ShippingBibleInsertSQL {
    public static final String END_OF_LINE = "\n";

    public static void main(String[] args) throws Exception {
        DepotLocationsList depotLocationsList;
        StoreLocationsList storeLocationsList;
        DepotToStoreRouteListsList depotToStoreRouteListsList;

        File file = new File("ShippingNetworkInsertSQL.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

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
            System.out.println("De-serialization of objects failed.");
            c.printStackTrace();
            return;
        }

        processStoreLocations(storeLocationsList, bufferedWriter);
        processDepotLocations(depotLocationsList, bufferedWriter);
        processRoutes(depotToStoreRouteListsList, bufferedWriter);

        System.out.println("SQL generation is complete.");
    }

    private static void processStoreLocations(StoreLocationsList storeLocationsList, BufferedWriter bufferedWriter) {
        for (Location location : storeLocationsList.getLocations()) {
            createSQLforStoreLocation(bufferedWriter, location);
        }
    }

    private static void processDepotLocations(DepotLocationsList depotLocationsList, BufferedWriter bufferedWriter) {
        for (Location location : depotLocationsList.getLocations()) {
            createSQLforDepotLocations(bufferedWriter, location);
        }
    }

    private static void processRoutes(DepotToStoreRouteListsList depotToStoreRouteListsList, BufferedWriter bufferedWriter) {
        DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
        int routeNumber = 0;
        for (DepotToStoreRouteList depotToStoreRouteList : depotToStoreRouteListsList.getDepotToStoreRouteLists()) {
            for (DepotToStoreRoute depotToStoreRoute : depotToStoreRouteList.getDepotToStoreRoutes()) {
                routeNumber++;
                createSQLforRoute(bufferedWriter, isoDate, routeNumber, depotToStoreRoute);
                if (depotToStoreRoute.getDepotList() == null) {
                    createSQLforNonTrunkedRouteLeg(bufferedWriter, routeNumber, depotToStoreRoute);
                } else {
                    createSQLforTrunkedRouteLeg(bufferedWriter, routeNumber, depotToStoreRoute);
                }
            }
        }
    }

    private static void createSQLforStoreLocation(BufferedWriter bufferedWriter, Location location) {
        try {
            bufferedWriter.write("insert into location " +
                    "(location_code,location_type_code,name,format,county,postcode,country,reporting_region) " +
                    "values (" + location.getLocationCode() +
                    ",'" + location.getLocationTypeCode() + "'" +
                    ",'" + location.getName().replaceAll("'", "") + "'" +
                    ",'" + location.getFormat() + "'" +
                    ",'" + location.getCounty() + "'" +
                    ",'" + location.getPostcode() + "'" +
                    ",'" + location.getCountry() + "'" +
                    ",'" + location.getReportingRegion() + "');" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createSQLforDepotLocations(BufferedWriter bufferedWriter, Location location) {
        try {
            bufferedWriter.write("insert into location " +
                    "(location_code,location_type_code,name,format) " +
                    "values (" + location.getLocationCode() +
                    ",'" + location.getLocationTypeCode() + "'" +
                    ",'" + location.getName() + "'" +
                    ",'" + location.getFormat() + "');" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createSQLforRoute(BufferedWriter bufferedWriter, DateFormat isoDate, int routeNumber, DepotToStoreRoute depotToStoreRoute) {
        try {
            bufferedWriter.write("insert into route " +
                    "(route_number, location_code_start, location_code_end, route_type_code, start_date, end_date) " +
                    "values (" + String.valueOf(routeNumber) +
                    "," + depotToStoreRoute.getDepot() +
                    "," + depotToStoreRoute.getStore() +
                    ",'" + depotToStoreRoute.getRouteTypeCode() + "'" +
                    ",'" + isoDate.format(depotToStoreRoute.getStartDate()) + "'" +
                    ",'" + isoDate.format(depotToStoreRoute.getEndDate()) + "'" +
                    ");" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createSQLforNonTrunkedRouteLeg(BufferedWriter bufferedWriter, int routeNumber, DepotToStoreRoute depotToStoreRoute) {
        try {
            bufferedWriter.write("insert into route_leg " +
                    "(route_number, leg_number, location_code_from, location_code_to) " +
                    "values (" + String.valueOf(routeNumber) +
                    ",1" +
                    "," + depotToStoreRoute.getDepot() +
                    "," + depotToStoreRoute.getStore() +
                    ");" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createSQLforTrunkedRouteLeg(BufferedWriter bufferedWriter, int routeNumber, DepotToStoreRoute depotToStoreRoute) {
        try {
            int legNumber = 0;
            String fromDepot = depotToStoreRoute.getDepot();
            for (String depot : depotToStoreRoute.getDepotList()) {
                legNumber++;
                bufferedWriter.write("insert into route_leg " +
                        "(route_number, leg_number, location_code_from, location_code_to) " +
                        "values (" + String.valueOf(routeNumber) +
                        "," + String.valueOf(legNumber) +
                        "," + fromDepot +
                        "," + depot +
                        ");" + END_OF_LINE);
                fromDepot = depot;
            }
            legNumber++;
            bufferedWriter.write("insert into route_leg " +
                    "(route_number, leg_number, location_code_from, location_code_to) " +
                    "values (" + String.valueOf(routeNumber) +
                    "," + String.valueOf(legNumber) +
                    "," + fromDepot +
                    "," + depotToStoreRoute.getStore() +
                    ");" + END_OF_LINE);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}