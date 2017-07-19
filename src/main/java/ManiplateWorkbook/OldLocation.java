package ManiplateWorkbook;

import java.util.ArrayList;
import java.util.List;

public class OldLocation {

    private String locationId;
    private String locationName;
    private List<LocationType> locationTypes = new ArrayList<LocationType>();
    private String postcode;
    private String format;
    private String reportingRegion;
    private String county;
    private String country;

    public OldLocation(){}

    public OldLocation(String locationId, String locationName, List<LocationType> locationType) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.locationTypes = locationType;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<LocationType> getLocationTypes() {
        return locationTypes;
    }

    public void setLocationTypes(List<LocationType> locationType) {
        this.locationTypes = locationType;
    }

    public void addLocationType(LocationType locationType){
        this.locationTypes.add(locationType);
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReportingRegion() {
        return reportingRegion;
    }

    public void setReportingRegion(String reportingRegion) {
        this.reportingRegion = reportingRegion;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OldLocation oldLocation = (OldLocation) o;

        if (locationId != null ? !locationId.equals(oldLocation.locationId)
                : oldLocation.locationId != null) {
            return false;
        }
        if (locationName != null ? !locationName.equals(oldLocation.locationName)
                : oldLocation.locationName != null) {
            return false;
        }
        if (locationTypes != null ? !locationTypes.equals(oldLocation.locationTypes)
                : oldLocation.locationTypes != null) {
            return false;
        }
        if (postcode != null ? !postcode.equals(oldLocation.postcode) : oldLocation.postcode != null) {
            return false;
        }
        if (format != null ? !format.equals(oldLocation.format) : oldLocation.format != null) {
            return false;
        }
        if (reportingRegion != null ? !reportingRegion.equals(oldLocation.reportingRegion)
                : oldLocation.reportingRegion != null) {
            return false;
        }
        if (county != null ? !county.equals(oldLocation.county) : oldLocation.county != null) {
            return false;
        }
        return country != null ? country.equals(oldLocation.country) : oldLocation.country == null;
    }

    @Override
    public int hashCode() {
        int result = locationId != null ? locationId.hashCode() : 0;
        result = 31 * result + (locationName != null ? locationName.hashCode() : 0);
        result = 31 * result + (locationTypes != null ? locationTypes.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (reportingRegion != null ? reportingRegion.hashCode() : 0);
        result = 31 * result + (county != null ? county.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OldLocation{" +
                "locationId='" + locationId + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationTypes=" + locationTypes +
                ", postcode='" + postcode + '\'' +
                ", format='" + format + '\'' +
                ", reportingRegion='" + reportingRegion + '\'' +
                ", county='" + county + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

