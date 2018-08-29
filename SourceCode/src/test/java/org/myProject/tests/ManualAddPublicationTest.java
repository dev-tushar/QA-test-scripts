package org.myProject.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.myProject.keywords.YamlInformationProvider;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

//@Listeners(MyListener.class)
public class ManualAddPublicationTest extends BaseTest{
	
	YamlInformationProvider yamlData;
	String userName, password, url, userPrefix, emailID, newUserName;
	String deliveryOption, studentFirstname, studentLastName, address, city, zip, dOB, state, studentEmailId,
			phoneNumber, gender;
	String transcriptName, userOrganization;
	int initalLeadCount;
	ArrayList<String> docs = new ArrayList<>();
	List<String> librarylist = null;
	List<String> docslist = null;
	
	

	public void initTestData() {
		url = yamlData.getApplicationURL();
		
	}


	@Test
	public void Step_001_LaunchCollabratecSignInPage()
	{
		collabratec.marketingPage.clickOnSignIn();

	}
	
	@Test
	public void Step_002_SignInToUser()
	{
		collabratec.signInPage.enterCredentials();
		Assert.assertTrue(collabratec.signInPage.getUsername().contains("Kathy"), "Username not as expected");
		Reporter.log("User successfully signed in to his/her account");
	
	}
	
	@Test
	public void Step_003_NavigateToAddPublication() {
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.navigateToEditProfile();
		collabratec.userHomePage.navigateToAddPublication();
		collabratec.userHomePage.verifyUserOnEditProfile();

	}
	
	@Test
	public void Step_004_AddandVerifyJournalPublication() {
		String paperCountBefore = "0";
		String docj = collabratec.profilePage.createJournalArticleManually();
		String papercountAfter = collabratec.profilePage.getClaimedPaperCount();
		Assert.assertFalse(paperCountBefore.equalsIgnoreCase(papercountAfter), "Claimed paper count did not change on adding Journal article");
		logIt("New journal publication added successfully");
	}
	
	@Test
	public void Step_005_AddandVerifyConferencePublication() throws InterruptedException {
		String paperCountBefore = collabratec.profilePage.getClaimedPaperCount();
		String docc = collabratec.profilePage.createConferenceArticleManually();
		Thread.sleep(4000);
		String papercountAfter = collabratec.profilePage.getClaimedPaperCount();
		Assert.assertFalse(paperCountBefore.equalsIgnoreCase(papercountAfter), "Claimed paper count did not change on  adding Conference article");
		logIt("New conference publication added successfully");
		docs.add(docc);
	}
	
	@Test
	public void Step_006_EditAndVerifyJournalPublication() throws InterruptedException {
		String paperCountBefore = collabratec.profilePage.getClaimedPaperCount();
		String doctitleedit = collabratec.profilePage.editJournalArticles();
		Thread.sleep(4000);
		String papercountAfter = collabratec.profilePage.getClaimedPaperCount();
		Assert.assertTrue(paperCountBefore.equalsIgnoreCase(papercountAfter), "Claimed paper count changed on editing journal article");
		logIt("Edit journal publication functionality verified successfully");
		docs.add(doctitleedit);
	}
	
	@Test
	public void Step_007_VerifyPublicationsUnderLibrary() throws InterruptedException {
	
		collabratec.userHomePage.navigateToSideMenuItem("Library");
		collabratec.libraryPage.navigateToLibrarySettings();
		collabratec.libraryPage.navigateToMyDocuments();
		librarylist = collabratec.libraryPage.getDocumentTitles();
		docslist = docs;
		Collections.sort(librarylist);
		Collections.sort(docslist);
		System.out.println("Listtttttt1"+librarylist);
		System.out.println("Listtttttt2"+docslist);
		Assert.assertTrue(librarylist.equals(docslist),"All created publications not found under My Documents");
		logIt("All publications are correctly listed under My Documents");
		
		
	}
	
	@Test
	public void Step_008_VerifyPublicationsUnderMyPublications()
	{
		collabratec.libraryPage.clickOnMyPublicationFilter();
		librarylist = collabratec.libraryPage.getDocumentTitles();
		Collections.sort(librarylist);
		Assert.assertTrue(librarylist.equals(docslist),"All created publications not found under My Documents");
		logIt("All publications are correctly listed under My Publications filter");
	}
	
	/*@Test
	public void Step_009_VerifyPublicationsUnderProfile()
	{
		boolean flag = false;
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.navigateToEditProfile();
		ArrayList<String> profilePubsList = collabratec.profilePage.getPublicationsFromProfilePage();
		for(String str2: docslist)
		{
			System.out.println("Checking for publication-->"+str2);
			for(String str: profilePubsList)
			{
				if(str.contains(str2))
				{
					logIt("Publication "+str2+" found on user profile page");
					 flag = true;
				}
			}
			if(flag==false)
			{
				logIt("Publication "+str2+" not found on user profile page");
			}
		}
	}
	
	@Test
	public void Step_010_VerifyRemoveJournalFromProfile()
	{
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.navigateToEditProfile();
		collabratec.profilePage.removeJournalFromProfile();
		Assert.assertTrue(collabratec.profilePage.verifyJournalRemovedFromClaimedPapers(), "Journal not removed from claimed papers");
		logIt("Journal removed from claimed papers");
		
	}
	
	@Test
	public void Step_011_verifyRemoveConferenceFromProfileAndLibrary()
	{
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.navigateToEditProfile();
		collabratec.profilePage.removeConferenceFromProfileAndLibrary();
		Assert.assertTrue(collabratec.profilePage.verifyConferenceRemovedFromClaimedPapers(), "Conference not removed from claimed papers");
		logIt("Conference removed from claimed papers");

	}
	
	@Test
	public void Step_012_verifyPublicationsRemoved()
	{
		Assert.assertTrue(collabratec.profilePage.verifyConferenceRemovedFromLibrary(), "Conference not removed from library");

	}
	
	@Test
	public void Step_013_Signout()
	{
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.signOut();
		Assert.assertTrue(collabratec.marketingPage.getLogOutText().contains("You have now been signed out of IEEE Collabratec"), "User did not log out successfully");
		System.out.println("User logged out successfully");
	}
	*/
	
}

