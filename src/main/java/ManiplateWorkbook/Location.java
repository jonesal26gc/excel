package ManiplateWorkbook;

public class Location {
    private String locationCode;
    private String locationTypeCode;
    private String name;
    private String format;
    private String county;
    private String postcode;
    private String country;
    private String reportingRegion;

    public Location(String locationCode, String locationTypeCode, String name, String format) {
        this.locationCode = locationCode;
        this.locationTypeCode = locationTypeCode;
        this.name = name;
        this.format = format;
    }

    public Location(String locationCode, String locationTypeCode, String name, String format, String county, String postcode, String country, String reportingRegion) {
        this.locationCode = locationCode;
        this.locationTypeCode = locationTypeCode;
        this.name = name;
        this.format = format;
        this.county = county;
        this.postcode = postcode;
        this.country = country;
        this.reportingRegion = reportingRegion;
    }

    public String getLocationCode() {
        return locationCode;
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
                "locationCode='" + locationCode + '\'' +
                ", locationTypeCode='" + locationTypeCode + '\'' +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", county='" + county + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", reportingRegion='" + reportingRegion + '\'' +
                '}';
    }
}
