package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ShippingBibleWorkbook {
    public static final int WORKSHEET_DEPOT_NAME_HEADER_ROW = 0;
    public static final int WORKSHEET_DEPOT_NAME_DATA_ROW_START = 1;
    public static final int WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN = 1;
    public static final int WORKSHEET_DEPOT_NAME_STORE_NAME_COLUMN = 2;
    public static final int WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN = 3;
    public static final int WORKSHEET_DEPOT_NAME_FORMAT_COLUMN = 4;
    public static final int WORKSHEET_DEPOT_NAME_REPORTING_REGION_COLUMN = 5;
    public static final int WORKSHEET_DEPOT_NAME_COUNTY_COLUMN = 6;
    public static final int WORKSHEET_DEPOT_NAME_COUNTRY_COLUMN = 7;
    public static final int WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN = 33;
    public static final int WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN = 34;
    public static final int WORKSHEET_DEPOT_NAME_NATIONAL_DEPOT_COLUMN_START = 10;
    public static final int WORKSHEET_DEPOT_NAME_NATIONAL_DEPOT_COLUMN_END = 18;
    public static final String WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN_TITLE = "Retail Outlet No";
    public static final String WORKSHEET_DEPOT_NAME_STORE_NAME_COLUMN_TITLE = "Store Name";
    public static final String WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN_TITLE = "Post Code";
    public static final String WORKSHEET_DEPOT_NAME_FORMAT_COLUMN_TITLE = "Format";
    public static final String WORKSHEET_DEPOT_NAME_REPORTING_REGION_COLUMN_TITLE = "Reporting Region";
    public static final String WORKSHEET_DEPOT_NAME_COUNTY_COLUMN_TITLE = "County";
    public static final String WORKSHEET_DEPOT_NAME_COUNTRY_COLUMN_TITLE = "Country";
    public static final String WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN_TITLE = "Bible Start Date";
    public static final String WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN_TITLE = "Bible End Date";
    public static final int WORKSHEET_DEPOT_CODES_HEADER_ROW = 1;
    public static final int WORKSHEET_DEPOT_CODES_DATA_ROW_START = 2;
    public static final int WORKSHEET_DEPOT_CODES_DEPOT_NAME_COLUMN = 2;
    public static final int WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN = 3;
    public static final int WORKSHEET_DEPOT_CODES_STREAM_COLUMN = 4;
    public static final String WORKSHEET_DEPOT_CODES_DEPOT_NAME_COLUMN_TITLE = "Depot & Trunk Link";
    public static final String WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN_TITLE = "Whse Number";
    public static final String WORKSHEET_DEPOT_CODES_STREAM_COLUMN_TITLE = "Stream";
    public static final int DEPOT_NUMBER_LENGTH = 3;
    public static final int STORE_NUMBER_MIN = 1000;
    public static final int STORE_NUMBER_MAX = 99999;
    private FileInputStream fis;
    private XSSFWorkbook workbook;
    private XSSFSheet depotNameWorksheet;
    private XSSFSheet depotCodesWorksheet;
    private Date startDate;
    private Date endDate;
    private String filename;
    private DepotCrossReference depotCrossReference;

    public ShippingBibleWorkbook(String filename) {
        this.filename = filename;
        try {
            this.fis = new FileInputStream(new File(filename));
            this.workbook = new XSSFWorkbook(fis);
            this.depotCodesWorksheet = workbook.getSheet("Depot Codes");
            this.depotNameWorksheet = workbook.getSheet("Depot Name");
            this.filename = filename;
            this.depotCrossReference = new DepotCrossReference();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        retrieveEffectiveDatesFromDepotNameWorksheet();
        buildDepotCrossReference();
        depotCrossReference.display();
        System.out.println("Bible Start Date is :" + startDate);
        System.out.println("Bible End Date is   :" + endDate);
    }

    public void retrieveEffectiveDatesFromDepotNameWorksheet() {
        try {
            XSSFRow currentRow = depotNameWorksheet.getRow(WORKSHEET_DEPOT_NAME_DATA_ROW_START);
            startDate = currentRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN).getDateCellValue();
            endDate = currentRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN).getDateCellValue();
        } catch (Exception ex) {
            throw new RuntimeException("ERROR - Unable to establish the Bible Start/End date(s).");
        }
    }

    public void buildDepotCrossReference() {
        verifyLocationOfDepotCodesTableData();
        for (int rowNumber = WORKSHEET_DEPOT_CODES_DATA_ROW_START; rowNumber < depotCodesWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow dataRow = depotCodesWorksheet.getRow(rowNumber);
            if (dataRow == null) {
                break;
            }
            String depotName = retrieveDepotName(dataRow);
            String concatenatedDepotNumbers = retrieveConcatenatedDepotNumbers(dataRow);
            depotCrossReference.add(depotName.toUpperCase(), buildDepotNumberArray(concatenatedDepotNumbers));
        }
        System.out.println(depotCrossReference.displayCount() + " depots were loaded to the cross-reference.");
    }

    private void verifyLocationOfDepotCodesTableData() {
        XSSFRow headerRow = depotCodesWorksheet.getRow(WORKSHEET_DEPOT_CODES_HEADER_ROW);
        if (!(headerRow.getCell(WORKSHEET_DEPOT_CODES_DEPOT_NAME_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_CODES_DEPOT_NAME_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_CODES_STREAM_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_CODES_STREAM_COLUMN_TITLE))) {
            throw new RuntimeException("ERROR - The 'Depot Codes' Header row cannot be verified. Check for misaligned data on the 'Depot Codes' Worksheet.");
        }
    }

    private String retrieveDepotName(XSSFRow currentRow) {
        return currentRow.getCell(WORKSHEET_DEPOT_CODES_DEPOT_NAME_COLUMN).getStringCellValue();
    }

    private String retrieveConcatenatedDepotNumbers(XSSFRow currentRow) {
        String concatenatedDepotNumbers = Integer.toString((int) currentRow.getCell(WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN).getNumericCellValue());
        if (concatenatedDepotNumbers.length() < DEPOT_NUMBER_LENGTH) {
            concatenatedDepotNumbers = String.format("%0" + DEPOT_NUMBER_LENGTH + "d", (int) currentRow.getCell(WORKSHEET_DEPOT_CODES_DEPOT_NUMBER_COLUMN).getNumericCellValue());
        }
        if (!(concatenatedDepotNumbers.length() % DEPOT_NUMBER_LENGTH == 0)) {
            throw new RuntimeException("ERROR - The concatenated depot number list is incorrectly formatted.");
        }
        return concatenatedDepotNumbers;
    }

    private String[] buildDepotNumberArray(String concatenatedDepotNumbers) {
        ArrayList<String> depotNumberArrayList = new ArrayList<String>();
        while (concatenatedDepotNumbers.length() > 0) {
            depotNumberArrayList.add(concatenatedDepotNumbers.substring(0, DEPOT_NUMBER_LENGTH));
            concatenatedDepotNumbers = concatenatedDepotNumbers.substring(DEPOT_NUMBER_LENGTH);
        }
        return depotNumberArrayList.toArray(new String[depotNumberArrayList.size()]);
    }

    public LocationList buildDepotLocationList() {
        LocationList depotLocationList = new LocationList();
        ArrayList<Integer> locationCodesEncountered = new ArrayList<Integer>();
        for (int rowNumber = WORKSHEET_DEPOT_CODES_DATA_ROW_START; rowNumber < depotCodesWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow dataRow = depotCodesWorksheet.getRow(rowNumber);
            if (dataRow == null) {
                break;
            }
            String concatenatedDepotNumbers = retrieveConcatenatedDepotNumbers(dataRow);
            if (concatenatedDepotNumbers.length() == DEPOT_NUMBER_LENGTH) {
                Integer locationCode = Integer.parseInt(concatenatedDepotNumbers);
                if (locationCodesEncountered.contains(locationCode)) {
                    System.out.println("WARNING - Duplicated Location (Depot) " + locationCode + " encountered.");
                    continue;
                }
                locationCodesEncountered.add(locationCode);
                depotLocationList.add(
                        new Location(locationCode,
                                "DEPOT",
                                retrieveDepotName(dataRow),
                                retrieveDepotStream(dataRow)));
            }
        }
        depotLocationList.sort();
        return depotLocationList;
    }

    private String retrieveDepotStream(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_CODES_STREAM_COLUMN).getStringCellValue();
    }

    public LocationList buildStoreLocationList() {
        verifyLocationOfDepotNameTableData();
        LocationList storeLocationList = new LocationList();
        ArrayList<Integer> locationCodesEncountered = new ArrayList<Integer>();

        for (int rowNumber = WORKSHEET_DEPOT_NAME_DATA_ROW_START; rowNumber < depotNameWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow dataRow = depotNameWorksheet.getRow(rowNumber);
            if (dataRow == null | !isValidStoreNumber(dataRow)) {
                break;
            }
            Integer locationCode = (int) dataRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue();
            if (locationCodesEncountered.contains(locationCode)) {
                System.out.println("WARNING - Duplicated Location (Store) " + locationCode + " encountered.");
                continue;
            }
            locationCodesEncountered.add(locationCode);
            storeLocationList.add(
                    new Location((int) dataRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue(),
                            "STORE",
                            retrieveStoreName(dataRow),
                            retrieveStoreFormat(dataRow),
                            retrieveStoreCounty(dataRow),
                            retrieveStorePostcode(dataRow),
                            retrieveStoreCountry(dataRow),
                            retrieveStoreReportingRegion(dataRow)));
        }
        storeLocationList.sort();
        return storeLocationList;
    }

    private void verifyLocationOfDepotNameTableData() {
        XSSFRow headerRow = depotNameWorksheet.getRow(WORKSHEET_DEPOT_NAME_HEADER_ROW);
        if (!(headerRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NAME_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_STORE_NAME_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_FORMAT_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_FORMAT_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_REPORTING_REGION_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_REPORTING_REGION_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_COUNTY_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_COUNTY_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_COUNTRY_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_COUNTRY_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN_TITLE)
                && headerRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN).getStringCellValue().equals(WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN_TITLE))) {
            throw new RuntimeException("ERROR - The 'Depot Name' Header row cannot be verified. Check for misaligned data on the 'Depot Name' Worksheet.");
        }
    }

    private boolean isValidStoreNumber(XSSFRow dataRow) {
        try {
            return (int) dataRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue() >= STORE_NUMBER_MIN;
        } catch (Exception ex) {
            return false;
        }
    }

    private String retrieveStoreName(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NAME_COLUMN).getStringCellValue();
    }

    private String retrieveStoreFormat(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_NAME_FORMAT_COLUMN).getStringCellValue();
    }

    private String retrieveStoreCounty(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_NAME_COUNTY_COLUMN).getStringCellValue();
    }

    private String retrieveStorePostcode(XSSFRow dataRow) {
        try {
            return dataRow.getCell(WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN).getStringCellValue();
        } catch (Exception e) {
            try {
                return Integer.toString((int) dataRow.getCell(WORKSHEET_DEPOT_NAME_POSTCODE_COLUMN).getNumericCellValue());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private String retrieveStoreCountry(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_NAME_COUNTRY_COLUMN).getStringCellValue();
    }

    private String retrieveStoreReportingRegion(XSSFRow dataRow) {
        return dataRow.getCell(WORKSHEET_DEPOT_NAME_REPORTING_REGION_COLUMN).getStringCellValue();
    }

    public RouteList buildRouteList(String targetDepot, String routeTypeCode) {
        int targetDepotColumn = findTargetDepotColumn(targetDepot);
        if (targetDepotColumn == 0) {
            return null;
        }
        RouteList routeList = new RouteList(Integer.parseInt(depotCrossReference.lookup(targetDepot.toUpperCase())[0]));
        for (int rowNumber = 1; rowNumber < depotNameWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotNameWorksheet.getRow(rowNumber);
            if (currentRow == null | !isValidStoreNumber(currentRow)) {
                break;
            }
            String[] depotList = depotCrossReference.lookup(currentRow.getCell(targetDepotColumn).getStringCellValue().toUpperCase());
            Set<RouteLeg> routeLegs = new LinkedHashSet<RouteLeg>();
            if (depotList == null) {
                System.out.println("ERROR - Cannot find '" + currentRow.getCell(targetDepotColumn).getStringCellValue() + "' in the DC Cross-Reference.");
                //throw new RuntimeException("ERROR - Cannot find '" + currentRow.getCell(targetDepotColumn).getStringCellValue() + "' in the DC Cross-Reference.");
//                routeLegs.add(new RouteLeg(routeNumber,
//                        legNumber,
//                        routeList.getLocationCodeFrom(),
//                        (int) currentRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue()));
            } else {
                if (depotList.equals("000")) {
                    break;
                }
                int legNumber = 1;
                int lastDepot = routeList.getLocationCodeFrom();
                for (String depot : depotList) {
                    routeLegs.add(new RouteLeg(legNumber,
                            lastDepot,
                            Integer.parseInt(depot)));
                    lastDepot = Integer.parseInt(depot);
                    legNumber++;
                }
                routeLegs.add(new RouteLeg(legNumber,
                        lastDepot,
                        (int) currentRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue()));
            }
            routeList.add(
                    new Route(0,
                            routeList.getLocationCodeFrom(),
                            (int) currentRow.getCell(WORKSHEET_DEPOT_NAME_STORE_NUMBER_COLUMN).getNumericCellValue(),
                            routeTypeCode,
                            routeLegs,
                            retrieveStoreBibleStartDate(currentRow),
                            retrieveStoreBibleEndDate(currentRow)));
        }
        routeList.sort();
        return routeList;
    }


    private int findTargetDepotColumn(String targetDepot) {
        XSSFRow headingsRow = depotNameWorksheet.getRow(WORKSHEET_DEPOT_NAME_HEADER_ROW);
        for (int headingsColumn = WORKSHEET_DEPOT_NAME_NATIONAL_DEPOT_COLUMN_START; headingsColumn <= WORKSHEET_DEPOT_NAME_NATIONAL_DEPOT_COLUMN_END; headingsColumn++) {
            if (headingsRow.getCell(headingsColumn).getStringCellValue().equals(targetDepot)) {
                return headingsColumn;
            }
        }
        return 0;
    }

    private Date retrieveStoreBibleStartDate(XSSFRow currentRow) {
        try {
            return currentRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_START_DATE_COLUMN).getDateCellValue();
        } catch (Exception ex) {
            return startDate;
        }
    }

    private Date retrieveStoreBibleEndDate(XSSFRow currentRow) {
        try {
            return currentRow.getCell(WORKSHEET_DEPOT_NAME_BIBLE_END_DATE_COLUMN).getDateCellValue();
        } catch (Exception ex) {
            return endDate;
        }
    }
}