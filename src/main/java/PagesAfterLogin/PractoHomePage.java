package PagesAfterLogin;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Pages.PractoBasePage;

public class PractoHomePage extends PractoBasePage {

	public PractoHomePage(WebDriver driver, ExtentTest logger) {
		super(driver,logger);
	}
	
	@FindBy(xpath = "//*[@class='up-triangle']//span[@class='user_info_top']")
	WebElement UserAccountNameEle;
	
	@FindBy(xpath = "//input[@placeholder='Search location']")
	WebElement LoctionBar;
	
	@FindBy(xpath = "//*[contains(@class,'suggestion-group')]//*")
	List<WebElement> LocationSuggestionLists;
	
	@FindBy(xpath = "//input[contains(@placeholder,'hospitals')]")
	WebElement SearchBar;
	
	@FindBy(xpath = "//*[contains(@class,'suggestion-item')]//*")
	List<WebElement> SearchSuggestionLists;
	
	@FindBy(xpath = "//*[@data-qa-id='current_location']")
	WebElement AutoDetect;


	public void UserLogged()
	{
		String UserAccountName= UserAccountNameEle.getText();

		reportPass(" Redirected To Home Page ");
		reportInfo( "<b>"+UserAccountName+"</b> Logged In ");
	}
	
	public void ManualLocation()
	{
		reportInfo(" Setting Location Manually ");
		String location= prop.getProperty("ManualLocation");
		LoctionBar.clear();
		waitLoad(1);
		LoctionBar.sendKeys(location);
		WaitForAjax();
		
		for(WebElement SuggestionAslocation: LocationSuggestionLists)
			if(SuggestionAslocation.getText().equalsIgnoreCase(location))
			{
				SuggestionAslocation.click();
				break;
			}
	}
	
	public void AutoLocation()
	{
		reportInfo(" Setting Your Location Automatically ");
		AutoDetect.click();
		WaitForAjax();	
	}
	
	
	public PractoSearchReslutPage Search()
	{
		
		String SearchingFor=prop.getProperty("SearchFor");
		SearchBar.sendKeys(SearchingFor);;
	
		reportInfo("Searching For <b>"+SearchingFor+"</b>");
		
		WaitForAjax();
		
		for(WebElement SuggestionAsSearch: SearchSuggestionLists)
			if(SuggestionAsSearch.getText().equalsIgnoreCase(SearchingFor))
			{
				SuggestionAsSearch.click();
				break;
			}
		
		waitForPageLoad();
		PractoSearchReslutPage PSRP= new PractoSearchReslutPage(driver, logger);
		PageFactory.initElements(driver, PSRP);
		return PSRP;
	}
	
}
