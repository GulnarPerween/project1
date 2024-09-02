package TESTCASES;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BASE_CLASS.BASE_CLASS;
import UTILITIES.TEST_UTLIS;
import UTILITIES.XLS_READER;



public class TESTCASE1 extends BASE_CLASS
{
	
	@Test
	public void TESTCASE1() throws InterruptedException, IOException
	{
	
		
		log.info("Method 1 of Test Case 1 is Excueted");
		Thread.sleep(3000);
		click("customer_login_XPATH");
		Thread.sleep(2000);
		verifyEquals(driver.getTitle(),"Protractor practice website - Banking Aps");
		Thread.sleep(2000);
		click("Addcusotmer_XPATH");
		

		log.info("Method 1 of Test Case 1 is completed");
		
	}
	@Test(dataProviderClass=TEST_UTLIS.class,dataProvider="DataProvide_4")
		public void TESTCASE1(Hashtable<String,String> data) throws Throwable 
		{
		if(!data.get("Runmode").equals("Y")){
			
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}
		Reporter.log("TEST CASE 1 IS STARTED!!!"); 
		log.info("Method 2 of Test Case 1 is Excueted");
		 
				Thread.sleep(3000);	
				type_value("firstname_XPATH",data.get("firstname"));
				Thread.sleep(2000);
				type_value("lastname_XPATH",data.get("lastname"));
				
				Thread.sleep(2000);
				type_value("postalcode_XPATH",data.get("postcode"));
				
				Thread.sleep(2000);
				Assert.assertTrue(IsElementPresent
						(By.xpath(OR.getProperty("add_cusomter_button_XPATH"))),"Login not successful");
				Thread.sleep(2000);
				click("add_cusomter_button_XPATH");

				
				//Wait for the alert to be displayed and store it in a variable
				Alert alert = waits.until(ExpectedConditions.alertIsPresent());
				  Thread.sleep(2000);
				//Store the alert text in a variable
			   String text = alert.getText();
               System.out.println("Text is "+text);
				//Press the OK button
              Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
              //Thread.sleep(2000);
				alert.accept();
				log.info("Method 2 of Test Case 1 is completed");
					
				Reporter.log("TEST CASE 1 completed!!!"); 
			
		}


	
}
