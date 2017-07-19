package ManiplateWorkbook;

public class ShippingBibleExtract {
    public static void main(String[] args) {
        ShippingBibleWorkbook shippingBibleWorkbook = new ShippingBibleWorkbook("Transport Bible.xlsx");
        System.out.println("Bible Start Date is :" + shippingBibleWorkbook.getStartDate());
        System.out.println("Bible End Date is   :" + shippingBibleWorkbook.getEndDate());

        // load the depot cross-reference.
        DepotCrossReference depotCrossReference = shippingBibleWorkbook.buildDepotCrossReference();
        System.out.println(depotCrossReference.displayCount() + " depots were loaded to the cross-reference.");
//        depotCrossReference.display();
//        for (String depot : depotCrossReference.lookup("Airberg")) {
//            System.out.println(depot);
//        }

        // load the depot list.
        DepotLocationsList depotLocationsList = shippingBibleWorkbook.buildDepotLocationsList();
        System.out.println(depotLocationsList.locations.size() + " depots were loaded.");
        //depotLocationsList.display();

        // load the store list.
        StoreLocationsList storeLocationsList = shippingBibleWorkbook.buildStoreLocationsList();
        System.out.println(storeLocationsList.locations.size() + " stores were loaded.");
        //storeLocationsList.display();

        // create the routes for each store.
    }
}
