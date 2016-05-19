package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.Centurylink.PWCM.pwcmlibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Sprint208 {
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	String reportpath=".\\Reporting\\Advreportpath.html";
	String injectpath=".\\src\\main\\resources\\Injectfile.xls";
	String Attachpath="C:\\Users\\348027\\git\\Centurylink";
	
	    @Test(priority=0)		
	    public void UtilityLinks(){
		extent=new ExtentReports(reportpath,true);
		test=extent.startTest("Global Header - Utility Links");
		test.assignCategory("Sprint108","Regression Test");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get("http://172.26.130.130:4502/content/pwcm-first-page/home.html");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "pwcmint page is ready");
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCM login*/
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("admin");
		driver.findElement(By.id("submit-button")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");
		
		/* User can see the utility links*/
		driver.findElement(By.xpath("//div[@class='dropdown-toggle']")).isDisplayed();
		driver.findElement(By.linkText("SalesForce")).isDisplayed();
		driver.findElement(By.linkText("Help")).isDisplayed();
		driver.findElement(By.linkText("Feedback")).isDisplayed();
		driver.findElement(By.linkText("Logout")).isDisplayed();
		test.log(LogStatus.PASS, "Utility links are visible");
		
		/*User navigate to help and feedback page*/
		String parentw=driver.getWindowHandle();
		driver.findElement(By.xpath("//div[@class='dropdown-toggle']")).isSelected();
		driver.findElement(By.xpath("//div[@class='dropdown-toggle']")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Country is selectable");
		
		/*User navigate to help and feedback page*/
		driver.findElement(By.linkText("Help")).click();
		Set<String> helps=driver.getWindowHandles();
		Iterator<String> helpi=helps.iterator();
		while(helpi.hasNext()){
			String childw=helpi.next();
			if(!parentw.equalsIgnoreCase(childw))
			{
				driver.switchTo().window(childw);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
				System.out.println(driver.getTitle());
				driver.close();
			}
		}
		test.log(LogStatus.PASS, "User navigated to the Help page");
		driver.switchTo().window(parentw);
		driver.findElement(By.linkText("Feedback")).click();
		Set<String> feedbacks=driver.getWindowHandles();
		Iterator<String> feedbacki=feedbacks.iterator();
		while(feedbacki.hasNext()){
			String childw=feedbacki.next();
			if(!parentw.equalsIgnoreCase(childw))
			{
				driver.switchTo().window(childw);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
				driver.close();
			}
		}
		test.log(LogStatus.PASS, "User navigated to the Feedback page");
		
		/*view Logout in utility link section*/
		driver.switchTo().window(parentw);
		driver.findElement(By.linkText("Logout")).isDisplayed();
		test.log(LogStatus.PASS, "Logout link is present in Utility link section");
		
		/*User can click on the CTL logo and navigate back to the home page*/
		String ctllogo="//img[@src='https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTy6yBiky4iWbqq2qnPPYT7X8CQA_o_GGWbiZvtW5IapfIcbd_C']";
		String resourceimg="//img[@src='https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSnXtWT2sNGRvc4T7I_SgH88-xewD8j0L9q6lrwUvMA2HSFJkAmLw']";
		driver.findElement(By.linkText("Resources")).click();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.findElement(By.xpath(resourceimg)).isDisplayed();
		driver.findElement(By.xpath("//a[@class='ctlbiz-logo']")).click();
		driver.findElement(By.xpath(ctllogo)).isDisplayed();
		test.log(LogStatus.PASS, "CTL logo is clickable and user is navigated to the Home page");
		
		/*User can view a Search utility */
		driver.findElement(By.xpath("//input[@class='search_textBox']")).isSelected();
		driver.findElement(By.xpath("//input[@class='search_textBox']")).sendKeys("Search text working");
		test.log(LogStatus.PASS, "Search utility is present");
		
		test.log(LogStatus.PASS, "UtilityLinks Test Case is Passed");
		extent.endTest(test);
		extent.flush();
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws InterruptedException{
	if(ITestResult.FAILURE==result.getStatus())
	{
	config.captureScreenshot(driver,result.getName());
	String image=test.addScreenCapture(Attachpath+"\\Screenshots\\"+result.getName()+".jpg");
	test.log(LogStatus.FAIL, result.getName(), image);
	Thread.sleep(5000);
	}
	extent.endTest(test);
	extent.flush();
	}

    @AfterClass
    public void closebrowser()
    {
    	System.out.println("Testpassed");
     driver.get("C:\\Users\\348027\\git\\Centurylink\\Reporting\\Advreportpath.html");
    }

}
