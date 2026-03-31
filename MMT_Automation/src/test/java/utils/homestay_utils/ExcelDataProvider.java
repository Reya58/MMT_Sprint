package utils.homestay_utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] getData() {

        ExcelUtils excel = new ExcelUtils(
            "src/test/resources/data/testdata.xlsx",
            "Sheet1"
        );

        Object[][] data = excel.getSheetData();
        excel.close();

        return data;
    }
}