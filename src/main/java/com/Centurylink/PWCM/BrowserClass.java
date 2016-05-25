package com.Centurylink.PWCM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserClass {

	static WebDriver driver;
	
    static String chromepath=".\\lib\\chromedriver.exe";
	static String iepath=".\\lib\\IEDriverServer.exe";	
	
	public static WebDriver browserSelection(String browserName) throws InterruptedException
	{
	 if(browserName.equalsIgnoreCase("firefox"))
	  {
	   driver=new FirefoxDriver();
	  }
	 else if(browserName.equalsIgnoreCase("chrome"))
	  {
	   System.setProperty("webdriver.chrome.driver",chromepath);
	   driver=new ChromeDriver();
	  }
	 else if(browserName.equalsIgnoreCase("ie"))
	  {
	   System.setProperty("webdriver.ie.driver",iepath);
	   DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
	   capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	   capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
	   capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	   driver=new InternetExplorerDriver();
	  }
	 return driver;
	 }

}

