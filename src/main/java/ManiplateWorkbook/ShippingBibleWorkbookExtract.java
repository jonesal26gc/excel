package ManiplateWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ShippingBibleWorkbookExtract {

    public static final String SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME = "ShippingBibleSerializedExtract.ser";
    public static final String TRANSPORT_BIBLE_FILENAME = "Transport Bible.xlsx";
    public static final String ROUTE_TYPE_CODE = "FRONT";

    public static void main(String[] args) {
        System.out.println("*** Transport Bible extract processing for Shipping Network database update ***");

        deleteSerializedExtractFile();

        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook(TRANSPORT_BIBLE_FILENAME);

        // load the depot list.
        LocationList depotLocationList = shippingBibleWorkbook.buildDepotLocationList();
        System.out.println("INFO - " + depotLocationList.locations.size() + " Depot Locations were extracted.");
        //depotLocationList.display();

        // load the store list.
        LocationList storeLocationList = shippingBibleWorkbook.buildStoreLocationList();
        System.out.println("INFO - " + storeLocationList.locations.size() + " Store Locations were extracted.");
        //storeLocationList.display();

        // create the routes for each store.
        RouteListsArray routeListsArray = new RouteListsArray();
        routeListsArray.add(shippingBibleWorkbook.buildRouteList("Daventry Clothing", ROUTE_TYPE_CODE));
        routeListsArray.add(shippingBibleWorkbook.buildRouteList("Chesterfield", ROUTE_TYPE_CODE));
        System.out.println("INFO - " + routeListsArray.getRouteLists().size() + " Depot/Store Routes were extracted.");
//        routeListsArray.sort();
//        for (RouteList routeList : routeListsArray.getRouteLists()) {
//            routeList.display();
//            break;
//        }

        // Serialize the objects to a file.
        createSerializedExtractFile(depotLocationList, storeLocationList, routeListsArray);
    }

    private static void deleteSerializedExtractFile() {
        File file = new File(SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME);
        if (file.delete()) {
            System.out.println("INFO - Pre-existing extract file " + file.getName() + " has been deleted.");
        }
    }

    private static void createSerializedExtractFile(LocationList depotLocationList, LocationList storeLocationList, RouteListsArray routeListsArray) {
        try {
            FileOutputStream fileOut = new FileOutputStream(SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("INFO - Saving Depot locations to serialized file...");
            out.writeObject(depotLocationList);
            System.out.println("INFO - Saving Store locations to serialized file...");
            out.writeObject(storeLocationList);
            System.out.println("INFO - Saving Depot/Store Route Lists to serialized file...");
            out.writeObject(routeListsArray);
            out.close();
            fileOut.close();
            System.out.printf("INFO - Serialized data is saved in " + SHIPPING_BIBLE_SERIALIZED_EXTRACT_FILENAME);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
