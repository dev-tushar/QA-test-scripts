package org.myProject.keywords;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;

public class userHomePageActions extends GetPage{

	int count;

	public userHomePageActions(WebDriver driver) {
		
		super(driver, "UserHomePage");
	}
	
	
	public void navigateToUserProfile()
	{
		wait.waitForPageToLoadCompletely();
		clickUsingJS(element("ddown_userAccount"));
		wait.waitForPageToLoadCompletely();


	}
	
	public void signOut()
	{
		clickUsingJS(element("link_signOut"));
	}

	public void navigateToAddPublication() {

		
		clickUsingJS(element("link_addPublication"));
		wait.waitForPageToLoadCompletely();

	}
	
	public void verifyUserOnEditProfile()
	{
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_editPersonalInfo");
		
	}
	
	public void navigateToEditProfile()
	{

		click(element("link_viewProfile"));
		wait.waitForPageToLoadCompletely();
	}
	
	public void navigateToSideMenuItem(String menuItem)
	{
		click(element("menuItems_sidePanel", menuItem));
		wait.waitForPageToLoadCompletely();

	}
	
}
