package org.myProject.keywords;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;

public class UserProfilePageActions extends GetPage {

	public UserProfilePageActions(WebDriver driver) {
		super(driver, "UserProfilePage");
	}
	
	public void navigateToAddPublications()
	{
		click(element("link_addPublications"));
	}
	

}
