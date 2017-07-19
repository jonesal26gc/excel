package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ShippingBibleWorkbook {
    private FileInputStream fis;
    private XSSFWorkbook workbook;
    private XSSFSheet depotNamesWorksheet;
    private XSSFSheet depotCodesWorksheet;
    private Date bibleStartDate;
    private Date bibleEndDate;


    public ShippingBibleWorkbook(String fileName) {
        try {
            this.fis = new FileInputStream(new File(fileName));
            this.workbook = new XSSFWorkbook(fis);
            this.depotCodesWorksheet = workbook.getSheet("Depot Codes");
            this.depotNamesWorksheet = workbook.getSheet("Depot Name");
            retrieveDateFromWorkbook();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void retrieveDateFromWorkbook() {
        XSSFRow currentRow = depotNamesWorksheet.getRow(1);
        bibleStartDate = currentRow.getCell(33).getDateCellValue();
        bibleEndDate = currentRow.getCell(34).getDateCellValue();
    }

    public Date getBibleStartDate() {
        return bibleStartDate;
    }

    public Date getBibleEndDate() {
        return bibleEndDate;
    }

    public DepotCrossReference retrieveDepotCrossReference() {
        DepotCrossReference depotCrossReference = new DepotCrossReference();

        // loop through all the rows until the end.
        for (int rowNumber = 2; rowNumber < depotCodesWorksheet.getLastRowNum(); rowNumber++) {
            XSSFRow currentRow = depotCodesWorksheet.getRow(rowNumber);

            // If there are no further rows, then terminate the processing.
            if (currentRow == (null)) {
                break;
            }

            // Create the cross-reference.
            String concatenatedDepotNumbers = Integer.toString((int) currentRow.getCell(3).getNumericCellValue());
            if (concatenatedDepotNumbers.length() < 3) {
                concatenatedDepotNumbers = String.format("%03d", (int) currentRow.getCell(3).getNumericCellValue());
            }
            ArrayList<String> depotNumberList = new ArrayList<String>();
            while (concatenatedDepotNumbers.length() > 0) {
                depotNumberList.add(concatenatedDepotNumbers.substring(0, 3));
                concatenatedDepotNumbers = concatenatedDepotNumbers.substring(3);
            }
            depotCrossReference.addEntry(currentRow.getCell(2).getStringCellValue(), depotNumberList);
        }

        return depotCrossReference;
    }
}
