package org.myProject.keywords;

import static org.myProject.utils.CustomFunctions.generateUniqueEmailId;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;

public class CreateAccountFormPage extends GetPage {
	
	WebDriver driver;
	YamlInformationProvider yamlvalue = new YamlInformationProvider();
	String useremail = generateUniqueEmailId();

	public CreateAccountFormPage(WebDriver driver) {
		super(driver, "CreateAccountFormPage");
		this.driver = driver;
	}

	public void enterUserInformation()
	{
		wait.waitForElementToBeVisible(element("input_fname"));
		element("input_fname").sendKeys(yamlvalue.getUserDetails("firstname"));
		element("input_lname").sendKeys(yamlvalue.getUserDetails("lastname"));
		element("input_email").sendKeys(useremail);
		element("input_confirmEmail").sendKeys(useremail);
		element("input_password").sendKeys(yamlvalue.getUserDetails("password"));
		element("input_rePassword").sendKeys(yamlvalue.getUserDetails("password"));

	}
	
	public void clickOnAcceptTerms()
	{
		click(element("chkbox_policy"));
	}
	
	public void clickOnCreateAccount()
	{
		wait.waitForElementToBeClickable(element("btn_createAccount"));
		click(element("btn_createAccount"));
	}
	
}
