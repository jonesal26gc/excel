package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class DepotToStoreRouteList implements Serializable, Comparable {
    private String depot;
    private ArrayList<DepotToStoreRoute> depotToStoreRoutes = new ArrayList<DepotToStoreRoute>();

    public DepotToStoreRouteList(String depot) {
        this.depot = depot;
    }

    public String getDepot() {
        return depot;
    }

    public ArrayList<DepotToStoreRoute> getDepotToStoreRoutes() {
        return depotToStoreRoutes;
    }

    public void add(DepotToStoreRoute depotToStoreRoute) {
        depotToStoreRoutes.add(depotToStoreRoute);
    }

    public void display() {
        for (DepotToStoreRoute depotToStoreRoute:depotToStoreRoutes){
            System.out.println(depotToStoreRoute.toString());
        }
    }

    public void sort(){
        Collections.sort(depotToStoreRoutes);
    }

    public int compareTo(Object o) {
        return Integer.parseInt(this.getDepot()) - Integer.parseInt(((DepotToStoreRouteList)o).getDepot());
    }
}
