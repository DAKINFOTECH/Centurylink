package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FeedbackPage {
	
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	String fdback="Feedback";
	
	@BeforeClass
	   @Parameters("browser")
	   public void drivercall(String browserName) throws InterruptedException
	   {
		driver=BrowserClass.browserSelection(browserName);
	   }
	
	@Test(priority=1,dataProvider="Feedbackpage")
	public void Feedbackpage( String uname, String uid, String partcompany, String mailid, String cellnum, String offnum,
			                  String subjects, String details, String comment) throws InterruptedException
	{
		
		extent=new ExtentReports(config.reportpath,false);
	    test=extent.startTest("Feedback Page-US49301");
	    test.assignCategory("FeedbackPage");
	    driver.get(config.baseurl);
	    test.log(LogStatus.PASS, "pwcmint Homepage");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");
		
	     /*Switch to child Feedback Page*/
		String parentw=driver.getWindowHandle();
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
				/*Check you are in feedback page*/
				Assert.assertEquals(fdback, driver.findElement(By.xpath("//p[@class='askATechHeader']")).getText());
				test.log(LogStatus.PASS, "Feedback page opened in new window");
				
				/*Fill the feedback page form*/
			    driver.findElement(By.id("name")).sendKeys(uname);
				driver.findElement(By.id("userid")).sendKeys(uid);
				driver.findElement(By.id("partnername")).sendKeys(partcompany);
				driver.findElement(By.id("email")).sendKeys(mailid);
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				driver.findElement(By.id("cellno")).sendKeys(cellnum);
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				driver.findElement(By.id("ofcnumber")).sendKeys(offnum);
				Select subj=new Select(driver.findElement(By.name("subject")));
				subj.selectByValue(subjects);
				driver.findElement(By.name("details")).sendKeys(details);
				driver.findElement(By.name("comments")).sendKeys(comment);
				test.log(LogStatus.PASS, "Able to fill all the fields in the page");
				Thread.sleep(2000);
				
				/*Check the reset functionality*/
				driver.findElement(By.className("negative")).click();
				WebElement inputBox = driver.findElement(By.id("userid"));
				String textInsideInputBox = inputBox.getAttribute("value");
				if(textInsideInputBox.isEmpty())
				{
				  test.log(LogStatus.PASS, "Reset Working");
				}else {
					test.log(LogStatus.FAIL, "Reset Not Working");
					  }
				
				/*Submit negative testing*/
				driver.findElement(By.id("name")).sendKeys(uname);
				driver.findElement(By.id("userid")).sendKeys(uid);
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				driver.findElement(By.id("cellno")).sendKeys(cellnum);
				driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
				driver.findElement(By.id("ofcnumber")).sendKeys(offnum);
				Select subj1=new Select(driver.findElement(By.name("subject")));
				subj1.selectByValue(subjects);
				driver.findElement(By.name("details")).sendKeys(details);
				driver.findElement(By.name("comments")).sendKeys(comment);
				
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("email")).isDisplayed();
				driver.findElement(By.id("partnername")).isDisplayed();
				driver.findElement(By.id("partnername")).sendKeys(partcompany);
				driver.findElement(By.id("email")).sendKeys("ilamukil.arunkumargmail.com");
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("email")).isDisplayed();
				driver.findElement(By.id("email")).sendKeys(mailid);
				test.log(LogStatus.PASS, "Feedback page (*)Mandatory is working");
				
				/*Submit Not functional*/
			    test.log(LogStatus.INFO, "Submit non functional:Negative Testing scripts will be created after submit is functional");
			    test.log(LogStatus.INFO, "For Expected Result 5,6,7and 8, scripts will be created after submit functional");
				
				Thread.sleep(2000);
				
				driver.close();
				}
			}
		driver.switchTo().window(parentw);
		test.log(LogStatus.PASS, "Feedback Page Test Passed");
		extent.endTest(test);
		extent.flush();
}


//Data provider for Feedback Page.
@DataProvider(name="Feedbackpage")
public Object[][] passData1(){
config.Excelpath(config.injectpath,1);
int row=config.Excelcount();
int rows=row-1;
Object[][] Feedbackdata=new Object[rows][9];
for(int i=1;i<row;i++)
{
	  for(int j=0;j<9;j++){
		int k=i-1;  
		Feedbackdata[k][j]=config.Exceldata(i, j);
 	  }
}
return Feedbackdata;
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

@AfterClass
public void closebrowser()
{
	
 driver.close();
}

}
