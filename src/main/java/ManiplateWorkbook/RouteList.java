package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RouteList implements Serializable, Comparable {
    private int locationCodeFrom;
    private String name;
    private String route_type_code;
    private ArrayList<Route> routes = new ArrayList<Route>();

    public RouteList(int locationCodeFrom, String name, String route_type_code) {
        this.locationCodeFrom = locationCodeFrom;
        this.name = name;
        this.route_type_code = route_type_code;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
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

    public void setLocationCodeFrom(int locationCodeFrom) {
        this.locationCodeFrom = locationCodeFrom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute_type_code() {
        return route_type_code;
    }

    public void setRoute_type_code(String route_type_code) {
        this.route_type_code = route_type_code;
    }
}