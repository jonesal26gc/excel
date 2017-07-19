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

        // Initialize oldLocations array.
        ArrayList<OldLocation> oldLocations = new ArrayList<OldLocation>();

        // loop through all the rows until the end.
        for (int rowNumber = 1; rowNumber < spreadsheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = spreadsheet.getRow(rowNumber);

            // If there are no further rows, then terminate the processing.
            if (currentRow == (null)) {
                break;
            }

            if ((int) currentRow.getCell(1).getNumericCellValue() >= 1000) {
                // Add oldLocation to the oldLocations array.
                ArrayList<LocationType> locationTypes = new ArrayList<LocationType>();
                locationTypes.add(LocationType.STORE);
                OldLocation oldLocation = new OldLocation(Integer.toString((int) currentRow.getCell(1).getNumericCellValue())
                        , currentRow.getCell(2).getStringCellValue(),
                        locationTypes);
                retrievePostcodeFromRow(currentRow, oldLocation);
                oldLocation.setFormat(currentRow.getCell(4).getStringCellValue());
                oldLocation.setReportingRegion(currentRow.getCell(5).getStringCellValue());
                oldLocation.setCounty(currentRow.getCell(6).getStringCellValue());
                oldLocation.setCountry(currentRow.getCell(7).getStringCellValue());
                oldLocations.add(oldLocation);
                System.out.println(oldLocation);
            }
        }

        fis.close();

        // List the oldLocations.
        for (OldLocation oldLocation : oldLocations) {
            System.out.println(oldLocation.toString());
        }
    }

    private static void retrievePostcodeFromRow(XSSFRow currentRow, OldLocation oldLocation) {
        try {
            oldLocation.setPostcode(currentRow.getCell(3).getStringCellValue());
        } catch (Exception ex) {
        }

    }
}

