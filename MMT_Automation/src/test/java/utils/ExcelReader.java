package utils;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static Workbook workbook;

    static {
        try {
            FileInputStream fis =
                    new FileInputStream("src/test/resources/Villas_TestData.xlsx");

            workbook = new XSSFWorkbook(fis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public static String getCellData(String sheetName, String tcId, String columnName) {

    Sheet sheet = workbook.getSheet(sheetName);

    Row headerRow = sheet.getRow(0);

    int colIndex = -1;

    for (Cell cell : headerRow) {
        if (cell.toString().trim().equalsIgnoreCase(columnName.trim())) {
            colIndex = cell.getColumnIndex();
            break;
        }
    }

    for (Row row : sheet) {

        if (row.getCell(0) == null) continue;

        String rowTcId = row.getCell(0).toString().trim();

        if (rowTcId.equalsIgnoreCase(tcId.trim())) {

            Cell cell = row.getCell(colIndex);

            if (cell == null) return "";

            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue().trim();
                case NUMERIC:
                    return String.valueOf((int) cell.getNumericCellValue());
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return cell.toString().trim();
            }
        }
    }

    System.out.println("❌ TC NOT FOUND IN EXCEL: " + tcId);
    return "";
}
}