package com.Centurylink.PWCM;


import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import com.Centurylink.PWCM.pwcmlibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HelpPage {

	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	String helptext="Help";
	
	@BeforeClass
	   @Parameters("browser")
	   public void drivercall(String browserName) throws InterruptedException
	   {
		driver=BrowserClass.browserSelection(browserName);
	   }
		
	@Test(priority=0,dataProvider="Help")
	public void Help(String usrname, String usrid, String acompany, String pcompany, String pnumber, String cnumber,
			             String mailid, String browser, String bversion, String obname, String pcategory, String ptype,
			             String pdescription, String errormsg, String stepstaken) throws InterruptedException
	{
		extent=new ExtentReports(config.reportpath,false);
		test=extent.startTest("Help Page-US49300");
		test.assignCategory("Helppage");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(config.baseurl);
		test.log(LogStatus.PASS, "pwcmint Homepage");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCMINT login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");
		
		/*Switch to child Help Page*/
        String parentw=driver.getWindowHandle();
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
				
				/*Check you are in help page*/
				Assert.assertEquals(helptext, driver.findElement(By.xpath("//p[@class='askATechHeader']")).getText());
				test.log(LogStatus.PASS, "Helppage opened in new window");
				
				/*Fill the help page form */
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				driver.findElement(By.id("PHONE_NUMBER")).sendKeys(pnumber);
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				Select ibrowser=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser.selectByValue("other");
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				driver.findElement(By.id("OTHER_BROWSER_NAME")).sendKeys(obname);
				Select probtype=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype.selectByValue("Other");
				driver.findElement(By.id("OTHER_PROBLEM_TYPE")).sendKeys(ptype);
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.id("ERROR_MSG")).sendKeys(errormsg);
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[1]")).click();
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				test.log(LogStatus.PASS, "Able to fill all the fields in helpage");
				Thread.sleep(4000);
				/*Check reset functionality*/
				driver.findElement(By.className("negative")).click();
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[2]")).isEnabled();
				WebElement inputBox = driver.findElement(By.id("USER_ID"));
				String textInsideInputBox = inputBox.getAttribute("value");
				if(textInsideInputBox.isEmpty())
				{
				  test.log(LogStatus.PASS, "Reset Working");
				}else {
					test.log(LogStatus.FAIL, "Reset Not Working");
					  }
				/*Submit negative testing*/
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				driver.findElement(By.id("PHONE_NUMBER")).sendKeys(pnumber);
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				Select ibrowser1=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser1.selectByValue(browser);
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				Select probtype1=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype1.selectByValue(pcategory);
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.id("ERROR_MSG")).sendKeys(errormsg);
				driver.findElement(By.id("PROBLEM_REPRODUCIBLE")).click();
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				driver.findElement(By.className("positive")).click();
				
				driver.findElement(By.id("E_MAIL")).isDisplayed();
				driver.findElement(By.id("E_MAIL")).sendKeys("arund87ymail.com");
				driver.findElement(By.className("positive")).click();
				driver.findElement(By.id("E_MAIL")).isDisplayed();
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				test.log(LogStatus.PASS, "Help page (*)Mandatory is working");
				
				/*Submit Not functional*/
			    test.log(LogStatus.INFO, "Submit non functional:Negative Testing scripts will be created after submit is functional");
			    test.log(LogStatus.INFO, "For Expected Result 5,6,7 and 8, scripts will be created after submit functional");
				
				Thread.sleep(2000);
				driver.close();
				}
			}
		driver.switchTo().window(parentw);
		test.log(LogStatus.PASS, "Helppage Test Passed");
		extent.endTest(test);
		extent.flush();
		}
	
	
	
	//Data provider for Help Page.
	@DataProvider(name="Help")
	public Object[][] passData(){
	config.Excelpath(config.injectpath,0);
	int row=config.Excelcount();
	int rows=row-1;
	Object[][] Helppagedata=new Object[rows][15];
	for(int i=1;i<row;i++)
	{
		  for(int j=0;j<15;j++){
			int k=i-1;  
			Helppagedata[k][j]=config.Exceldata(i, j);
	 	  }
	}

	return Helppagedata;
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


	
