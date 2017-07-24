package ManiplateWorkbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Route implements Serializable, Comparable {
    private int locationCodeStart;
    private int locationCodeEnd;
    private String routeTypeCode;
    private ArrayList<RouteLeg> routeLegs;
    private Date startDate;
    private Date endDate;

    public Route(int locationCodeStart, int locationCodeEnd, String routeTypeCode, ArrayList<RouteLeg> routeLegs, Date startDate, Date endDate) {
        this.locationCodeStart = locationCodeStart;
        this.locationCodeEnd = locationCodeEnd;
        this.routeTypeCode = routeTypeCode;
        this.routeLegs = routeLegs;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getRouteTypeCode() {
        return routeTypeCode;
    }

    public ArrayList<RouteLeg> getRouteLegs() {
        return routeLegs;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Route{" +
                "locationCodeStart=" + locationCodeStart +
                ", locationCodeEnd=" + locationCodeEnd +
                ", routeTypeCode='" + routeTypeCode + '\'' +
                ", routeLegs=" + routeLegs +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    // Compare the Route objects on Start, End, type and start date sequence.
    public int compareTo(Object o) {
        if (this.getLocationCodeStart() - ((Route) o).getLocationCodeStart() < 0) {
            return -1;
        }
        if (this.getLocationCodeEnd() - ((Route) o).getLocationCodeEnd() < 0) {
            return -1;
        }
        if (this.getRouteTypeCode().compareTo(((Route) o).getRouteTypeCode()) < 0) {
            return -1;
        }
        return this.getStartDate().compareTo(((Route) o).getStartDate());
    }

    public int getLocationCodeEnd() {
        return locationCodeEnd;
    }

    public int getLocationCodeStart() {
        return locationCodeStart;
    }
}
