package org.myProject.keywords;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class MarketingPageActions extends GetPage{
	
	WebDriver driver;
	
	public MarketingPageActions(WebDriver driver) {
		super(driver, "CollabratecMarketingPage");
		this.driver = driver;
	}

	public void clickOnCreateAccount()
	{
		scroll(element("icon_CreateAccount"));
		wait.waitForElementToBeVisible(element("icon_CreateAccount"));
		clickUsingJS(element("icon_CreateAccount"));
	}

	public void clickOnSignIn() {
		
		scroll(element("icon_CreateAccount"));
		wait.waitForElementToBeVisible(element("icon_CreateAccount"));
		click(element("icon_SignIn"));
	}
	
	public String getLogOutText()
	{
		return element("txt_signOut_msg").getText().trim();
	}

}
