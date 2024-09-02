package ROUGH_WORK;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TEST_PROPERTIES {

	public static void main(String[] args) throws IOException
	{
	Properties config=new Properties();
	Properties OR=new Properties();
	
	FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\PROPERTIES\\CONFIG.properties");
	
	config.load(fis);
	System.out.println(config.getProperty("TESTSITEURL"));
	
	
     fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\PROPERTIES\\OR.properties");
	
     OR.load(fis);
	System.out.println(OR.getProperty("openaccount_XPATH")); 
	
	}
}


