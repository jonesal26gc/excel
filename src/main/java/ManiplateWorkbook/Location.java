package ManiplateWorkbook;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
        @NamedQuery(name = "allDepotLocations",
                query = "from location " +
                        " where location_type_code = 'DEPOT'" +
                        " order by location_code asc"),
        @NamedQuery(name = "allStoreLocations",
                query = "from location " +
                        " where location_type_code = 'STORE'")})

@Entity(name="location")
@Table(name = "LOCATION")
public class Location implements Serializable, Comparable {
    @Id
    @Column(name = "LOCATION_CODE")
    private int locationCode;
    @Column(name = "LOCATION_TYPE_CODE")
    private String locationTypeCode;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FORMAT")
    private String format;
    @Column(name = "COUNTY")
    private String county;
    @Column(name = "POSTCODE")
    private String postcode;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "REPORTING_REGION")
    private String reportingRegion;

    public Location() {
    }

    public Location(int locationCode, String locationTypeCode, String name, String format) {
        this.locationCode = locationCode;
        this.locationTypeCode = locationTypeCode;
        this.name = name;
        this.format = format;
    }

    public Location(int locationCode, String locationTypeCode, String name, String format, String county, String postcode, String country, String reportingRegion) {
        this.locationCode = locationCode;
        this.locationTypeCode = locationTypeCode;
        this.name = name;
        this.format = format;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
        this.reportingRegion = reportingRegion;
    }

    public String getLocationTypeCode() {
        return locationTypeCode;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public String getCounty() {
        return county;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getReportingRegion() {
        return reportingRegion;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationCode=" + locationCode +
                ", locationTypeCode='" + locationTypeCode + '\'' +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", reportingRegion='" + reportingRegion + '\'' +
                '}';
    }

    public int compareTo(Object o) {
        return this.getLocationCode() - ((Location) o).getLocationCode();
    }

    public int getLocationCode() {
        return locationCode;
    }
}
