package com.Centurylink.PWCM;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class pwcmlibrary {

	HSSFWorkbook worbk;
	HSSFSheet sheet1;
	
	//locate the Excel file
		public void Excelpath(String path, int sheetnum)
		{
		try {
			FileInputStream fis=new FileInputStream(path);
			worbk=new HSSFWorkbook(fis);
			sheet1=worbk.getSheetAt(sheetnum);
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		}
		
		//Get the text value from Excel 
		public String Exceldata(int rownum,int column) 
		{
			String daata1=sheet1.getRow(rownum).getCell(column).getStringCellValue();
			
			return daata1;
		}
		
		//Get the row count from Excel 
		public int Excelcount(){
			int rows = sheet1.getLastRowNum();
			rows=rows+1;
			return rows;
			}
		
	    //Method for Radio buttons
		  public void rdclick(WebDriver driver, String name, String rdselect){
				List<WebElement> CheckBox=driver.findElements(By.name(name));
				int iSize = CheckBox.size();
				for(int i=0; i < iSize ; i++ )
				      {
					   String sValue = CheckBox.get(i).getAttribute("value");
				          if (sValue.equalsIgnoreCase(rdselect))
				          {
				    	  CheckBox.get(i).click();
				          break;
				          }
				       }
				     return;
			    }
		  //Method for Screenshots
		  public void captureScreenshot(WebDriver driver, String Snap){
			  try {
			  TakesScreenshot clk=(TakesScreenshot)driver;
			  File source=clk.getScreenshotAs(OutputType.FILE);
			  FileUtils.copyFile(source,new File("./Screenshots/"+Snap+".jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			  
		  }
	
}
