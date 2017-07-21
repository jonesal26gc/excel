package ManiplateWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ShippingBibleWorkbookExtract {
    public static void main(String[] args) {
        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook("Transport Bible.xlsx");

        // load the depot list.
        DepotLocationList depotLocationList = shippingBibleWorkbook.buildDepotLocationsList();
        System.out.println(depotLocationList.locations.size() + " depots were loaded.");
        //depotLocationList.display();

        // load the store list.
        StoreLocationList storeLocationList = shippingBibleWorkbook.buildStoreLocationsList();
        System.out.println(storeLocationList.locations.size() + " stores were loaded.");
        //storeLocationList.display();

        // create the routes for each store.
        RouteListsList routeListsList = new RouteListsList();
        routeListsList.add(shippingBibleWorkbook.buildDepotToStoreRouteList("Daventry Clothing", "FRONT"));
        routeListsList.add(shippingBibleWorkbook.buildDepotToStoreRouteList("Chesterfield", "FRONT" ));
        System.out.println(routeListsList.getRouteLists().size() + " depots were routed.");
        routeListsList.sort();
        for (RouteList depotToStoreRouteList : routeListsList.getRouteLists()) {
            depotToStoreRouteList.display();
            break;
        }

        // Serialize the objects to a file.
        try {
            FileOutputStream fileOut = new FileOutputStream("ShippingBibleWorkbookExtract.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("Saving Depot locations to serialized file.");
            out.writeObject(depotLocationList);
            System.out.println("Saving Store locations to serialized file.");
            out.writeObject(storeLocationList);
            System.out.println("Saving Route Lists to serialized file.");
            out.writeObject(routeListsList);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in ShippingBibleWorkbookExtract.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
