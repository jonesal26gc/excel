package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;

public class ShippingBibleWorkbook {
    public static final int NATIONAL_DEPOT_COLUMN_START = 10;
    public static final int NATIONAL_DEPOT_COLUMN_END = 18;
    private FileInputStream fis;
    private XSSFWorkbook workbook;
    private XSSFSheet depotNameWorksheet;
    private XSSFSheet depotCodesWorksheet;
    private Date startDate;
    private Date endDate;
    private String filename;


    public ShippingBibleWorkbook(String filename) {
        this.filename = filename;
        try {
            this.fis = new FileInputStream(new File(filename));
            this.workbook = new XSSFWorkbook(fis);
            this.depotCodesWorksheet = workbook.getSheet("Depot Codes");
            this.depotNameWorksheet = workbook.getSheet("Depot Name");
            retrieveEffectiveDatesFromWorkbook();
            this.filename = filename;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void retrieveEffectiveDatesFromWorkbook() {
        XSSFRow currentRow = depotNameWorksheet.getRow(1);
        startDate = currentRow.getCell(33).getDateCellValue();
        endDate = currentRow.getCell(34).getDateCellValue();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public DepotCrossReference buildDepotCrossReference() {
        DepotCrossReference depotCrossReference = new DepotCrossReference();
        verifyLocationOfDepotNumberTableData();
        for (int rowNumber = 2; rowNumber < depotCodesWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotCodesWorksheet.getRow(rowNumber);
            if (currentRow == null) {
                break;
            }
            String depotName = retrieveDepotName(currentRow);
            String concatenatedDepotNumbers = retrieveConcatenatedDepotNumbers(currentRow);
            depotCrossReference.add(depotName, buildDepotNumberList(concatenatedDepotNumbers));
        }
        return depotCrossReference;
    }

    private void verifyLocationOfDepotNumberTableData() {
        XSSFRow headerRow = depotCodesWorksheet.getRow(1);
        if (!(headerRow.getCell(2).getStringCellValue().equals("Depot & Trunk Link")
                && headerRow.getCell(3).getStringCellValue().equals("Whse Number")
                && headerRow.getCell(4).getStringCellValue().equals("Stream"))) {
            throw new RuntimeException("ERROR - The Depot Codes header record has not been located.");
        }
    }

    private String retrieveDepotName(XSSFRow currentRow) {
        return currentRow.getCell(2).getStringCellValue();
    }

    private String retrieveConcatenatedDepotNumbers(XSSFRow currentRow) {
        String concatenatedDepotNumbers = Integer.toString((int) currentRow.getCell(3).getNumericCellValue());
        if (concatenatedDepotNumbers.length() < 3) {
            concatenatedDepotNumbers = String.format("%03d", (int) currentRow.getCell(3).getNumericCellValue());
        }
        if (!(concatenatedDepotNumbers.length() % 3 == 0)) {
            throw new RuntimeException("ERROR - The concatenated depot number list is incorrectly formatted.");
        }
        return concatenatedDepotNumbers;
    }

    private String[] buildDepotNumberList(String concatenatedDepotNumbers) {
        ArrayList<String> depotNumberList = new ArrayList<String>();
        while (concatenatedDepotNumbers.length() > 0) {
            depotNumberList.add(concatenatedDepotNumbers.substring(0, 3));
            concatenatedDepotNumbers = concatenatedDepotNumbers.substring(3);
        }
        return depotNumberList.toArray(new String[depotNumberList.size()]);
    }

    public DepotLocationsList buildDepotLocationsList() {
        DepotLocationsList depotLocationsList = new DepotLocationsList();
        verifyLocationOfDepotNumberTableData();
        for (int rowNumber = 2; rowNumber < depotCodesWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotCodesWorksheet.getRow(rowNumber);
            if (currentRow == null) {
                break;
            }
            String concatenatedDepotNumbers = retrieveConcatenatedDepotNumbers(currentRow);
            if (concatenatedDepotNumbers.length() == 3) {
                depotLocationsList.add(
                        new Location(concatenatedDepotNumbers,
                                "DEPOT",
                                retrieveDepotName(currentRow),
                                retrieveFormat(currentRow)));
            }
        }
        return depotLocationsList;
    }

    private String retrieveFormat(XSSFRow currentRow) {
        return currentRow.getCell(4).getStringCellValue();
    }

    public StoreLocationsList buildStoreLocationsList() {
        StoreLocationsList storeLocationsList = new StoreLocationsList();

        for (int rowNumber = 1; rowNumber < depotNameWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotNameWorksheet.getRow(rowNumber);
            if (currentRow == null | !isValidStoreNumber(currentRow)) {
                break;
            }
            storeLocationsList.add(
                    new Location(Integer.toString((int) currentRow.getCell(1).getNumericCellValue()),
                            "STORE",
                            retrieveStoreName(currentRow),
                            retrieveStoreFormat(currentRow),
                            retrieveStoreCounty(currentRow),
                            retrieveStorePostcode(currentRow),
                            retrieveStoreCountry(currentRow),
                            retrieveStoreReportingRegion(currentRow)));
        }
        return storeLocationsList;
    }

    private boolean isValidStoreNumber(XSSFRow currentRow) {
        try {
            return (int) currentRow.getCell(1).getNumericCellValue() >= 1000;
        } catch (Exception ex) {
            return false;
        }
    }

    private String retrieveStoreName(XSSFRow currentRow) {
        return currentRow.getCell(2).getStringCellValue();
    }

    private String retrieveStoreFormat(XSSFRow currentRow) {
        return currentRow.getCell(4).getStringCellValue();
    }

    private String retrieveStoreCounty(XSSFRow currentRow) {
        return currentRow.getCell(6).getStringCellValue();
    }

    private String retrieveStorePostcode(XSSFRow currentRow) {
        try {
            return currentRow.getCell(3).getStringCellValue();
        } catch (Exception e) {
            try {
                return Integer.toString((int) currentRow.getCell(3).getNumericCellValue());
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private String retrieveStoreCountry(XSSFRow currentRow) {
        return currentRow.getCell(7).getStringCellValue();
    }

    private String retrieveStoreReportingRegion(XSSFRow currentRow) {
        return currentRow.getCell(5).getStringCellValue();
    }

    public DepotToStoreRouteList buildDepotToStoreRouteList(DepotCrossReference depotCrossReference, String targetDepot) {
        int targetDepotColumn = findTargetDepotColumn(targetDepot);
        if (targetDepotColumn == 0) {
            return null;
        }
        DepotToStoreRouteList depotToStoreRouteList = new DepotToStoreRouteList(depotCrossReference.lookup(targetDepot)[0]);
        for (int rowNumber = 1; rowNumber < depotNameWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotNameWorksheet.getRow(rowNumber);
            if (currentRow == null | !isValidStoreNumber(currentRow)) {
                break;
            }
            DepotToStoreRoute depotToStoreRoute = new DepotToStoreRoute(
                    depotToStoreRouteList.getDepot(),
                    Integer.toString((int) currentRow.getCell(1).getNumericCellValue()),
                    depotCrossReference.lookup(currentRow.getCell(targetDepotColumn).getStringCellValue()),
                    startDate,
                    endDate);
            depotToStoreRouteList.add(depotToStoreRoute);
        }
        return depotToStoreRouteList;
    }

    private int findTargetDepotColumn(String targetDepot) {
        XSSFRow headingsRow = depotNameWorksheet.getRow(0);
        for (int headingsColumn = NATIONAL_DEPOT_COLUMN_START; headingsColumn <= NATIONAL_DEPOT_COLUMN_END; headingsColumn++) {
            if (headingsRow.getCell(headingsColumn).getStringCellValue().equals(targetDepot)) {
                return headingsColumn;
            }
        }
        return 0;
    }
}