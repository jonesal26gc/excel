package ManiplateWorkbook;

import com.sun.tools.javac.file.Locations;
import org.hibernate.Query;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShippingNetworkUpdate {

    public static void main(String[] args) throws Exception {
        DepotLocationList depotLocationList;
        StoreLocationList storeLocationList;
        RouteListsArray routeListsArray;

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
            System.out.println("De-serialization of Shipping Network objects has failed.");
            c.printStackTrace();
            return;
        }

        System.out.println("De-serialization of Shipping Network objects is complete.");
        HibernateConnectionToShippingNetwork hc = new HibernateConnectionToShippingNetwork();
        hc.open();
        processLocations(hc, depotLocationList.getLocations());
        processLocations(hc, storeLocationList.getLocations());
        hc.close();
        System.out.println("Update(s) to the Shipping Network Database are complete.");
    }

    private static void processLocations(HibernateConnectionToShippingNetwork hc, ArrayList<Location> locations) {
        hc.startTransaction();
        for (Location location : locations) {
            System.out.println(location.getLocationCode());
            hc.session.saveOrUpdate(location);
        }
        hc.endTransaction();
    }
}