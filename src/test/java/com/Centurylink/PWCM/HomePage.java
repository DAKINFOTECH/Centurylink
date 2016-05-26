package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Centurylink.PWCM.pwcmlibrary;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class HomePage {
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	pwcmlibrary config=new pwcmlibrary();
		
	String Homeimg="//img[@src='https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTy6yBiky4iWbqq2qnPPYT7X8CQA_o_GGWbiZvtW5IapfIcbd_C']";
	String resourceimg="//img[@src='https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSnXtWT2sNGRvc4T7I_SgH88-xewD8j0L9q6lrwUvMA2HSFJkAmLw']";
	String productandsoluimg="//img[@src='http://www.localinternetservice.com/images/localinternet/centurylink-brand/devices.jpg']";
	String copyright="    © 2016 CenturyLink. All Rights Reserved.";
		
	  @BeforeClass
	   @Parameters("browser")
	   public void drivercall(String browserName) throws InterruptedException
	   {
		driver=BrowserClass.browserSelection(browserName);		
	   }
	
	
	    @Test(priority=0)		
	    public void UtilityLinks(){
		extent=new ExtentReports(config.reportpath,true);
		test=extent.startTest("Global Header-Utility Links-US49296");
		test.assignCategory("HomePage");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(config.baseurl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "pwcmint Homepage");
		driver.findElement(By.id("submit-button")).isDisplayed();
		
		/*PWCM login*/
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Login Success");
		
		/* User can see the utility links*/
		driver.findElement(By.className("dropdown-toggle")).isDisplayed();
		driver.findElement(By.linkText("SalesForce")).isDisplayed();
		driver.findElement(By.linkText("Help")).isDisplayed();
		driver.findElement(By.linkText("Feedback")).isDisplayed();
		driver.findElement(By.linkText("Logout")).isDisplayed();
		test.log(LogStatus.PASS, "Utility links are visible");
		
		/*User can select a Country*/
		String parentw=driver.getWindowHandle();
		driver.findElement(By.className("dropdown-toggle")).isDisplayed();
		driver.findElement(By.className("dropdown-toggle")).click();
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
		
		/*User can click on the CTL LOGO and navigate back to the home page*/
		driver.findElement(By.linkText("Resources")).click();
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.findElement(By.xpath(resourceimg)).isDisplayed();
		driver.findElement(By.className("ctlbiz-logo")).click();
		driver.findElement(By.xpath(Homeimg)).isDisplayed();
		test.log(LogStatus.PASS, "CTL logo is clickable and user is navigated to the Home page");
		
		/*User can view a Search utility */
		driver.findElement(By.className("search_textBox")).isDisplayed();
		driver.findElement(By.className("search_textBox")).sendKeys("Search text working");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "Search utility is present");
		
		/*User can view the View As drop down*/
		driver.findElement(By.xpath("//div[@class='dropdown-toggle active']")).isDisplayed();
		driver.findElement(By.xpath("//div[@class='dropdown-toggle active']/i[@class='fa fa-chevron-down']")).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "View As dropdown is present");
		
		/*User can view the link to SalesForce*/
		driver.findElement(By.linkText("SalesForce")).isDisplayed();
		test.log(LogStatus.PASS, "Sales Force link is present");
		
		test.log(LogStatus.PASS, "UtilityLinks Test Passed");
		extent.endTest(test);
		extent.flush();
	}
	    
	    @Test(priority=1)		
	    public void QuickLinks(){
	    driver.get(config.baseurl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test=extent.startTest("Global Footer-Quick Links-US49297");
		test.assignCategory("HomePage");
		test.log(LogStatus.PASS, "pwcmint Homepage");
		/*User can see a section containing quick links to frequently used pages within Products and Resources sections */
		driver.findElement(By.linkText("Home")).isDisplayed();
		driver.findElement(By.linkText("Products and Solutions")).isDisplayed();
		driver.findElement(By.linkText("Resources")).isDisplayed();
		test.log(LogStatus.PASS, "Quick link Home,Product&solutions and Resources is present");
		
		/*Navigate to pages within the site*/
		driver.findElement(By.linkText("Products and Solutions")).click();
		driver.findElement(By.xpath(productandsoluimg)).isDisplayed();
		driver.findElement(By.linkText("Resources")).click();
		driver.findElement(By.xpath(resourceimg)).isDisplayed();
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.xpath(Homeimg)).isDisplayed();
		test.log(LogStatus.PASS, "Links are functional, navigation works properly");
		
		/* Links TBD my content manager and editable by content manager*/
		test.log(LogStatus.INFO, "Links TBD are editable by content manager");
		
		/* User can see a section containing copyright statement, use current partner site (© 2016, CenturyLink. All Rights Reserved)*/
		driver.findElement(By.linkText("Products and Solutions")).click();
		Assert.assertEquals(copyright, driver.findElement(By.xpath("//p[@class='small']")).getText());
		driver.findElement(By.linkText("Resources")).click();
		Assert.assertEquals(copyright, driver.findElement(By.xpath("//p[@class='small']")).getText());
		driver.findElement(By.linkText("Home")).click();
		Assert.assertEquals(copyright, driver.findElement(By.xpath("//p[@class='small']")).getText());
		test.log(LogStatus.PASS, "Copyright statement is present on the page");
		test.log(LogStatus.PASS, "Quiclinks Test Passed");
		extent.endTest(test);
		extent.flush();
	    }
	    
	    
		@Test(priority=2)		
		public void Homepage(){
			driver.get(config.baseurl);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			test=extent.startTest("Global Header-Home Page-US49292");
			test.assignCategory("HomePage");
			test.log(LogStatus.PASS, "pwcmint Homepage");
			
			/*User is able to land on a home page*/
			driver.findElement(By.linkText("Home")).click();
			driver.findElement(By.xpath(Homeimg)).isDisplayed();
			test.log(LogStatus.PASS, "Home page is accessible by user");
			
			/*User is able to see the global header, footer, landing page body*/
			driver.findElement(By.className("dropdown-toggle")).isDisplayed();
			driver.findElement(By.linkText("SalesForce")).isDisplayed();
			driver.findElement(By.linkText("Help")).isDisplayed();
			driver.findElement(By.linkText("Feedback")).isDisplayed();
			driver.findElement(By.linkText("Logout")).isDisplayed();
			driver.findElement(By.xpath(Homeimg)).isDisplayed();
			driver.findElement(By.className("ctlbiz-logo")).isDisplayed();
			driver.findElement(By.className("search_textBox")).isDisplayed();
			driver.findElement(By.xpath("//div[@class='dropdown-toggle active']")).isDisplayed();
			driver.findElement(By.className("corp-logo")).isDisplayed();
			driver.findElement(By.linkText("Home")).isDisplayed();
			driver.findElement(By.linkText("Contact Us")).isDisplayed();
			driver.findElement(By.linkText("Site Map")).isDisplayed();
			driver.findElement(By.linkText("Policies")).isDisplayed();
			test.log(LogStatus.PASS, "Global header/footer/landing page body are present");
			
			/*User see Home tab is bolded as active tab*/
			driver.findElement(By.xpath("//span[@class='nav-home' and @style='font-weight: bolder;']")).isDisplayed();
			test.log(LogStatus.PASS, "Home tab is bolded as active tab");
					
			test.log(LogStatus.PASS, "HomePage Test Passed");
			extent.endTest(test);
			extent.flush();
		}
		
		@Test(priority=3)		
		public void NavigationBar(){
			driver.get(config.baseurl);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			test=extent.startTest("Global Header-Navigation Bar-US49294");
			test.assignCategory("HomePage");
			test.log(LogStatus.PASS, "pwcmint Homepage");
			
			/* User can see Navigation tabs-Home, Products and Solutions, Resources*/
			driver.findElement(By.linkText("Home")).isDisplayed();
			driver.findElement(By.linkText("Products and Solutions")).isDisplayed();
			driver.findElement(By.linkText("Resources")).isDisplayed();
			test.log(LogStatus.PASS, "Navigation tabs (Home, Products and Solutions, Resources) are present");
			
			/*User sees Bolded tab for active tab*/
			driver.findElement(By.linkText("Products and Solutions")).click();
			driver.findElement(By.xpath("//span[@class='nav-products' and @style='font-weight: bold;']")).isDisplayed();
			driver.findElement(By.linkText("Resources")).click();
			driver.findElement(By.xpath("//span[@class='nav-resources' and @style='font-weight: bold;']")).isDisplayed();
			driver.findElement(By.linkText("Home")).click();
			driver.findElement(By.xpath("//span[@class='nav-home' and @style='font-weight: bolder;']")).isDisplayed();
			test.log(LogStatus.PASS,"Bolded tab for active tab (clicked tab) is present");
			test.log(LogStatus.PASS,"Home tab is clickable and returns the user to the Home page");
			
			test.log(LogStatus.PASS, "NavigationBar Test Passed");
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

    @AfterClass
    public void closebrowser()
    {
    	
     driver.close();
    }

}
