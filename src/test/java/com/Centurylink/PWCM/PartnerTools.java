package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class PartnerTools {
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	
	
	@BeforeClass
	   @Parameters("browser")
	   public void drivercall(String browserName) throws InterruptedException
	   {
		driver=BrowserClass.browserSelection(browserName);
	   }
	
	@Test(priority=0)
	public void AQCBPage()
	{
		extent=new ExtentReports(config.reportpath,false);
	    test=extent.startTest("AQCB page-US55844");
	    test.assignCategory("PartnerTools");
	    driver.get(config.toolsurl);
	    test.log(LogStatus.PASS, "AEM Homepage Lauched");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "AEM Login Success");
		
		//Link to the tool can be inserted into a test content page
		driver.findElement(By.linkText("AQCB")).isDisplayed();
		test.log(LogStatus.PASS, "AQCB Link is present");
		
		//User clicks link and is taken to the AQCB login page– open in a new tab
		driver.findElement(By.linkText("AQCB")).click();
		String parentw=driver.getWindowHandle();
		Set<String> s1=driver.getWindowHandles();
		  Iterator<String> i1=s1.iterator();
		  while(i1.hasNext()){
		  String childw=i1.next();
		  if(!parentw.equalsIgnoreCase(childw))
		  {
		  driver.switchTo().window(childw);
		  driver.findElement(By.name("AQCB")).isDisplayed();
		  test.log(LogStatus.PASS, "AQCB page is opened");
		  
		  //User sees the CUID Login fields
		  driver.findElement(By.id("user")).isDisplayed();
		  driver.findElement(By.name("password")).isDisplayed();
		  driver.findElement(By.id("homeButton")).isDisplayed();
		  test.log(LogStatus.PASS, "AQCB CUID Log in fields present");
		  
		  //No other functionality on our part.  Just get the user to the tool login page
		  driver.close();
				}
			}
		driver.switchTo().window(parentw);
		driver.close();
		test.log(LogStatus.PASS, "AQCB Test Passed");
		extent.endTest(test);
		extent.flush();
		
	}

}
