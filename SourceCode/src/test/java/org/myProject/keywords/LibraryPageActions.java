package org.myProject.keywords;

import java.util.ArrayList;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LibraryPageActions extends GetPage{

	public LibraryPageActions(WebDriver driver) {
		super(driver, "LibraryPage");
	}
	
	
	public void navigateToLibrarySettings()
	{
		click(element("menuItem_LibSettings"));
		wait.waitForPageToLoadCompletely();
	}

	public void navigateToMyDocuments()
	{
		click(elements("tab_myDocs").get(0));
		wait.waitForMsgToastToDisappear();
		
	}
	
	public ArrayList<String> getDocumentTitles()
	{
		ArrayList<String> doctitles = new ArrayList<>();
		for(WebElement we: elements("list_docTitles"))
		{
			doctitles.add(we.getText().trim());
		}
 	
		return doctitles;
	}
	
	public void clickOnMyPublicationFilter()
	{
		click(elements("list_filters").get(5));
	}
}
