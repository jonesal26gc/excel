package ManiplateWorkbook;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "firstTenRoutes",
                query = "from route " +
                        " where route_number between 1 and 10" +
                        " order by route_number asc")})

@Entity(name = "route")
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
    @OneToMany(mappedBy = "route", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    //@JoinColumns(@JoinColumn(name="route_number",referencedColumnName = "route_number"))
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
        int result = this.getLocationCodeStart() - ((Route) o).getLocationCodeStart();
        if (result != 0) {
            return result;
        }
        result = this.getLocationCodeEnd() - ((Route) o).getLocationCodeEnd();
        if (result != 0) {
            return result;
        }
        result = this.getRouteTypeCode().compareTo(((Route) o).getRouteTypeCode());
        if (result != 0) {
            return result;
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

    public boolean routeMatches(Route route) {
        if (!(this.locationCodeStart == route.locationCodeStart
                && this.locationCodeEnd == route.locationCodeEnd
                && this.routeTypeCode.equals(route.routeTypeCode)
                && this.startDate == route.startDate
                && this.endDate == route.endDate
                && this.routeLegs.size() == route.routeLegs.size())) {
            return false;
        }
        //Collections.sort(this.routeLegs);
        for (RouteLeg routeLeg : this.routeLegs) {
            if (!(routeLeg.routeLegMatch(routeLeg))) {
                return false;
            }
        }
        return true;
    }
}
