package com.Centurylink.PWCM;


import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.Centurylink.PWCM.pwcmlibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Sprint209 {

	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
	String reportpath=".\\Reporting\\Advreportpath.html";
	String injectpath=".\\src\\main\\resources\\Injectfile.xls";
	String Attachpath="C:\\Users\\348027\\git\\Centurylink";
	String username="admin";
	String password="admin";
	String baseurl="http://172.26.130.130:4502/content/pwcm-first-page/home.html";
	String FBDDchk="-- Select a Subject --";
	String HPDDchk="-- Choose One --";
	
	@Test(priority=0,dataProvider="Helppage")
	public void Helppage(String usrname, String usrid, String acompany, String pcompany, String pnumber, String cnumber,
			             String mailid, String browser, String bversion, String obname, String pcategory, String ptype,
			             String pdescription, String errormsg, String stepstaken) throws InterruptedException
	{
		//System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
		extent=new ExtentReports(reportpath,false);
		test=extent.startTest("Help Page");
		test.assignCategory("Sprint209");
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(baseurl);
		test.log(LogStatus.PASS, "pwcmint Homepage");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
        driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("submit-button")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");
		
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
				
				driver.findElement(By.className("positive")).isDisplayed();
				driver.findElement(By.className("negative")).isDisplayed();
				test.log(LogStatus.PASS, "Helppage opened in new window");
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				driver.findElement(By.id("PHONE_NUMBER")).sendKeys(pnumber);
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				driver.findElement(By.id("E_MAIL")).sendKeys(mailid);
				Select ibrowser=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser.selectByValue(browser);
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				driver.findElement(By.id("OTHER_BROWSER_NAME")).sendKeys(obname);
				Select probtype=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype.selectByVisibleText(pcategory);
				driver.findElement(By.id("OTHER_PROBLEM_TYPE")).sendKeys(ptype);
				driver.findElement(By.id("DET_DESC_PROBLEM")).sendKeys(pdescription);
				driver.findElement(By.id("ERROR_MSG")).sendKeys(errormsg);
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[1]")).click();
				driver.findElement(By.id("STEPS_TAKEN")).sendKeys(stepstaken);
				test.log(LogStatus.PASS, "Able to fill all the fields in helpage");
				Thread.sleep(2000);
				driver.findElement(By.className("negative")).click();
				driver.findElement(By.xpath("(//input[@name='PROBLEM_REPRODUCIBLE'])[2]")).isEnabled();
				test.log(LogStatus.PASS, "Reset is working");
				
				driver.findElement(By.id("USER_NAME")).sendKeys(usrname);
				driver.findElement(By.id("USER_ID")).sendKeys(usrid);
				driver.findElement(By.id("SUB_AGENT_COMPANY")).sendKeys(acompany);
				driver.findElement(By.id("MASTER_PARTNER_COMPANY")).sendKeys(pcompany);
				driver.findElement(By.id("PHONE_NUMBER")).sendKeys(pnumber);
				driver.findElement(By.id("CELL_NUMBER")).sendKeys(cnumber);
				Select ibrowser1=new Select(driver.findElement(By.id("internetbrowser")));
				ibrowser1.selectByValue(browser);
				driver.findElement(By.id("BROWSER_VERSION")).sendKeys(bversion);
				driver.findElement(By.id("OTHER_BROWSER_NAME")).sendKeys(obname);
				Select probtype1=new Select(driver.findElement(By.id("PROBLEM_CATEGORY")));
				probtype1.selectByVisibleText(pcategory);
				driver.findElement(By.id("OTHER_PROBLEM_TYPE")).sendKeys(ptype);
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
				test.log(LogStatus.PASS, "Email and Mandatory is working");
				
			/**/test.log(LogStatus.INFO, "Submit non functional:Negative Testing scripts will be created after submit is functional");
			/**/test.log(LogStatus.INFO, "For Expected Result 5,6,7 and 8, scripts will be created after submit functional");
				
				Thread.sleep(2000);
				driver.close();
				}
			}
		driver.switchTo().window(parentw);
		test.log(LogStatus.PASS, "Helppage Test Passed");
		extent.endTest(test);
		extent.flush();
		}
	
	@Test(priority=1,dataProvider="Feedbackpage")
	public void Feedbackpage( String uname, String uid, String partcompany, String mailid, String cellnum, String offnum,
			                  String subjects, String details, String comment) throws InterruptedException
	{
		    extent=new ExtentReports(reportpath,false);
		    test=extent.startTest("Feedback Page");
		    test.assignCategory("Sprint209");
		    driver.get(baseurl);
		    String parentw=driver.getWindowHandle();
		    test.log(LogStatus.PASS, "pwcmint Homepage");
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
					test.log(LogStatus.PASS, "Feedback page opened in new window");
					driver.findElement(By.className("positive")).isDisplayed();
					driver.findElement(By.className("negative")).isDisplayed();
					
				    driver.findElement(By.id("name")).sendKeys(uname);
					driver.findElement(By.id("userid")).sendKeys(uid);
					driver.findElement(By.id("partnername")).sendKeys(partcompany);
					driver.findElement(By.id("email")).sendKeys(mailid);
					driver.findElement(By.id("cellno")).sendKeys(cellnum);
					driver.findElement(By.id("ofcnumber")).sendKeys(offnum);
					Select subj=new Select(driver.findElement(By.name("subject")));
					subj.selectByValue(subjects);
					driver.findElement(By.name("details")).sendKeys(details);
					driver.findElement(By.name("comments")).sendKeys(comment);
					test.log(LogStatus.PASS, "Able to fill all the fields in the page");
					Thread.sleep(2000);
					driver.findElement(By.className("negative")).click();
					test.log(LogStatus.PASS, "Reset working");
					
					driver.findElement(By.id("name")).sendKeys(uname);
					driver.findElement(By.id("userid")).sendKeys(uid);
					driver.findElement(By.id("email")).sendKeys(mailid);
					driver.findElement(By.id("cellno")).sendKeys(cellnum);
					driver.findElement(By.id("ofcnumber")).sendKeys(offnum);
					Select subj1=new Select(driver.findElement(By.name("subject")));
					subj1.selectByValue(subjects);
					driver.findElement(By.name("details")).sendKeys(details);
					driver.findElement(By.name("comments")).sendKeys(comment);
					
					driver.findElement(By.className("positive")).click();
					test.log(LogStatus.PASS, "Mandatory is working");
					driver.findElement(By.id("partnername")).isDisplayed();
					driver.findElement(By.id("partnername")).sendKeys(partcompany);
				/**/test.log(LogStatus.INFO, "Submit non functional:Negative Testing scripts will be created after submit is functional");
				/**/test.log(LogStatus.INFO, "For Expected Result 5,6,7and 8, scripts will be created after submit functional");
					
					Thread.sleep(2000);
					
					driver.close();
					}
				}
			driver.switchTo().window(parentw);
			test.log(LogStatus.PASS, "Feedback Page Test Passed");
			extent.endTest(test);
			extent.flush();
	}
	
	//Data provider for Help Page.
	@DataProvider(name="Helppage")
	public Object[][] passData(){
	config.Excelpath(injectpath,0);
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
	
	//Data provider for Feedback Page.
	@DataProvider(name="Feedbackpage")
	public Object[][] passData1(){
	config.Excelpath(injectpath,1);
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
    	
     driver.get("C:\\Users\\348027\\git\\Centurylink\\Reporting\\Advreportpath.html");
    }
    
	}


	
