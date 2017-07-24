package ManiplateWorkbook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

@Entity(name="route")
@Table(name = "ROUTE")
public class Route implements Serializable, Comparable {

    @TableGenerator(
            name = "RouteNumberGenerator",      // name of this generator.
            initialValue = 0,                   // starting value if the row is not already present.
            allocationSize = 1,                 // increment.
            table = "route_number_sequence",      // table name for sequences.
            pkColumnName = "route_number_sequence",     // 'key' column name.
            valueColumnName = "last_value",       // 'next value' column name.
            pkColumnValue = "ROUTE_NUMBER")        // key value for this row.

    @Id
    @Column(name = "route_number")
    private int route_number;
    @Column(name = "location_code_start")
    private int locationCodeStart;
    @Column(name = "location_code_end")
    private int locationCodeEnd;
    @Column(name = "route_type_code")
    private String routeTypeCode;
    @OneToMany(mappedBy = "route")
    private Set<RouteLeg> routeLegs;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    public Route() {
    }

    public Route(int route_number, int locationCodeStart, int locationCodeEnd, String routeTypeCode, Set<RouteLeg> routeLegs, Date startDate, Date endDate) {
        this.route_number = route_number;
        this.locationCodeStart = locationCodeStart;
        this.locationCodeEnd = locationCodeEnd;
        this.routeTypeCode = routeTypeCode;
        this.routeLegs = routeLegs;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Set<RouteLeg> getRouteLegs() {
        return routeLegs;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Route{" +
                "route_number=" + route_number +
                ", locationCodeStart=" + locationCodeStart +
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

    public int getLocationCodeStart() {
        return locationCodeStart;
    }

    public int getLocationCodeEnd() {
        return locationCodeEnd;
    }

    public String getRouteTypeCode() {
        return routeTypeCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getRoute_number() {
        return route_number;
    }

    public void setRoute_number(int route_number) {
        this.route_number = route_number;
    }
}
