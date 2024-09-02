package TESTCASES;

import static org.testng.Assert.assertTrue;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.aventstack.extentreports.util.Assert;

import BASE_CLASS.BASE_CLASS;
import UTILITIES.TEST_UTLIS;

public class TESTCASE2 extends BASE_CLASS
{
@Test(dataProviderClass=TEST_UTLIS.class,dataProvider="DataProvide_4")
public void TESTCASE2(Hashtable<String,String> data ) throws InterruptedException {



	if(!data.get("Runmode").equals("Y")){
		
		throw new SkipException("Skipping the test case as the Run mode for data set is NO");
	}
		log.info(" Test Case 2 is STARTED");
		Thread.sleep(3000);
		
	    click("openaccount_XPATH");
	    Thread.sleep(2000);
		select("customer_XPATH", data.get("CUSOMTERNAME "));
		
		Thread.sleep(2000);
		select("currency_CSS", data.get("CURRENCY"));
		Thread.sleep(2000);
		click("process_CSS");
		Thread.sleep(2000);
		Alert alert = waits.until(ExpectedConditions.alertIsPresent());
		Thread.sleep(2000);
		alert.accept();
		log.info(" Test Case 2 is Excueted");

	}
@Test
public void TESTCASE2() throws InterruptedException
{
	 click("openaccount_XPATH");
	    Thread.sleep(2000);
	 select("customer_XPATH", "Hermoine Granger");
	 assertTrue(false);
	 
	 
}



	
}
