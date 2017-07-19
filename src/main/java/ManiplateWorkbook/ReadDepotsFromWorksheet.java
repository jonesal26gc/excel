package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ReadDepotsFromWorksheet {
    static XSSFRow row;

    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(
                new File("Transport Bible.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet spreadsheet = workbook.getSheet("Depot Codes");

        // Initialize oldLocations array.
        ArrayList<OldLocation> oldLocations = new ArrayList<OldLocation>();
        HashMap<String, ArrayList<String>> depotCrossReference = new HashMap<String, ArrayList<String>>();

        // loop through all the rows until the end.
        for (int rowNumber = 2; rowNumber < spreadsheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = spreadsheet.getRow(rowNumber);

            // If there are no further rows, then terminate the processing.
            if (currentRow == (null)) {
                break;
            }

            if ((int) currentRow.getCell(3).getNumericCellValue() <= 999) {
                // Add oldLocation to the oldLocations array.
                ArrayList<LocationType> locationTypes = new ArrayList<LocationType>();
                locationTypes.add(LocationType.DEPOT);
                String depotNumber = Integer.toString((int) currentRow.getCell(3).getNumericCellValue());
                OldLocation oldLocation = new OldLocation(depotNumber
                        , currentRow.getCell(2).getStringCellValue(),
                        locationTypes);
                oldLocation.setFormat(currentRow.getCell(4).getStringCellValue());
                oldLocations.add(oldLocation);
            }

            if ((int) currentRow.getCell(3).getNumericCellValue() > 0) {
                // Create the cross-reference.
                String depotNumberWhole = Integer.toString((int) currentRow.getCell(3).getNumericCellValue());
                if (depotNumberWhole.length() < 3) {
                    depotNumberWhole = String.format("%03d", (int) currentRow.getCell(3).getNumericCellValue());
                }
                System.out.println(depotNumberWhole);
                ArrayList<String> depotNumbers = new ArrayList<String>();
                while (depotNumberWhole.length() > 0) {
                    depotNumbers.add(depotNumberWhole.substring(0, 3));
                    depotNumberWhole = depotNumberWhole.substring(3);
                }
                depotCrossReference.put(currentRow.getCell(2).getStringCellValue(), depotNumbers);
            }
        }

        fis.close();

        // List the oldLocations.
        for (OldLocation oldLocation : oldLocations) {
            System.out.println(oldLocation.toString());
        }

        // List the cross-reference.
        for (Map.Entry<String, ArrayList<String>> h : depotCrossReference.entrySet()) {
            System.out.println(h.getKey() + " | " + h.getValue().toString());
        }

    }

}