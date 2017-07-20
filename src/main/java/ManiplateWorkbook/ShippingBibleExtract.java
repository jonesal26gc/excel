package ManiplateWorkbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ShippingBibleExtract {
    public static void main(String[] args) {
        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook("Transport Bible.xlsx");

        // load the depot list.
        DepotLocationsList depotLocationsList = shippingBibleWorkbook.buildDepotLocationsList();
        System.out.println(depotLocationsList.locations.size() + " depots were loaded.");
        //depotLocationsList.display();

        // load the store list.
        StoreLocationsList storeLocationsList = shippingBibleWorkbook.buildStoreLocationsList();
        System.out.println(storeLocationsList.locations.size() + " stores were loaded.");
        //storeLocationsList.display();

        // create the routes for each store.
        ArrayList<DepotToStoreRouteList> depotLocationsLists = new ArrayList<DepotToStoreRouteList>();
        depotLocationsLists.add(shippingBibleWorkbook.buildDepotToStoreRouteList("Daventry Clothing"));
        depotLocationsLists.add(shippingBibleWorkbook.buildDepotToStoreRouteList("Chesterfield"));
        for (DepotToStoreRouteList depotToStoreRouteList:depotLocationsLists){
            depotToStoreRouteList.display();
        }
    }
}
