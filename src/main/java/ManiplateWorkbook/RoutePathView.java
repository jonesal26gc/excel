package ManiplateWorkbook;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "RoutePathView")
@Table(name = "ROUTE_PATH_VIEW")
@NamedNativeQueries({
        @NamedNativeQuery(name = "routePathSourceToDestination",
                query = "select " +
                        "  route_number" +
                        ", route_type_code" +
                        ", location_code_start" +
                        ", location_code_end" +
                        ", start_date" +
                        ", end_date" +
                        ", leg_number" +
                        ", location_code_from" +
                        ", location_from_name" +
                        ", location_from_type_code" +
                        ", location_from_format" +
                        ", location_code_to" +
                        ", location_to_name" +
                        ", location_to_type_code" +
                        ", location_to_format" +
                        " from route_path_view" +
                        " where route_type_code     = :routeTypeCode" +
                        "   and location_code_start = :locationCodeStart" +
                        "   and location_code_end   = :locationCodeEnd" +
                        " order by route_number asc" +
                        "        , leg_number asc"
        )})

public class RoutePathView {
    @Column(name = "route_number", insertable = false, updatable = false)
    private int routeNumber;
    @Column(name = "route_type_code", insertable = false, updatable = false)
    private String routeTypeCode;
    @Column(name = "location_code_start", insertable = false, updatable = false)
    private int locationCodeStart;
    @Column(name = "location_code_end", insertable = false, updatable = false)
    private int locationCodeEnd;
    @Column(name = "start_date", insertable = false, updatable = false)
    private Date startDate;
    @Column(name = "end_date", insertable = false, updatable = false)
    private Date endDate;
    @Id
    @Column(name = "leg_number", insertable = false, updatable = false)
    private int legNumber;
    @Column(name = "location_code_from", insertable = false, updatable = false)
    private int locationCodeFrom;
    @Column(name = "location_from_name", insertable = false, updatable = false)
    private String locationFromName;
    @Column(name = "location_from_type_code", insertable = false, updatable = false)
    private String locationFromTypeCode;
    @Column(name = "location_from_format", insertable = false, updatable = false)
    private String locationFromFormat;
    @Column(name = "location_code_to", insertable = false, updatable = false)
    private int locationCodeTo;
    @Column(name = "location_to_name", insertable = false, updatable = false)
    private String locationToName;
    @Column(name = "location_to_type_code", insertable = false, updatable = false)
    private String locationToTypeCode;
    @Column(name = "location_to_format", insertable = false, updatable = false)
    private String locationToFormat;

    public RoutePathView(int routeNumber, String routeTypeCode, int locationCodeStart, int locationCodeEnd, Date startDate, Date endDate, int legNumber, int locationCodeFrom, String locationFromName, String locationFromTypeCode, String locationFromFormat, int locationCodeTo, String locationToName, String locationToTypeCode, String locationToFormat) {
        this.routeNumber = routeNumber;
        this.routeTypeCode = routeTypeCode;
        this.locationCodeStart = locationCodeStart;
        this.locationCodeEnd = locationCodeEnd;
        this.startDate = startDate;
        this.endDate = endDate;
        this.legNumber = legNumber;
        this.locationCodeFrom = locationCodeFrom;
        this.locationFromName = locationFromName;
        this.locationFromTypeCode = locationFromTypeCode;
        this.locationFromFormat = locationFromFormat;
        this.locationCodeTo = locationCodeTo;
        this.locationToName = locationToName;
        this.locationToTypeCode = locationToTypeCode;
        this.locationToFormat = locationToFormat;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteTypeCode() {
        return routeTypeCode;
    }

    public void setRouteTypeCode(String routeTypeCode) {
        this.routeTypeCode = routeTypeCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getLocationFromName() {
        return locationFromName;
    }

    public void setLocationFromName(String locationFromName) {
        this.locationFromName = locationFromName;
    }

    public String getLocationFromTypeCode() {
        return locationFromTypeCode;
    }

    public void setLocationFromTypeCode(String locationFromTypeCode) {
        this.locationFromTypeCode = locationFromTypeCode;
    }

    public String getLocationFromFormat() {
        return locationFromFormat;
    }

    public void setLocationFromFormat(String locationFromFormat) {
        this.locationFromFormat = locationFromFormat;
    }

    public int getLocationCodeTo() {
        return locationCodeTo;
    }

    public void setLocationCodeTo(int locationCodeTo) {
        this.locationCodeTo = locationCodeTo;
    }

    public String getLocationToName() {
        return locationToName;
    }

    public void setLocationToName(String locationToName) {
        this.locationToName = locationToName;
    }

    public String getLocationToTypeCode() {
        return locationToTypeCode;
    }

    public void setLocationToTypeCode(String locationToTypeCode) {
        this.locationToTypeCode = locationToTypeCode;
    }

    public String getLocationToFormat() {
        return locationToFormat;
    }

    public void setLocationToFormat(String locationToFormat) {
        this.locationToFormat = locationToFormat;
    }

    public int getLocationCodeStart() {
        return locationCodeStart;
    }

    public void setLocationCodeStart(int locationCodeStart) {
        this.locationCodeStart = locationCodeStart;
    }

    public int getLocationCodeEnd() {
        return locationCodeEnd;
    }

    public void setLocationCodeEnd(int locationCodeEnd) {
        this.locationCodeEnd = locationCodeEnd;
    }

    @Override
    public String toString() {
        return "RoutePathView{" +
                "routeNumber=" + routeNumber +
                ", routeTypeCode='" + routeTypeCode + '\'' +
                ", locationCodeStart=" + locationCodeStart +
                ", locationCodeEnd=" + locationCodeEnd +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", legNumber=" + legNumber +
                ", locationCodeFrom=" + locationCodeFrom +
                ", locationFromName='" + locationFromName + '\'' +
                ", locationFromTypeCode='" + locationFromTypeCode + '\'' +
                ", locationFromFormat='" + locationFromFormat + '\'' +
                ", locationCodeTo=" + locationCodeTo +
                ", locationToName='" + locationToName + '\'' +
                ", locationToTypeCode='" + locationToTypeCode + '\'' +
                ", locationToFormat='" + locationToFormat + '\'' +
                '}';
    }
}



