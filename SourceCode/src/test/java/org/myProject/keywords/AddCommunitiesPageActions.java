package org.myProject.keywords;

import static org.myProject.utils.CustomFunctions.generateUniqueEmailId;

import java.util.ArrayList;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddCommunitiesPageActions extends GetPage {
	
	WebDriver driver;
	YamlInformationProvider yamlvalue = new YamlInformationProvider();
	String useremail = generateUniqueEmailId();
	int count=0,count1=0;
	ArrayList<String> selectedCommunities = new ArrayList<>();

	public AddCommunitiesPageActions(WebDriver driver) {
		super(driver, "AddCommunitiesPage");
		this.driver = driver;
	}
	
	public void addCommunities()
	{
		wait.waitForPageToLoadCompletely();
		for(WebElement we: elements("list_communities"))
		{
			count++;
			clickUsingJS(we);
			if (count == 3)
				break;
		}
		
		for(WebElement we: elements("list_communities_titles"))
		{
			count1++;
			clickUsingJS(we);
			selectedCommunities.add(we.getText().trim());

			if (count1 == 3)
				break;
		}
	}

	public ArrayList<String> getselectedCommunities() {
		
		return selectedCommunities;
	}

}
