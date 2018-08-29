package org.myProject.keywords;

import java.util.ArrayList;

import org.hamcrest.generator.qdox.tools.QDoxTester.Reporter;
import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateInterestPageActions extends GetPage{
	
	int count;

	public CreateInterestPageActions(WebDriver driver) {
		
		super(driver, "CreateInterestPage");
	}

	public void selectInterestsViaSearchTab() {
		
		count=0;
		scroll(element("tab_search"));
		click(element("tab_search"));
		element("input_searchInterest").sendKeys("sa");
		wait.waitForElementsToBeVisible(elements("list_searchInterests"));
		logMessage("Search list appeared based on interest typed");
		for(WebElement we: elements("list_searchInterests"))
		{
			click(we);
			count++;
			if(count==3)
				break;
		}
		
	}

	public void selectInterestsViaBrowseTab() {
		
		count=0;
		click(element("tab_browse"));
		for(WebElement we: elements("list_alphabetSearch"))
		{
			//hardWait(3);
			System.out.println(we.getText());
			if(we.getText().trim().equals("J"))
			{
				System.out.println("Found alphabet J ");
				clickUsingJS(we);
				count++;
				int count1 = 0;
				try
				{
					for(WebElement webe: webdriver.findElements(getLocator("list_alphabetSearchResults")))
					{
						clickUsingJS(webe);
						count1++;
						if(count1==3)
							break;
					}
					if(count==1)
						break;
				}
				catch(StaleElementReferenceException se)
				{
					for(WebElement webe: elements("list_alphabetSearchResults"))
					{
						clickUsingJS(webe);
						count1++;
						if(count1==3)
							break;
						//scroll(element("tab_search"));

					}
					if(count==1)
						break;
				}
			}
		}
	}

	public void clickonSelectionsTab() {

		click(element("tab_selections"));
	}
	
	public void removeSelectedInterests()
	{
		int count=0;
		wait.waitForElementToBeVisible(element("tab_selections"));
		for(WebElement we: elements("list_selectedInterests"))
		{
			we.click();
			System.out.println("Removed interest "+count);
			count++;
		}
	}

	public boolean verifyInterestPageIsDisplayed() {
		
		wait.waitForMsgToastToDisappear();
		return isElementDisplayed("title_interestPage");
	}
	
	
	public String getInterestSelectionNumber()
	{
		System.out.println("Selection count now is "+element("span_InterestsNumber").getText());
		return element("span_InterestsNumber").getText();
	}
	


	public void clickOnNextButn() {
		
		click(element("btn_next"));
		
	}
	
	public void clickOnNextBtnCommunityPage()
	{
		clickUsingJS(element("btn_next_community"));
	}

	public ArrayList<String> getSelectedInterests() {

		int count=0;
		ArrayList<String> selectedInterests = new ArrayList<>();
		wait.waitForElementToBeVisible(element("tab_selections"));
		for(WebElement we: elements("list_selectedInterestTitles"))
		{
			selectedInterests.add(we.getText().trim());
			System.out.println("Removed interest "+count);
			count++;
		}
		
		return selectedInterests;
	}

}
