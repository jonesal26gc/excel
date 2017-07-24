package ManiplateWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ShippingNetworkSqlGeneration {
    public static final String END_OF_LINE = "\n";

    public static void main(String[] args) throws Exception {
        DepotLocationList depotLocationList;
        StoreLocationList storeLocationList;
        RouteListsArray routeListsArray;

        File file = new File("ShippingNetworkInsertSQL.txt");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        try {
            FileInputStream fileIn = new FileInputStream("ShippingBibleWorkbookExtract.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            depotLocationList = (DepotLocationList) in.readObject();
            storeLocationList = (StoreLocationList) in.readObject();
            routeListsArray = (RouteListsArray) in.readObject();
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

        processStoreLocations(storeLocationList, bufferedWriter);
        processDepotLocations(depotLocationList, bufferedWriter);
        processRoutes(routeListsArray, bufferedWriter);

        System.out.println("SQL generation is complete.");
    }

    private static void processStoreLocations(StoreLocationList storeLocationList, BufferedWriter bufferedWriter) {
        for (Location location : storeLocationList.getLocations()) {
            createSQLforStoreLocation(bufferedWriter, location);
        }
    }

    private static void processDepotLocations(DepotLocationList depotLocationList, BufferedWriter bufferedWriter) {
        for (Location location : depotLocationList.getLocations()) {
            createSQLforDepotLocations(bufferedWriter, location);
        }
    }

    private static void processRoutes(RouteListsArray routeListsArray, BufferedWriter bufferedWriter) {
        DateFormat isoDate = new SimpleDateFormat("yyyy-MM-dd");
        int routeNumber = 0;
        for (RouteList routeList : routeListsArray.getRouteLists()) {
            for (Route route : routeList.getRoutes()) {
                routeNumber++;
                route.setRoute_number(routeNumber);
                createSQLforRoute(bufferedWriter, isoDate, route);
                for (RouteLeg routeLeg : route.getRouteLegs()) {
                    createSQLforRouteLeg(bufferedWriter, route.getRoute_number(), routeLeg);
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

    private static void createSQLforRoute(BufferedWriter bufferedWriter, DateFormat isoDate, Route route) {
        try {
            bufferedWriter.write("insert into route " +
                    "(route_number, location_code_start, location_code_end, route_type_code, start_date, end_date) " +
                    "values (" + String.valueOf(route.getRoute_number()) +
                    "," + route.getLocationCodeStart() +
                    "," + route.getLocationCodeEnd() +
                    ",'" + route.getRouteTypeCode() + "'" +
                    ",'" + isoDate.format(route.getStartDate()) + "'" +
                    ",'" + isoDate.format(route.getEndDate()) + "'" +
                    ");" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createSQLforRouteLeg(BufferedWriter bufferedWriter, int routeNumber, RouteLeg routeLeg) {
        try {
            bufferedWriter.write("insert into route_leg " +
                    "(route_number, leg_number, location_code_from, location_code_to) " +
                    "values (" + String.valueOf(routeNumber) +
                    "," + String.valueOf(routeLeg.getLegNumber()) +
                    "," + String.valueOf(routeLeg.getLocationCodeFrom()) +
                    "," + String.valueOf(routeLeg.getLocationCodeTo()) +
                    ");" + END_OF_LINE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}