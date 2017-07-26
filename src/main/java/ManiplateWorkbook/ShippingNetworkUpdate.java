package ManiplateWorkbook;

import org.hibernate.Query;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingNetworkUpdate {

    public static void main(String[] args) throws Exception {
        LocationList depotLocationList;
        LocationList storeLocationList;
        RouteListsArray routeListsArray;

        try {
            FileInputStream fileIn = new FileInputStream("ShippingBibleWorkbookExtract.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            depotLocationList = (LocationList) in.readObject();
            storeLocationList = (LocationList) in.readObject();
            routeListsArray = (RouteListsArray) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("De-serialization of Shipping Network objects has failed.");
            c.printStackTrace();
            return;
        }

        System.out.println("De-serialization of Shipping Network objects is complete.");
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();
        hc.open();
        processLocations(hc, depotLocationList.getLocations());
        processLocations(hc, storeLocationList.getLocations());
        deleteExistingRoutes(hc);
        for (RouteList routeList : routeListsArray.getRouteLists()) {
            processRouteList(hc, routeList);
        }
        hc.close();
        System.out.println("Update(s) to the Shipping Network Database are complete.");
    }

    private static void processLocations(HibernateConnectionToShippingNetwork hc, ArrayList<Location> locations) {
        hc.startTransaction();
        int transactionCount = 0;
        for (Location location : locations) {
            transactionCount++;
            hc.session.saveOrUpdate(location);
        }
        hc.endTransaction();
        System.out.println(transactionCount + " updates for LOCATION table.");
    }

    private static void deleteExistingRoutes(HibernateConnectionToShippingNetwork hc) {
        hc.startTransaction();
        int routeDeleteCount = 0;
        int routeLegDeleteCount = 0;

        Query deleteRoutes = hc.session.getNamedQuery("allRoutes");
        List<Route> routesToDelete = deleteRoutes.list();
        for (Route route : routesToDelete) {
//            System.out.println("route legs");
//            for (RouteLeg routeLeg: route.getRouteLegs()){
//                System.out.println(routeLeg.toString());
//                hc.session.delete(routeLeg);
//                routeLegDeleteCount++;
//            }
            hc.session.delete(route);
            routeDeleteCount++;
//        hc.session.getTransaction().commit();
        }
        System.out.println(routeDeleteCount + " rows were deleted");
        System.out.println(routeLegDeleteCount + " rows were deleted");
        hc.endTransaction();
    }

    private static void processRouteList(HibernateConnectionToShippingNetwork hc, RouteList routeList) {
        System.out.println("processing route");
        int transactionCount = 0;
        hc.startTransaction();
        for (Route route : routeList.getRoutes()) {
            transactionCount++;
            processRoute(hc, route);
        }
        //hc.session.getTransaction().commit();
        hc.endTransaction();
        System.out.println(transactionCount + " updates for ROUTE table.");
    }

    private static void processRoute(HibernateConnectionToShippingNetwork hc, Route route) {
        try {
            hc.session.save(route);
            System.out.println(route.toString());
            for (RouteLeg routeLeg : route.getRouteLegs()) {
                routeLeg.setLegNumber(0);
                routeLeg.setRoute(route);
                hc.session.save(routeLeg);
                System.out.println(">" + routeLeg.toString());
            }
        } catch (Exception ex) {
            System.out.println("Warning - " + ex.getMessage());
        }
    }
}