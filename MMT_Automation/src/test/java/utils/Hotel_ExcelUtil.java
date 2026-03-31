package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Hotel_ExcelUtil {

    private static final String FILE_PATH =
        System.getProperty("user.dir") +
        "/src/test/resources/data/Hotels_TestData.xlsx";

    public static Map<String, String> getRowData(String tcId) throws Exception {

        Map<String, String> map = new HashMap<>();
        DataFormatter fmt = new DataFormatter();

        // Bug 3 fixed: try-with-resources closes both fis and wb automatically
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = wb.getSheet("Sheet1");
            XSSFRow header  = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                XSSFRow row = sheet.getRow(i);

                // Bug 4 fixed: skip null (blank) rows
                if (row == null) continue;

                // Bug 1 fixed: DataFormatter handles null cells and all cell types
                String cellId = fmt.formatCellValue(row.getCell(0)).trim();

                if (cellId.equals(tcId)) {
                    for (int j = 0; j < header.getLastCellNum(); j++) {
                        String key   = fmt.formatCellValue(header.getCell(j)).trim();
                        String value = fmt.formatCellValue(row.getCell(j)).trim();
                        map.put(key, value);
                    }
                    break;  // Bug 2 fixed: stop scanning after first match
                }
            }
        }

        return map;
    }
}