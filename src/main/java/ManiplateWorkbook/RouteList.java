package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RouteList implements Serializable, Comparable {
    private int locationCodeFrom;
    private ArrayList<Route> routes = new ArrayList<Route>();

    public RouteList(int locationCodeFrom) {
        this.locationCodeFrom = locationCodeFrom;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void add(Route route) {
        routes.add(route);
    }

    public void display() {
        for (Route route : routes) {
            System.out.println(route.toString());
        }
    }

    public void sort() {
        Collections.sort(routes);
    }

    public int compareTo(Object o) {
        return this.getLocationCodeFrom() - ((RouteList) o).getLocationCodeFrom();
    }

    public int getLocationCodeFrom() {
        return locationCodeFrom;
    }
}