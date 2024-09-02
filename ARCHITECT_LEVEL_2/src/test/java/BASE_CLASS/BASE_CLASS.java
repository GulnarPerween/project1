package BASE_CLASS;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import LISTENERS.CUSTOM_LSITENER;
import UTILITIES.TEST_UTLIS;
import UTILITIES.XLS_READER;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BASE_CLASS {
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static WebDriverWait waits ;
	
	public static Logger log = Logger.getLogger(BASE_CLASS.class.getName());
	
	public static XLS_READER excel = new XLS_READER(
			System.getProperty("user.dir") + "\\src\\test\\resources\\EXCEL_SHEET\\testdata.xlsx");
	
	@BeforeSuite
	
	public void setup() throws IOException, InterruptedException 
	{
		 Date d = new Date();
         System.setProperty("current.date", d.toString().replace(":", "_").replace(" ", "_"));
         PropertyConfigurator.configure("C:\\Users\\Gulnar\\Desktop\\Automation Notes\\Framework_ARCHITECT_LEVEL\\DATA_DRIVEN_FRAMEWORK\\ARCHITECT_LEVEL_2\\src\\test\\resources\\LOGS\\log4j.properties");	
         
		if (driver == null) 
		{
			
			
	         
	         FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\PROPERTIES\\CONFIG.properties");

			config.load(fis);

			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\PROPERTIES\\OR.properties");
			OR.load(fis);
			
	        

			if(config.getProperty("BROWSER").equals("CHROME"))
			{
				
				
				System.setProperty("Webdriver.chrome.driver",config.getProperty("chromepath"));
				 ChromeOptions option = new ChromeOptions();
		         option.addArguments("--remote-allow-origins=*");
		            WebDriverManager.chromedriver().setup();
		            driver = new ChromeDriver(option);
		            log.info("chrome driver is Excueted");
				
			}
			else if(config.getProperty("BROWSER").equals("firefox"))
			{
				System.setProperty("Webdriver.gecko.driver",config.getProperty("geckodriverpath"));
				FirefoxOptions option = new FirefoxOptions();
		         //option.addArguments("--remote-allow-origins=*");
		         WebDriverManager.firefoxdriver().setup();
		            driver = new FirefoxDriver(option);
		            log.info("firefox driver is Excueted");
			}
			
			else if(config.getProperty("BROWSER").equals("ie"))
			{
				
				System.setProperty("Webdriver.edge.driver",config.getProperty("ie_driverpath"));
				EdgeOptions  option = new EdgeOptions ();
		        option.addArguments("--remote-allow-origins=*");
		         WebDriverManager.edgedriver().setup();
			
				driver =new EdgeDriver(option);
				 log.info("edge driver is Excueted");
				
			}
			
			driver.get(config.getProperty("TESTSITEURL"));
			Thread.sleep(3000);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			   driver.manage().timeouts().implicitlyWait(Duration.ofHours(2));
			   waits = new WebDriverWait(driver, Duration.ofHours(2));
			   log.info("TESTSITEURL is Excueted");
		}
		

	}
	public boolean IsElementPresent(By by)
	{
		try
		{
			driver.findElement(by);
			return true;
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
	}
	@AfterSuite
	public void Tearup() {

		if(driver!=null)
		{
			driver.quit();
		}
		log.info("close Browser is Excueted");
	}

	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_className")) {
			driver.findElement(By.className(OR.getProperty(locator))).click();
		}
		CUSTOM_LSITENER.testReport_1.get().log(Status.INFO, "Clicking on : " + locator);
	}

	public void type_value(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		CUSTOM_LSITENER.testReport_1.get().log(Status.INFO, "Typing in : " + locator + " entered value as " + value);

	}
	
	static WebElement dropdown;

	public void select(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		CUSTOM_LSITENER.testReport_1.get().log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

	}

	
public static void verifyEquals(String expected, String actual) throws IOException {

	try {

		Assert.assertEquals(actual, expected);

	} catch (Throwable t) {

		TEST_UTLIS.captureScreenshot_Failed_screesnhot();
		// ReportNG
		Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
		Reporter.log("<a target=\"_blank\" href=" + TEST_UTLIS.screenshotName + "><img src=" + TEST_UTLIS.screenshotName
				+ " height=200 width=200></img></a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		
		
		// Extent Reports for generating Reports
		
		CUSTOM_LSITENER.testReport_1.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
        CUSTOM_LSITENER.testReport_1.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
				MediaEntityBuilder.createScreenCaptureFromPath(TEST_UTLIS.screenshotName)
				.build());

	}
}
}

