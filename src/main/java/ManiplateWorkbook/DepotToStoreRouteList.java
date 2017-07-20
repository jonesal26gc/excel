package ManiplateWorkbook;

import java.util.ArrayList;

public class DepotToStoreRouteList {
    private String depot;
    private ArrayList<DepotToStoreRoute> depotToStoreRoutes = new ArrayList<DepotToStoreRoute>();

    public DepotToStoreRouteList(String depot) {
        this.depot = depot;
    }

    public String getDepot() {
        return depot;
    }

    public void add(DepotToStoreRoute depotToStoreRoute) {
        depotToStoreRoutes.add(depotToStoreRoute);
    }

    public void display() {
        for (DepotToStoreRoute depotToStoreRoute:depotToStoreRoutes){
            System.out.println(depotToStoreRoute.toString());
        }
    }
}
