package Base;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Pages.PractoBasePage;
import Utilities.ExtentReportManagerClass;
import Utilities.PropConfigLoadClass;

public class MainClass {

	public WebDriver driver;
	public Properties prop= PropConfigLoadClass.loadFile();
	public ExtentReports report = ExtentReportManagerClass.getReportInstance();
	public ExtentTest logger;
	
	public void invokeBrowser(String browserNameKey)
	{
		
		String browserName= prop.getProperty(browserNameKey);
		reportInfo("Opening "+browserName+ " Browser ");
		String DriverPath=null;
		if(browserName.equalsIgnoreCase("Chrome"))
		{
			DriverPath= System.getProperty("user.dir")+prop.getProperty("ChromePath");
			System.setProperty("webdriver.chrome.driver", DriverPath);
			DesiredCapabilities caps = new DesiredCapabilities();
			ChromeOptions co= new ChromeOptions();
			co.addArguments("disable-infobars");
			co.addArguments("--incognito");
			Map < String, Object > prefs = new HashMap < String, Object > ();
		    Map < String, Object > profile = new HashMap < String, Object > ();
		    Map < String, Object > contentSettings = new HashMap < String, Object > ();

		    // SET CHROME OPTIONS
		    // 0 - Default, 1 - Allow, 2 - Block
		    contentSettings.put("geolocation", 1);
		    profile.put("managed_default_content_settings", contentSettings);
		    prefs.put("profile", profile);
		    co.setExperimentalOption("prefs", prefs);

		    // SET CAPABILITY
		    caps.setCapability(ChromeOptions.CAPABILITY, co);
			driver= new ChromeDriver(co);
			reportPass(browserName+ " Browser Opened ");
		}
		else if(browserName.equalsIgnoreCase("FireFox"))
		{
			DriverPath=System.getProperty("user.dir")+prop.getProperty("FirefoxPath");
			System.setProperty("webdriver.gecko.driver", DriverPath);
			driver= new FirefoxDriver();
			reportPass(browserName+ " Browser Opened ");
		}
		else if(browserName.equalsIgnoreCase("InternetExplorer"))
		{
			DriverPath=System.getProperty("user.dir")+prop.getProperty("IEPath");
			System.setProperty("webdriver.ie.driver", DriverPath);
			driver= new InternetExplorerDriver();
			reportPass(browserName+ " Browser Opened ");
		}
		else
		{
			System.out.println("No Driver Found for "+browserName+" browser .");
			reportFail("No Driver Found for "+browserName+" browser .");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
	}
	
	
	
	/********************************* Redirects to PractoBase Application ************************/
	
	public PractoBasePage openApplication()
	{
		String url=prop.getProperty("ApplicationWebsite");
		reportInfo("Opening Application <b>"+url +"</b> ");
		driver.get(url);
		waitForPageLoad();
		reportPass("Site Opened ");
		PractoBasePage PBP= new PractoBasePage(driver, logger);
		PageFactory.initElements(driver, PBP);
		return PBP;
	}
	
	/*********************************** Checking Title *******************************************/
	
	public void PageTitle(String expectedTitle)
	{
		reportInfo("Checking Page Title : "+"<b>"+ expectedTitle +"</b>");
		String actualTitle= driver.getTitle();
		if(expectedTitle.equalsIgnoreCase(actualTitle))
			reportPass("Checked Page Title as Expected  ");
		else
			reportFail("Checked Page Title Not as Expected  ");
		
	}
	
	/********************************** Reporting Assertion ***************************************/
	
	public void reportFail(String ReportMsg)
	{
		logger.log(Status.FAIL, "<b>"+ReportMsg+"</b>");
		takeScreenshotOnFailure(ReportMsg);
		Assert.fail(ReportMsg);
	}
	
	public void reportPass(String ReportMsg)
	{
		logger.log(Status.PASS, ReportMsg);
	}
	
	public void reportInfo(String ReportMsg)
	{
		logger.log(Status.INFO, ReportMsg);
	}
	
	@AfterClass
	public void flushReports() {
		report.flush();
		
	}
	public void takeScreenshotOnFailure(String name)
	{
		TakesScreenshot capture= (TakesScreenshot) driver;
		File srcFile= capture.getScreenshotAs(OutputType.FILE);
		
		File destFile= new File(System.getProperty("user.dir")+"\\Resources\\ScreenShotsOnFailure\\"+name+".png");
		
		try {
			FileUtils.copyFile(srcFile, destFile);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir")+"\\Resources\\ScreenShotsOnFailure\\"+name+".png");
		} catch (IOException e) {
		
			System.out.println("Unable to Capture Screenshot on Failure ");
		}
	}
	
	/**************************** Wait Functions in Framework ************************************/
	
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		while (true) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) 
				break;
			 else 
				waitLoad(1);
		}
		waitLoad(2);
		
	}
		public void WaitForAjax()
		{
			JavascriptExecutor executor = (JavascriptExecutor)driver;
		    if((Boolean) executor.executeScript("return window.jQuery != undefined")){
		        while(!(Boolean) executor.executeScript("return jQuery.active == 0")){
		           waitLoad(1);
		        }
		    }
		    waitLoad(3);
		}

	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (Exception e) {}
	}

	/********************************* Closing Driver **********************/
	
	public void closeBrowser()
	{
		driver.quit();
	}	
}
