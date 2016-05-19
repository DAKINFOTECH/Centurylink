package com.Centurylink.PWCM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserClass {

	static WebDriver driver;
	public static WebDriver verifypageTitle(String browserName) throws InterruptedException
	{
	 if(browserName.equalsIgnoreCase("firefox"))
	  {
	   driver=new FirefoxDriver();
	  }
	 else if(browserName.equalsIgnoreCase("chrome"))
	  {
	   	   driver=new ChromeDriver();
	  }
	 else if(browserName.equalsIgnoreCase("ie"))
	  {
	   	   driver=new InternetExplorerDriver();
	  }
	 return driver;
	 }
    
}

