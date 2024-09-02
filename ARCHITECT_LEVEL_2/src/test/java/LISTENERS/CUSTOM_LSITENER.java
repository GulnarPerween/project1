package LISTENERS;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import BASE_CLASS.BASE_CLASS;
import UTILITIES.EXTENT_CLASS;
import UTILITIES.TEST_UTLIS;

public class CUSTOM_LSITENER extends BASE_CLASS implements ITestListener,ISuiteListener
{
	static Date d = new Date();
	static String fileName1 = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	private static ExtentReports extent = 
			EXTENT_CLASS.createInstance(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+fileName1);
	public static ThreadLocal<ExtentTest> testReport_1 = new ThreadLocal<ExtentTest>();

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
		
	}

	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

	public void onTestStart(ITestResult result) 
	{
		//Inorder to start Extent Report to work ,below code is given for extent reports
		ExtentTest test = extent.createTest(result.getTestClass()
				.getName()+"     @TestCase : "+result.getMethod().getMethodName());
		testReport_1.set(test);
        
	}

	public void onTestSuccess(ITestResult result) {
		
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {

			TEST_UTLIS.captureScreenshot_Failed_screesnhot();

		} catch (IOException e) {


		e.printStackTrace();

		}
		Reporter.log("<a target=\"_blank\" href="+ TEST_UTLIS.screenshotName +">Screenshot</a>");

		Reporter.log("<br>");
		Reporter.log("<br>");

		Reporter.log("<a target=\"_blank\" href="+ TEST_UTLIS.screenshotName +"><img src="+ TEST_UTLIS.screenshotName +" height=200 width=200></img></a>" );

	//below Exend Report for Success
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport_1.get().pass(m);
          // EXTEND REPORT FOR CAPTURING THE SCREENSHOT FOR PASSED TESTCASES
        
		
	}
	
	

	public void onTestFailure(ITestResult result) {
		// Setting up screenshot set up.

		System.setProperty("org.uncommons.reportng.escape-output", "false");

		try {

			TEST_UTLIS.captureScreenshot_Failed_screesnhot();

		} catch (IOException e) {


		e.printStackTrace();

		}
		Reporter.log("<a target=\"_blank\" href="+ TEST_UTLIS.screenshotName +">Screenshot</a>");

		Reporter.log("<br>");
		Reporter.log("<br>");

		Reporter.log("<a target=\"_blank\" href="+ TEST_UTLIS.screenshotName +"><img src="+ TEST_UTLIS.screenshotName +" height=200 width=200></img></a>" );

        // Below is code for Extend Reports
		

		String excepionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		testReport_1.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
		try {

			TEST_UTLIS.captureScreenshot_Failed_screesnhot();
			testReport_1.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(TEST_UTLIS.screenshotName)
							.build());
		} catch (IOException e) {

		}
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport_1.get().log(Status.FAIL, m);

	}

		
		


	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		//Extent Report code are as below
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"Test Case:- "+ methodName+ " Skipped"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport_1.get().skip(m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		//Need to flush the extend reports
		
		if (extent != null) {

			extent.flush();
		}
	}

}
