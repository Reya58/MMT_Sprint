package utils.homestay_utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    // ============================================
    // Constructor
    // ============================================
    public ExcelUtils(String filePath, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============================================
    // Get Row Count
    // ============================================
    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    // ============================================
    // Get Column Count
    // ============================================
    public int getColCount() {
        Row row = sheet.getRow(0);
        return row.getLastCellNum();
    }

    // ============================================
    // Get Cell Data (String)
    // ============================================
    public String getCellData(int rowNum, int colNum) {
        try {
            Cell cell = sheet.getRow(rowNum).getCell(colNum);

            if (cell == null) return "";

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();

                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        return String.valueOf((long) cell.getNumericCellValue());
                    }

                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());

                case FORMULA:
                    return cell.getCellFormula();

                default:
                    return "";
            }

        } catch (Exception e) {
            return "";
        }
    }

    // ============================================
    // Get All Data (2D Object Array) → DataProvider
    // ============================================
    public Object[][] getSheetData() {

        int rows = getRowCount();
        int cols = getColCount();

        Object[][] data = new Object[rows][cols];

        for (int i = 1; i <= rows; i++) {  // skip header row
            for (int j = 0; j < cols; j++) {
                data[i - 1][j] = getCellData(i, j);
            }
        }

        return data;
    }

    // ============================================
    // Close Workbook
    // ============================================
    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}