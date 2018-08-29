package org.myProject.keywords;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;

public class WelcomeToCollabratecPageActions extends GetPage

{

	WebDriver driver;
	
	public WelcomeToCollabratecPageActions(WebDriver driver) {
		super(driver, "WelcomeToCollabratecPage");
		this.driver = driver;
	}

	public void clickOnAgreeButton()
	{
		wait.waitForElementToBeClickable(element("btn_agree"));
		click(element("btn_agree"));
	}
	
	
	
	
}
