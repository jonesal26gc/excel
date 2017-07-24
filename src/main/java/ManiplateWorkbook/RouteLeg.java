package ManiplateWorkbook;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "allRouteLegs",
                query = "from route_leg " +
                        " where route_number = :routeNumber" +
                        " order by leg_number asc")})

@Entity(name = "route_leg")
@Table(name = "ROUTE_LEG")
public class RouteLeg implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROUTE_NUMBER", nullable = false)
    private Route route;
    @Id
    @Column(name = "LEG_NUMBER")
    private int legNumber;
    @Column(name = "location_code_from")
    private int locationCodeFrom;
    @Column(name = "location_code_to")
    private int locationCodeTo;

    public RouteLeg() {
    }

    public RouteLeg(int legNumber, int locationCodeFrom, int locationCodeTo) {
        this.legNumber = legNumber;
        this.locationCodeFrom = locationCodeFrom;
        this.locationCodeTo = locationCodeTo;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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
                "legNumber=" + legNumber +
                ", locationCodeFrom=" + locationCodeFrom +
                ", locationCodeTo=" + locationCodeTo +
                '}';
    }

    public boolean routeLegMatch(RouteLeg routeLeg) {
        if (this.legNumber != routeLeg.legNumber
                | this.locationCodeFrom != routeLeg.locationCodeFrom
                | this.locationCodeTo != routeLeg.locationCodeTo) {
            return false;
        }
        return true;
    }
}
