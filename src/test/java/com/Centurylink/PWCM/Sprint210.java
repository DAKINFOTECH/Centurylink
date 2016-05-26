package com.Centurylink.PWCM;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Sprint210 {
	pwcmlibrary config=new pwcmlibrary();
	
	@Test(priority=0,dataProvider="npanxx")
	public void Npanxxlookup(String npa, String nxx)
	{
		WebDriver driver=new FirefoxDriver();
		/*Open tools URL and Login*/
		driver.get(config.toolsurl);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("submit-button")).isDisplayed();
		config.login(driver);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		/*Check NPA/NXX links present and switch to NPA/NXX*/
		driver.findElement(By.linkText("NPA/NXX Lookup Tool")).isDisplayed();
		String parentw=driver.getWindowHandle();
		driver.findElement(By.linkText("NPA/NXX Lookup Tool")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		/*Login into production page and come back to NPA/NXX page*/
		Set<String> prods=driver.getWindowHandles();
		Iterator<String> prodi=prods.iterator();
		while(prodi.hasNext()){
			String childw=prodi.next();
			if(!parentw.equalsIgnoreCase(childw))
			{
				driver.switchTo().window(childw);
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				driver.findElement(By.name("logon")).isDisplayed();
				driver.findElement(By.name("password")).isDisplayed();
				driver.findElement(By.linkText("I forgot my password")).isDisplayed();
				config.prodlogin(driver);
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				driver.findElement(By.id("welcomeUser")).isDisplayed();
				
				driver.close();
			}
		}
		driver.switchTo().window(parentw);
		/* Switching to NPA/NXX site */
		driver.findElement(By.linkText("NPA/NXX Lookup Tool")).isDisplayed();
		driver.findElement(By.linkText("NPA/NXX Lookup Tool")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		Set<String> npas=driver.getWindowHandles();
		Iterator<String> npai=npas.iterator();
		while(npai.hasNext()){
			String childw=npai.next();
			if(!parentw.equalsIgnoreCase(childw))
			{
				driver.switchTo().window(childw);
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[text()='NPA/NXX/CLLI Lookup']")).isDisplayed();
				driver.findElement(By.xpath("//input[@value='SUBMIT']")).isDisplayed();
				
				/*Enter the NPA and NXX values then submit*/
				driver.findElement(By.name("npa")).sendKeys(npa);
				driver.findElement(By.name("nxx")).sendKeys(nxx);
				driver.findElement(By.xpath("//input[@value='SUBMIT']")).click(); //
				driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				driver.close();
			}
		}
		driver.switchTo().window(parentw);
				
		driver.close();
	}
	
	//Data provider for NPA/NXX.
	@DataProvider(name="npanxx")
	public Object[][] passData(){
	config.Excelpath(config.injectpath,2);
	int row=config.Excelcount();
	int rows=row-1;
	Object[][] npanxxdata=new Object[rows][2];
	for(int i=1;i<row;i++)
	{
		  for(int j=0;j<2;j++){
			int k=i-1;  
			npanxxdata[k][j]=config.Exceldata(i, j);
	 	  }
	}

	return npanxxdata;
	}
	

}
