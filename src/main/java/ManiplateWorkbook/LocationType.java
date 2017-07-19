package ManiplateWorkbook;

public enum LocationType {
    DEPOT,
    STORE,
    NATIONAL_DEPOT,
    REGIONAL_DEPOT,
    EXPRESS;

    public static LocationType determineLocationType(String locationTypeAsString) {
        for (LocationType locationType : LocationType.values()) {
            if (locationTypeAsString.toUpperCase().equals(locationType.name())) {
                return locationType;
            }
        }
        return null;
    }
}

