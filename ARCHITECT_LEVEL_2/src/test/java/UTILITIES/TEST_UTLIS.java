package UTILITIES;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;



import BASE_CLASS.BASE_CLASS;

public class TEST_UTLIS extends BASE_CLASS
{
	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot_Failed_screesnhot() throws IOException  
	{

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		
		
		File destFile=new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName);
		FileUtils.copyFile(scrFile,destFile);

	}
	@DataProvider(name="DataProvide_4")

	public Object[][] getData(Method m) 
	{
		String sheetName=m.getName();
		if (excel == null) {

			excel = new XLS_READER("C:\\Users\\Gulnar\\Desktop\\Automation Notes\\Framework_ARCHITECT_LEVEL\\DATA_DRIVEN_FRAMEWORK\\ARCHITECT_LEVEL_2\\src\\test\\resources\\EXCEL_SHEET\\testdata.xlsx");
		}

		//String sheetName = "SHEET1";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
         Object[][] data = new Object[rows - 1][1];
		
		Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2

			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum < cols; colNum++) {

				// data[0][0]
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
			}

		}

		return data;

	}
public static boolean Method_to_Skip(String testName, XLS_READER excel){
		
		String sheetName="SHEET 1";
		int rows = excel.getRowCount(sheetName);
		
		
		for(int rNum=2; rNum<=rows; rNum++){
			
			String testCase = excel.getCellData(sheetName, "TESTCASE_NEED_TO_RUN", rNum);
			
			if(testCase.equalsIgnoreCase(testName)){
				
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
			}
			
			
		}
		return false;
	}
	
	
}
