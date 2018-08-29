package org.myProject.tests;

import java.util.ArrayList;

import org.myProject.keywords.YamlInformationProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAccountTest extends BaseTest {

	YamlInformationProvider yamlData;
	String userName, password, url, userPrefix, emailID, newUserName;
	String deliveryOption, studentFirstname, studentLastName, address, city, zip, dOB, state, studentEmailId,
			phoneNumber, gender;
	String transcriptName, userOrganization;
	int initalLeadCount;
	ArrayList<String> selectedInterests = new ArrayList<>();
	ArrayList<String> selectedCommunities = new ArrayList<>();


	public void initTestData() {
		url = yamlData.getApplicationURL();
		/*
		 * userName = yamlData.getUserCredentialForLPP("user3"); password =
		 * yamlData.getUserCredentialForLPP("password3"); emailID =
		 * yamlData.getUserCredentialForLPP("eamilID"); userPrefix =
		 * yamlData.getUserCredentialForLPP("user3Prefix"); userOrganization =
		 * yamlData.getUserCredentialForLPP("user3org");
		 */
	}

	@Test
	public void Step_001_LaunchSignInPage() {
		collabratec.marketingPage.clickOnCreateAccount();

	}

	@Test
	public void Step_002_FillUserInformation() {
		collabratec.createaccountPage.enterUserInformation();
		collabratec.createaccountPage.clickOnAcceptTerms();
		collabratec.createaccountPage.clickOnCreateAccount();
		collabratec.welcomepage.clickOnAgreeButton();
		Assert.assertTrue(collabratec.createInterestPage.verifyInterestPageIsDisplayed(), "Create Interest page not displayed");
		
	}

	@Test
	public void Step_003_VerifyInterestAddingAndRemoval() {
		collabratec.createInterestPage.selectInterestsViaSearchTab();
		String interestAfterSearch = collabratec.createInterestPage.getInterestSelectionNumber();
		collabratec.createInterestPage.selectInterestsViaBrowseTab();
		String interestAfterBrowse = collabratec.createInterestPage.getInterestSelectionNumber();
		Assert.assertFalse(interestAfterSearch.equals(interestAfterBrowse), "Interest count is not changing after adding interests");
		collabratec.createInterestPage.clickonSelectionsTab();
		selectedInterests = collabratec.createInterestPage.getSelectedInterests();
//	//	collabratec.createInterestPage.removeSelectedInterests();
//		collabratec.createInterestPage.selectInterestsViaSearchTab();
//		collabratec.createInterestPage.selectInterestsViaBrowseTab();
		collabratec.createInterestPage.clickOnNextButn();
	}

	@Test
	public void Step_004_VerifyAddCommunities() {
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.addCommunitiesPage.addCommunities();
		selectedCommunities = collabratec.addCommunitiesPage.getselectedCommunities();
		collabratec.createInterestPage.clickOnNextBtnCommunityPage();

	}

	@Test
	public void Step_005_VerifySignOut() {
		collabratec.userHomePage.navigateToUserProfile();
		collabratec.userHomePage.signOut();
		Assert.assertTrue(collabratec.marketingPage.getLogOutText().contains("You have now been signed out of IEEE Collabratec"), "User did not log out successfully");
		System.out.println("User logged out successfully");
	}

}
