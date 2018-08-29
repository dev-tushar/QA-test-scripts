package org.myProject.keywords;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;

public class SignInPageActions extends GetPage{

	public SignInPageActions(WebDriver driver) {
		super(driver, "SignInPage");
	}
	
	public void enterCredentials()
	{
		element("input_uname").sendKeys("03278066@domain.com");
		System.out.println("Entered username "+"03278066@domain.com");
		element("input_pword").sendKeys("qaIEEE,1");
		System.out.println("Entered password "+"qaIEEE,1");
		click(element("btn_signIn"));
		click(element("input_searchbox"));

	}

	public String getUsername() 
	{
		System.out.println("Username is "+element("txt_uname").getAttribute("innerText").trim());
		return element("txt_uname").getAttribute("innerText").trim();
		
	}
	
	
	

}
