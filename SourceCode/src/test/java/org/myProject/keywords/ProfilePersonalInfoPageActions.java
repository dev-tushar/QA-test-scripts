package org.myProject.keywords;

import static org.myProject.utils.CustomFunctions.generateUniqueDocTitle;

import java.util.ArrayList;

import org.myProject.getpageobjects.GetPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProfilePersonalInfoPageActions extends GetPage{
	
	String docTitle= generateUniqueDocTitle();
	ArrayList<String> pubs = new ArrayList<>();


	public ProfilePersonalInfoPageActions(WebDriver driver) {
		super(driver, "Profile-PersonalInfoPage");
	}
	
	
	public String getClaimedPaperCount()
	{
		 scroll(element("link_manualAdd"));
		 hardWait(2);
		 System.out.println("!@@@@@@"+element("txt_claimedPaperCount").getText().trim());
		 return element("txt_claimedPaperCount").getText().trim();
	}
	
	public String createJournalArticleManually()
	{
		scroll(element("link_manualAdd"));
		clickUsingJS(element("link_manualAdd"));
		String docTitle = generateUniqueDocTitle()+"_journal";
		element("input_docTitle").sendKeys(docTitle);
		element("input_pubdate").sendKeys("2018-08-21");
		element("input_pauthor").sendKeys("Automation_pauthor");
		element("input_cauthor").sendKeys("Automation_cauthor");
		element("input_pubtitle").sendKeys("Automation_pubtitle");
		element("input_volume").sendKeys("Automation_volume1");
		element("input_issue").sendKeys("Automation_issue1");
		element("input_year").sendKeys("2018");
		element("input_pubtitle").sendKeys("Automation_pubtitle");
		element("input_startPage").sendKeys("automation_00");
		element("input_endPage").sendKeys("automation_100");
		scroll(element("btn_create"));
		click(element("ddn_publicationType"));
		selectProvidedTextFromDropDown(element("ddn_publicationType"), "Journal Article");
		click(element("btn_create"));
		return docTitle;
	}
	
	public String createConferenceArticleManually()
	{
		scroll(element("link_manualAdd"));
		clickUsingJS(element("link_manualAdd"));
		String docTitle = generateUniqueDocTitle()+"_conference";
		element("input_docTitle").sendKeys(docTitle);
		element("input_pubdate").sendKeys("2018-08-21");
		element("input_pauthor").sendKeys("Automation_pauthor");
		element("input_cauthor").sendKeys("Automation_cauthor");
		element("input_pubtitle").sendKeys("Automation_pubtitle");
		element("input_volume").sendKeys("Automation_volume1");
		element("input_issue").sendKeys("Automation_issue1");
		element("input_year").sendKeys("2018");
		element("input_pubtitle").sendKeys("Automation_pubtitle");
		element("input_startPage").sendKeys("automation_00");
		element("input_endPage").sendKeys("automation_100");
		scroll(element("btn_create"));
		click(element("ddn_publicationType"));
		selectProvidedTextFromDropDown(element("ddn_publicationType"), "Conference Article");
		click(element("btn_create"));
		return docTitle;
	}
	
	public String editJournalArticles()
	{
		 scroll(element("link_manualAdd"));
		 hardWait(2);
		 click(element("tab_claimedPapers"));
		 click(elements("icons_edit").get(1));
		 click(elements("ddn_editddown").get(0));
		 wait.waitForMsgToastToDisappear();
		 element("input_docTitle").clear();
		 String docTitleEdit = generateUniqueDocTitle()+"_journal_edited";
		 element("input_docTitle").sendKeys(docTitleEdit);
		 element("input_pubtitle").clear();
   		 element("input_pubtitle").sendKeys("Automation_pubtitle_edit");
 		 scroll(element("btn_save"));
 		 clickUsingJS(element("btn_save"));
 		 return docTitleEdit;

	}
	
	public ArrayList<String> getPublicationsFromProfilePage()
	{
		for(WebElement we: elements("list_publications"))
		{
			pubs.add(we.getText().trim());
		}
		
		return pubs;
	}
	
	public ArrayList<String> getDocTitlesFromEditProfilePage()
	{
		pubs.removeAll(pubs);
		for(WebElement we: elements("list_profile_docTitles"))
		{
			pubs.add(we.getText().trim());
		}
		
		return pubs;
	}
	
	public void removeJournalFromProfile()
	{
		click(element("icon_journalEdit"));
		click(elements("ddn_editddown").get(1));
		wait.waitForElementToBeClickable(element("btn_confirmRemove"));
		click(element("btn_confirmRemove"));
	}


	public boolean verifyJournalRemovedFromClaimedPapers() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void removeConferenceFromProfileAndLibrary()
	{
		click(element("icon_journalEdit"));
		click(elements("ddn_editddown").get(2));
		wait.waitForElementToBeClickable(element("btn_confirmRemove"));
		click(element("btn_confirmRemove"));
	}


	public boolean verifyConferenceRemovedFromClaimedPapers() {
		// TODO Auto-generated method stub
		return true;
	}


	public boolean verifyConferenceRemovedFromLibrary() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
}
