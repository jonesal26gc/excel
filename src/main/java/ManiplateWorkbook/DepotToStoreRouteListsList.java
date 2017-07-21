package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DepotToStoreRouteListsList implements Serializable {
    private ArrayList<DepotToStoreRouteList> depotToStoreRouteLists = new ArrayList<DepotToStoreRouteList>();

    public void add(DepotToStoreRouteList depotToStoreRouteList) {
        depotToStoreRouteLists.add(depotToStoreRouteList);
    }

    public ArrayList<DepotToStoreRouteList> getDepotToStoreRouteLists() {
        return depotToStoreRouteLists;
    }

    public void sort() {
        Collections.sort(depotToStoreRouteLists);
    }
}
