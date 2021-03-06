package ManiplateWorkbook;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "firstTenRoutes",
                query = "from route " +
                        " where route_number between 1 and 10" +
                        " order by route_number asc"),
        @NamedQuery(name = "allRoutes",
                query = "from route ")})

@NamedNativeQueries({@NamedNativeQuery(name = "deleteRoute",
        query = "DELETE FROM ROUTE" +
                " where route_number = :routeNumber")})

@Entity(name = "route")
@Table(name = "ROUTE")
public class Route implements Serializable, Comparable {

    @SequenceGenerator(
            name = "RouteNumberGenerator",              // name of this generator.
            initialValue = 0,                           // starting value if the row is not already present.
            allocationSize = 1,                         // increment.
            sequenceName = "route_number_sequence")     // name of the sequence.

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RouteNumberGenerator")
    @Column(name = "route_number")
    private int route_number;

    @Column(name = "location_code_start")
    private int locationCodeStart;

    @Column(name = "location_code_end")
    private int locationCodeEnd;

    @Column(name = "route_type_code")
    private String routeTypeCode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "route_number")
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
