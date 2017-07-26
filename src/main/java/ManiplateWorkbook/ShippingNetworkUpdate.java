package ManiplateWorkbook;

import org.hibernate.Query;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingNetworkUpdate {
    public static final String SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME = "ShippingBibleSerializedExtract.ser";

    public static void main(String[] args) throws Exception {
        LocationList depotLocationList;
        LocationList storeLocationList;
        RouteListsArray routeListsArray;

        try {
            FileInputStream fileIn = new FileInputStream(SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            depotLocationList = (LocationList) in.readObject();
            storeLocationList = (LocationList) in.readObject();
            routeListsArray = (RouteListsArray) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            throw new RuntimeException("ERROR - De-serialization of objects failed.");
        } catch (ClassNotFoundException c) {
            throw new RuntimeException("ERROR - De-serialization of objects failed - unable to find class.");
        }
        System.out.println("INFO - De-serialization of Shipping Network objects is complete.");

        DBConnectionToShippingNetworkViaHibernate dbc = new DBConnectionToShippingNetworkViaHibernate();
        dbc.open();
        processLocations(dbc, depotLocationList.getLocations());
        processLocations(dbc, storeLocationList.getLocations());
        deleteExistingRoutes(dbc);
        for (RouteList routeList : routeListsArray.getRouteLists()) {
            processRouteList(dbc, routeList);
        }
        dbc.close();
        System.out.println("INFO - Update(s) to the Shipping Network Database are complete.");
    }

    private static void processLocations(DBConnectionToShippingNetworkViaHibernate dbc, ArrayList<Location> locations) {
        dbc.startTransaction();
        int transactionCount = 0;
        for (Location location : locations) {
            transactionCount++;
            dbc.session.saveOrUpdate(location);
        }
        dbc.endTransaction();
        System.out.println("INFO - " + transactionCount + " updates for LOCATION table.");
    }

    private static void deleteExistingRoutes(DBConnectionToShippingNetworkViaHibernate dbc) {
        dbc.startTransaction();
        int routeDeleteCount = 0;

        Query allRoutes = dbc.session.getNamedQuery("allRoutes");
        @SuppressWarnings("unchecked")
        List<Route> routesToDelete = allRoutes.list();
        for (Route route : routesToDelete) {
            Query deleteRoute = dbc.session.getNamedQuery("deleteRoute");
            deleteRoute.setParameter("routeNumber", route.getRoute_number());
            if (deleteRoute.executeUpdate() > 0) {
                routeDeleteCount++;
            }
        }
        System.out.println("INFO - " + routeDeleteCount + " rows were deleted");
        dbc.endTransaction();
    }

    private static void processRouteList(DBConnectionToShippingNetworkViaHibernate dbc, RouteList routeList) {
        int transactionCount = 0;
        dbc.startTransaction();
        for (Route route : routeList.getRoutes()) {
            transactionCount++;
            processRoute(dbc, route);
        }
        dbc.endTransaction();
        System.out.println("INFO - " + transactionCount + " updates for ROUTE table.");
    }

    private static void processRoute(DBConnectionToShippingNetworkViaHibernate dbc, Route route) {
        try {
            dbc.session.save(route);
            for (RouteLeg routeLeg : route.getRouteLegs()) {
                routeLeg.setLegNumber(0);
                routeLeg.setRoute(route);
                dbc.session.save(routeLeg);
            }
        } catch (Exception ex) {
            throw  new RuntimeException("ERROR - " + ex.getMessage());
        }
    }
}