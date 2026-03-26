package utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

//Hotels_ExcelUtils
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Hotel_ExcelUtil {
	
	public static Map<String, String> getRowData(String tcId) throws Exception {

	    Map<String, String> map = new HashMap<>();

	    FileInputStream fis = new FileInputStream("C:\\Users\\KIIT\\Desktop\\MakeMyTrip_Capg\\MMT_Automation\\src\\test\\resources\\data\\Hotels_TestData.xlsx");
	    XSSFWorkbook wb = new XSSFWorkbook(fis);
	    XSSFSheet sheet = wb.getSheet("Sheet1");

	    XSSFRow header = sheet.getRow(0);

	    for(int i=1; i<=sheet.getLastRowNum(); i++){
	        XSSFRow row = sheet.getRow(i);

	        if(row.getCell(0).toString().equals(tcId)){

	            for(int j=0; j<header.getLastCellNum(); j++){
	                map.put(header.getCell(j).toString(), row.getCell(j).toString());
	            }
	        }
	    }

	    wb.close();
	    return map;
	}

}
