package TestCases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.MainClass;
import Pages.PractoBasePage;
import Pages.PractoLoginPage;
import PagesAfterLogin.PractoHomePage;
import PagesAfterLogin.PractoSearchReslutPage;
import Setup.ReadExcelData;

public class TestCase1_ValidLoginwithAutoLocation extends MainClass{

	PractoBasePage PBP;
	PractoLoginPage PLP;
	PractoHomePage PHP;
	PractoSearchReslutPage PSRP;
	
	@Test(priority = 0, dataProvider = "Authentication")
	public void testcase1(String Username, String Password)
	{
		logger=report.createTest("TestCase1");
		invokeBrowser("Browser2");
		
		PBP= openApplication();
		PageTitle(prop.getProperty("PractoHomePageTitle"));

		PLP= PBP.redirectToPractoLoginPage();		
		PageTitle(prop.getProperty("PractoLoginPageTitle"));
		
		PHP=PLP.Logging(Username, Password);
		PHP.UserLogged();
		
		/* For Manual Location */
		/*PHP.ManualLocation();
		PSRP= PHP.Search();
		PSRP.checkSearchResultForManualLocation();
		
		/* For Auto Location that Detects Your Location*/
		PHP.AutoLocation();
		PSRP=PHP.Search();
		PSRP.ResultsDisplayedForAutoDetectLocation();
		
		PSRP.Filtering();
		PSRP.ShowHospitalCards();
		
		PSRP.ListOutHospitalsInExcelSheet("Best_Hospitals_24/7");
	}
	
	@DataProvider(name = "Authentication")
	public Object[][] getCredentialsData() throws IOException
	{
		return ReadExcelData.getCredentailsData("CredentailsData.xlsx", "AccountCredentials", "TestCase1");
	}
	@AfterMethod
	public void closeDriver()
	{
		closeBrowser();
	}

	
	
	
	
	
	
}
