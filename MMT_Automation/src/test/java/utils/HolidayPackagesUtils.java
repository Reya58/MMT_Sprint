package utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HolidayPackagesUtils {

    public static List<String[]> getTestData(String sheetName) {

        List<String[]> data = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("src/test/resources/data/HPInput.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            Iterator<Row> rows = sheet.iterator();

            // Skip header row
            rows.next();

            while (rows.hasNext()) {
                Row row = rows.next();

                String fromCity = row.getCell(0).getStringCellValue();
                String toCity = row.getCell(1).getStringCellValue();

                data.add(new String[]{fromCity, toCity});
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}