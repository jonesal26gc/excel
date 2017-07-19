package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ReadStoresFromWorksheet {
    static XSSFRow row;

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(
                new File("Transport Bible.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheet("Depot Name");

        // Initialize locations array.
        ArrayList<Location> locations = new ArrayList<Location>();

        // loop through all the rows until the end.
        for (int rowNumber = 1; rowNumber < spreadsheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = spreadsheet.getRow(rowNumber);

            // If there are no further rows, then terminate the processing.
            if (currentRow == (null)) {
                break;
            }

            if ((int) currentRow.getCell(1).getNumericCellValue() >= 1000) {
                // Add location to the locations array.
                ArrayList<LocationType> locationTypes = new ArrayList<LocationType>();
                locationTypes.add(LocationType.STORE);
                Location location = new Location(Integer.toString((int) currentRow.getCell(1).getNumericCellValue())
                        , currentRow.getCell(2).getStringCellValue(),
                        locationTypes);
                retrievePostcodeFromRow(currentRow, location);
                location.setFormat(currentRow.getCell(4).getStringCellValue());
                location.setReportingRegion(currentRow.getCell(5).getStringCellValue());
                location.setCounty(currentRow.getCell(6).getStringCellValue());
                location.setCountry(currentRow.getCell(7).getStringCellValue());
                locations.add(location);
                System.out.println(location);
            }
        }

        fis.close();

        // List the locations.
        for (Location location : locations) {
            System.out.println(location.toString());
        }
    }

    private static void retrievePostcodeFromRow(XSSFRow currentRow, Location location) {
        try {
            location.setPostcode(currentRow.getCell(3).getStringCellValue());
        } catch (Exception ex) {
        }

    }
}

