package ManiplateWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;

public class ShippingBibleWorkbook {
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
}
