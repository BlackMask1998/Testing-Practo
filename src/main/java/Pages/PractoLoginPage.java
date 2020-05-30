package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import PagesAfterLogin.PractoHomePage;

public class PractoLoginPage extends PractoBasePage {
	
	public PractoLoginPage(WebDriver driver, ExtentTest logger) {
		super(driver,logger);
	}
	
	@FindBy(xpath = "//input[@id='username']")
	WebElement UsernameField;
	
	@FindBy(xpath = "//input[@id='password']")
	WebElement PasswordField;
	
	@FindBy(xpath = "//button[@id='login']")
	WebElement SigninBtnField;
	
	@FindBy(xpath = "//span[@id='usernameErrorBlock']")
	WebElement UsernameError;
	
	@FindBy(xpath = "//span[@id='passwordErrorBlock']")
	WebElement PasswordError;
	
	public PractoHomePage Logging(String Username, String Password)
	{
		reportInfo(" Authenticating with Credentials for User Account Login ");
		UsernameField.sendKeys(Username);
		PasswordField.sendKeys(Password);
		SigninBtnField.click();
		waitForPageLoad();
		if(!driver.getTitle().equals(prop.getProperty("PractoLoginPageTitle")))
				reportPass("<b> Logged in Succesfull </b>");
		else
		{
			String ErrorMsg= PasswordError.getText();
			if(!ErrorMsg.isEmpty())
				reportFail(ErrorMsg);
			else
			{
				ErrorMsg=UsernameError.getText();
				reportFail(ErrorMsg);
			}
		}
		PractoHomePage PHP=new PractoHomePage(driver, logger);
		PageFactory.initElements(driver, PHP);
		return PHP;
	}
	
}
