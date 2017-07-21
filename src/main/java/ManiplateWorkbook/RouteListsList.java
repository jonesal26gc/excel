package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RouteListsList implements Serializable {
    private ArrayList<RouteList> routeLists = new ArrayList<RouteList>();

    public void add(RouteList routeList) {
        routeLists.add(routeList);
    }

    public ArrayList<RouteList> getRouteLists() {
        return routeLists;
    }

    public void sort() {
        Collections.sort(routeLists);
    }
}
