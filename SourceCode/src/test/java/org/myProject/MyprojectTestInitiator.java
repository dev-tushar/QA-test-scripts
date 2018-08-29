package org.myProject;

import static org.myProject.utils.YamlReader.setYamlFilePath;

import org.myProject.getpageobjects.GetPage;
import org.myProject.keywords.AddCommunitiesPageActions;
import org.myProject.keywords.CreateAccountFormPage;
import org.myProject.keywords.CreateInterestPageActions;
import org.myProject.keywords.LibraryPageActions;
import org.myProject.keywords.MarketingPageActions;
import org.myProject.keywords.ProfilePersonalInfoPageActions;
import org.myProject.keywords.SignInPageActions;
import org.myProject.keywords.UserProfilePageActions;
import org.myProject.keywords.WelcomeToCollabratecPageActions;
import org.myProject.keywords.userHomePageActions;
import org.myProject.utils.CustomFunctions;
import org.myProject.utils.DateUtil;
import org.myProject.utils.TakeScreenshot;

public class MyprojectTestInitiator extends TestSessionInitiator{

	public CustomFunctions customFunctions;
	public TakeScreenshot takescreenshot;
    public MarketingPageActions allPARsProjectPage;
    public DateUtil date;
    public CreateAccountFormPage createaccountPage;
    public WelcomeToCollabratecPageActions	welcomepage;
    public CreateInterestPageActions createInterestPage;
    public AddCommunitiesPageActions addCommunitiesPage;
    public userHomePageActions userHomePage;
    public SignInPageActions signInPage;
    public UserProfilePageActions userProfilePage;
    public ProfilePersonalInfoPageActions profilePage;
    public LibraryPageActions libraryPage;
    
    /****************Collabratec Keywords*******************************/
    
    public MarketingPageActions marketingPage;
	
	private void _initPage() {
		customFunctions = new CustomFunctions(driver);
		allPARsProjectPage = new MarketingPageActions(driver);
		marketingPage = new MarketingPageActions(driver);
		createaccountPage = new CreateAccountFormPage(driver);
		welcomepage = new WelcomeToCollabratecPageActions(driver);
		createInterestPage = new CreateInterestPageActions(driver);
		addCommunitiesPage = new AddCommunitiesPageActions(driver);
		userHomePage = new userHomePageActions(driver);
		signInPage = new SignInPageActions(driver);
		userProfilePage = new UserProfilePageActions(driver);
		profilePage = new ProfilePersonalInfoPageActions(driver);
		libraryPage = new LibraryPageActions(driver);
		
		
		date= new DateUtil();
		
	}

	
	public MyprojectTestInitiator(String testname) {
		super();
		setProduct();
		setYamlFilePath();
		configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	public void setProduct(){
		product = "Collabratec";
		CustomFunctions.setProduct(product);
		GetPage.setProduct(product);
	}
}

