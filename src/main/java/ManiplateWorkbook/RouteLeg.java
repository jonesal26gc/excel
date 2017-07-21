package ManiplateWorkbook;

import java.io.Serializable;

public class RouteLeg implements Serializable {
    private int routeNumber;
    private int legNumber;
    private int locationCodeFrom;
    private int locationCodeTo;

    public RouteLeg(int routeNumber, int legNumber, int locationCodeFrom, int locationCodeTo) {
        this.routeNumber = routeNumber;
        this.legNumber = legNumber;
        this.locationCodeFrom = locationCodeFrom;
        this.locationCodeTo = locationCodeTo;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public int getLegNumber() {
        return legNumber;
    }

    public void setLegNumber(int legNumber) {
        this.legNumber = legNumber;
    }

    public int getLocationCodeFrom() {
        return locationCodeFrom;
    }

    public void setLocationCodeFrom(int locationCodeFrom) {
        this.locationCodeFrom = locationCodeFrom;
    }

    public int getLocationCodeTo() {
        return locationCodeTo;
    }

    public void setLocationCodeTo(int locationCodeTo) {
        this.locationCodeTo = locationCodeTo;
    }

    @Override
    public String toString() {
        return "RouteLeg{" +
                "routeNumber=" + routeNumber +
                ", legNumber=" + legNumber +
                ", locationCodeFrom=" + locationCodeFrom +
                ", locationCodeTo=" + locationCodeTo +
                '}';
    }
}
