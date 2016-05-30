package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
	String NBIE="NBIE Q Partner Login";
	
	
	@BeforeMethod
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
		String parentw=driver.getWindowHandle();
		driver.findElement(By.linkText("AQCB")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Set<String> aqcbs=driver.getWindowHandles();
		  Iterator<String> aqcbi=aqcbs.iterator();
		  while(aqcbi.hasNext()){
		  String childw=aqcbi.next();
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
	
	@Test(priority=1)
	public void CNDCpage() throws InterruptedException
	{
		extent=new ExtentReports(config.reportpath,false);
		test=extent.startTest("CNDC (NBIE Portal) - US55845");
		test.assignCategory("PartnerTools");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(config.toolsurl);
		test.log(LogStatus.PASS, " AEM Homepage Lauched");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "AEM Login Success");
		
		//Link to the tool can be inserted into a test content page
		driver.findElement(By.linkText("CNDC")).isDisplayed();
		test.log(LogStatus.PASS, "CNDC link is present");
		
		//User clicks link and is taken to the CNDC login page – open in new tab
		  String parentw=driver.getWindowHandle();
		  driver.findElement(By.linkText("CNDC")).click();
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  Set<String> channels=driver.getWindowHandles();
		  Iterator<String> channeli=channels.iterator();
		  while(channeli.hasNext()){
		  String childw=channeli.next();
		  if(!parentw.equalsIgnoreCase(childw))
		  {
		  driver.switchTo().window(childw);
		  // SSO workaround - Log into the Site for the tool page to display
		  config.prodlogin(driver);
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		  driver.findElement(By.id("welcomeUser")).isDisplayed();
		  test.log(LogStatus.PASS, "Channel Alliance Log in Successful");
		  driver.close();			 
		   }
	       }
		  driver.switchTo().window(parentw);
		  driver.findElement(By.linkText("CNDC")).click();
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		    Set<String> cndcs=driver.getWindowHandles();
		  Iterator<String> cndci=cndcs.iterator();
			  while(cndci.hasNext()){
			  String childw=cndci.next();
			  if(!parentw.equalsIgnoreCase(childw))
			  {
			  driver.switchTo().window(childw);
			  Assert.assertEquals(NBIE, driver.findElement(By.className("title")).getText());
			  test.log(LogStatus.PASS, "CNDC Page is opened");
			  
		 //User sees the CUID Login fields
			  driver.findElement(By.name("ldapUser")).isDisplayed();
			  driver.findElement(By.name("password")).isDisplayed();
			  driver.findElement(By.xpath("//input[@value='    Login    ']")).isDisplayed();
			  test.log(LogStatus.PASS, "CNDC CUID Log in fields present");
			  
		//No other functionality on our part.  Just get the user to the tool login page	  	 				  
			   driver.close();
			   
			   }
		       }
		driver.switchTo().window(parentw);
		driver.close();
		test.log(LogStatus.PASS, "CNDC Test Passed");
		extent.endTest(test);
		extent.flush();
		}
	
	@Test(priority=2)
	public void VQT() throws InterruptedException
	{
		extent=new ExtentReports(config.reportpath,false);
		test=extent.startTest("VQT - VoIP Qualification Tool - US55862");
		test.assignCategory("PartnerTools");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(config.toolsurl);
		test.log(LogStatus.PASS, " AEM Homepage Lauched");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "AEM Login Success");
		
		//Link to the tool can be inserted into a test content page
		  driver.findElement(By.linkText("VQT-VoIP Qualification Tool")).isDisplayed();
		  test.log(LogStatus.PASS, "VQT Link is present");
		  
		  //User clicks the link and is taken directly to the VQT page without additional login (SSO)
		  String parentw=driver.getWindowHandle();
		  driver.findElement(By.linkText("VQT-VoIP Qualification Tool")).click();
		  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			
			Set<String> channels=driver.getWindowHandles();
			  Iterator<String> channeli=channels.iterator();
			  while(channeli.hasNext()){
			  String childw=channeli.next();
			  if(!parentw.equalsIgnoreCase(childw))
			  {
			  driver.switchTo().window(childw);
			  config.prodlogin(driver);
			  driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			  driver.findElement(By.id("welcomeUser")).isDisplayed();
			  test.log(LogStatus.PASS, "Channel Alliance Log in Successful");
			  driver.close();			 
			  
			   }
		       }
			  driver.switchTo().window(parentw);
			  driver.findElement(By.linkText("VQT-VoIP Qualification Tool")).click();
			  driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);	
			  
			  Set<String> vqts=driver.getWindowHandles();
			  Iterator<String> vqti=vqts.iterator();
				  while(vqti.hasNext()){
				  String childw=vqti.next();
				  if(!parentw.equalsIgnoreCase(childw))
				  {
				  driver.switchTo().window(childw);
				  test.log(LogStatus.PASS, "VQT page is opened");
				  
		  //User is presented with the tool page with  fields to be completed
				  driver.findElement(By.partialLinkText("Service Availability and TN Portability Check")).isDisplayed();
				  driver.findElement(By.partialLinkText("Hosted VoIP MCC Check")).isDisplayed();
				  driver.findElement(By.partialLinkText("Legacy Managed VoIP MCC Check")).isDisplayed();
				  driver.findElement(By.partialLinkText("Integrated Access MCC Check")).isDisplayed();
				  test.log(LogStatus.PASS, "VQT Tool page with the associated links are present");
				  
		  //No other functionality on our part.  Just get the user to the tool.
				  driver.close();
			   
			   }
		       }
		driver.switchTo().window(parentw);
		driver.close();
		test.log(LogStatus.PASS, "CNDC Test Passed");
		extent.endTest(test);
		extent.flush();
		}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws InterruptedException{
	if(ITestResult.FAILURE==result.getStatus())
	{
	config.captureScreenshot(driver,result.getName());
	String image=test.addScreenCapture(config.Attachpath+"\\Screenshots\\"+result.getName()+".jpg");
	test.log(LogStatus.FAIL, result.getName(), image);
	Thread.sleep(5000);
	}
	extent.endTest(test);
	extent.flush();
	}

}
