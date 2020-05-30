package PagesAfterLogin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;

import Pages.PractoBasePage;
import Setup.WriteExcelData;

public class PractoSearchReslutPage extends PractoBasePage {
	public PractoSearchReslutPage(WebDriver driver, ExtentTest logger) {
		super(driver,logger);
	}
	

	@FindBy(xpath = "//span[@data-qa-id='results_count']")
	WebElement ResultCount;

	@FindBy(xpath = "//*[@for='Open-24X70']")
	WebElement Open24hrsCheckBox;
	
	@FindBy(xpath = "//i[@data-qa-id='all_filters_icon']")
	WebElement AllFilterDropDown;
	
	@FindBy(xpath = "//*[@for='Amenities0']")
	WebElement HasParkingCheckBox;
	
	@FindBy(xpath = "//div[@data-qa-id='hospital_card']")
	List<WebElement> LoadCards;
	
	@FindBy(xpath = "//li[@class='active']")
	WebElement ScrollToLast;
	
	
	List<String> BestRatedHospitalsList= new ArrayList<String>();
	
	public void ResultsDisplayedForAutoDetectLocation()
	{
		reportInfo("Searching Hospitals Near You ");
		int index= driver.getTitle().indexOf('-');
		String ResultsFor= driver.getTitle().substring(0, index);
		System.out.println(ResultsFor);
		String HospFoundCount= ResultCount.getText();
		reportInfo(HospFoundCount +" <b>"+ ResultsFor + "</b> ");
		
	}
	
	public void checkSearchResultForManualLocation()
	{
		String SearchResultPageExpectedTitle= "Best Hospitals in "+ prop.getProperty("ManualLocation") ;
		reportInfo("Checking SearchResults");
		int index =driver.getTitle().indexOf("-");
		String actualTitle=driver.getTitle().substring(0, index);
		
		if(actualTitle.contains(SearchResultPageExpectedTitle))
		{
			reportPass("Search Results as Expected  <b>"+SearchResultPageExpectedTitle+"</b>");
			String HospFoundCount= ResultCount.getText();
			reportInfo(HospFoundCount+" Hospitals found in that location you searched for ");
		}
		else
			reportFail(" Search Results as Not Expected & Found Search Results for   : <b>"+actualTitle+"</b>");
	}
	
	public void Filtering()
	{
		Open24hrsCheckBox.click();
		waitForPageLoad();
		String HospFoundCount= ResultCount.getText();
		reportInfo(HospFoundCount+" Hospitals Found which is <b> Open 24hrs </b> ");
		
		AllFilterDropDown.click();
		waitLoad(1);
		HasParkingCheckBox.click();
		waitForPageLoad();
		HospFoundCount= ResultCount.getText();
		reportInfo(HospFoundCount+" Hospitals Found which <b> Has Parking </b> and <b> Open 24hrs </b> ");
	}
	
	public void ShowHospitalCards()
	{
		int hospCount=Integer.parseInt(ResultCount.getText() );
		if(hospCount==0)
		{
			reportPass("No Near Hospitals Found which <b> Has Parking </b> and <b> Open 24hrs </b> ");
			System.out.println("No Near Hospitals Found that Opens 24hrs and Parking Facility ");
		}
		
		else if(hospCount<=10 && hospCount>0 )
			RatedHospitals();
			
		else
		{
			int presentCount= LoadCards.size();
			if(hospCount>10)
			{
				reportInfo(" Scrolling Page Until "+hospCount+" Hospitals Cards Listed on page ");
				Actions action = new Actions(driver);
				while(hospCount>presentCount)
				{
					WebElement ScrollTo= LoadCards.get(presentCount-1);
					
					action.moveToElement(ScrollTo).perform();
					waitLoad(1);
					action.moveToElement(ScrollToLast).perform();
					waitLoad(1);
					presentCount= LoadCards.size();
				}
				action.moveToElement(LoadCards.get(0)).perform();
				System.out.println("Wait........Results are filtering \nResults will be displyed :");
				RatedHospitals();
			}
		}
		
	}
	
	public void RatedHospitals()
	{
		reportInfo("Listing Hospitals that <b> Rated >=3.5 </b>");
		for(WebElement card :LoadCards)
		{
			try {
				WebElement eachratedcard= card.findElement(By.className("common__star-rating__value"));
				String HospitalName= card.findElement(By.tagName("h2")).getText();
			
				double rateValue=Double.parseDouble(eachratedcard.getText());
				if(rateValue>=3.5)
				{
					BestRatedHospitalsList.add(HospitalName);
				}
				}
			catch(Exception e){}
			
		}
		
		int FoundHospitalsCount= BestRatedHospitalsList.size();
		
		if(!BestRatedHospitalsList.isEmpty())
		{
		reportPass("<b> "+ FoundHospitalsCount+" </b> Hospitals Found which <b> Has Parking </b> and <b> Open 24hrs </b> and <b> Rated Above 3.5 Star </b>  "
				+ "<br> Those Hospitals listed in Excel file i.e <b> Hospitals.xlsx </b> " );
		System.out.println(FoundHospitalsCount+" Hospitals Found that opens 24hrs , has parking and rated above 3.5 star and Check Excel File Hospitals.xlsx ");
		}
		else
		{
			reportPass("<b> NO </b> Hospitals Found which <b>Opens 24/7 </b> ,<b> Has Paking Facility </b> and <b> Rated Above 3.5 Star </b>  " );
			System.out.println("Sorry, No Hospitals Found that opens 24hrs , has parking and rated above 3.5 star");
		}
		
		Iterator<String> itr= BestRatedHospitalsList.iterator();
		while(itr.hasNext())
			System.out.println(itr.next());
		
	}
	
	public void ListOutHospitalsInExcelSheet(String SheetName)
	{
		if(!BestRatedHospitalsList.isEmpty())
			WriteExcelData.ListOutHospitals(BestRatedHospitalsList);
		else
			System.out.println("No Hospitals Data Found to write Excel File");
	}
	
	

}
