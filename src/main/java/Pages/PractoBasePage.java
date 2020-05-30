package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Base.MainClass;

public class PractoBasePage extends MainClass {
	
	public PractoBasePage(WebDriver driver, ExtentTest logger)
	{
		this.driver=driver;
		this.logger=logger;
	}
	@FindBy(xpath = "//a[contains(@name,'Practo login')]")
	WebElement LoginBtn;
	
	public PractoLoginPage redirectToPractoLoginPage()
	{
		reportInfo("Redirecting To Practo Login Page");
		LoginBtn.click();
		waitForPageLoad();
		reportPass(" Redirected To Practo Login Page ");
		PractoLoginPage PLP= new PractoLoginPage(driver,logger);
		PageFactory.initElements(driver,PLP);
		return PLP;
	}
}
