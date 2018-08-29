package org.myProject.getpageobjects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.myProject.getpageobjects.ObjectFileReader.getELementFromFile;
import static org.myProject.utils.ConfigPropertyReader.getProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.myProject.utils.ConfigPropertyReader;
import org.myProject.utils.SeleniumWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class GetPage extends BaseUi {

	protected WebDriver webdriver;
	String pageName;
	static String product;
	
	protected SeleniumWait wait;
	int x = 10;

	public static void setProduct(String productName) {
		product = productName;
	}
	
	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
	}

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId.trim()));
		}
	}

	protected WebElement element(String elementToken) {
		return element(elementToken, "");
	}
	
	protected WebElement hiddenElement(String elementToken) {
		return hiddenElement(elementToken, "");
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2)
			throws NoSuchElementException {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(
					webdriver.findElement(getLocator(elementToken, replacement1, replacement2)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element " + elementToken + " not found on the " + this.pageName + " !!!");
		}

		printData(elem.toString());
		return elem;
	}

	protected WebElement element(String elementToken, String replacement) {
		WebElement elem = null;
		try {
			elem = wait.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken, replacement)));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element '" + elementToken + "' not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement hiddenElement(String elementToken, String replacement) {
		WebElement elem = null;
		try {
			elem = webdriver.findElement(getLocator(elementToken, replacement));
		} catch (NoSuchElementException excp) {
			fail("FAILED: Element '" + elementToken + "' not found on the " + this.pageName + " !!!");
		}
		return elem;
	}
	protected boolean checkIfElementIsNotThere(String eleString) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}

	protected boolean checkIfElementIsNotThere(String eleString, String replacement) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString, replacement)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString, String replacement) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString, replacement)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(webdriver.findElements(getLocator(elementToken, replacement)));
	}

	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return webdriver.findElements(getLocator(elementToken, replacement1, replacement2));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected void _waitForElementToDisappear(String elementToken, String replacement) {
		int i = 0;
		int initTimeout = wait.getTimeout();
		wait.resetImplicitTimeout(2);
		int count;
		while (i <= 20) {
			if (replacement.isEmpty())
				count = elements(elementToken).size();
			else
				count = elements(elementToken, replacement).size();
			if (count == 0)
				break;
			i += 2;
		}
		wait.resetImplicitTimeout(initTimeout);
	}

	protected void waitForElementToDisappear(String elementToken) {
		_waitForElementToDisappear(elementToken, "");
	}

	protected void waitForElementToDisappear(String elementToken, String replacement) {
		_waitForElementToDisappear(elementToken, replacement);
	}

	protected void isStringMatching(String actual, String expected) {
		logMessage("Comparing 2 strings");
		logMessage("EXPECTED STRING :" + expected);
		logMessage("ACTUAL STRING :" + actual);
		Assert.assertEquals(actual, expected, "The strings does not match!!!");
		logMessage("String compare Assertion passed.");
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace) {
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		//assertTrue(result,"TEST FAILED: element '" + elementName + "with text " + elementTextReplace + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace + " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText().trim(), expectedText,
				"TEST FAILED: element '" + elementName + "' Text is not as expected: ");
		logMessage("TEST PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat("TEST FAILED: element '" + elementName + "' Text is not as expected: ",
				element(elementName).getText().trim(), containsString(expectedText));
		logMessage("TEST PASSED: element " + elementName + " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName) {
		wait.waitForPageToLoadCompletely();
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = element(elementName).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayedByWebElement(WebElement el) {
		wait.waitForElementToBeVisible(el);
		boolean result = el.isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + el + "' is not displayed.");
		// logMessage("TEST PASSED: element " + el + " is displayed.");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName) {
		boolean result;
		result = checkIfElementIsNotThere(elementName);
		assertTrue(result, "Assertion Failed: element '" + elementName + "' is displayed which should not be there");
		logMessage("Assertion Passed: element " + elementName
				+ " is not displayed after waiting for 10 seconds on the page as expected!!!");
		return result;
	}

	protected boolean isElementNotDisplayed(String elementName, String elementTextReplace) {
		boolean result;
		try {
			wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
			driver.findElement(getLocator(elementName, elementTextReplace));
			result = false;
		} catch (NoSuchElementException excp) {
			result = true;
		}
		assertTrue(result, "Assertion Failed: element '" + elementName + "' with text " + elementTextReplace
				+ " is displayed as expected");
		logMessage("Assertion Passed: element " + elementName + " with text " + elementTextReplace
				+ "is not displayed as expected!!!");
		return result;
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "TEST FAILED: element '" + elementName + "' is  ENABLED :- " + !expected);
		logMessage("TEST PASSED: element " + elementName + " is enabled :- " + expected);
		return result;
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);

		printData(locator[2].trim());
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private void printData(String trim) {
		// System.out.println(trim);

	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	public void clickOnFirstLinkBasedOnProvidedText(String elementToken, String linkText) {
		element(elementToken, linkText).click();
	}

	public boolean matchGivenPatternWithProvidedText(String pattern, String text) {
		Matcher matcher = Pattern.compile(pattern).matcher(text);
		return matcher.matches();
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);

		case xpath:
			// logMessage(locatorValue);
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	public void redirectionToPubsTestEnvironment() {
		String baseURl = null;
		/*
		 * if
		 * (ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Staging")
		 * || ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Stag"))
		 */
		String Tier = ObjectFileReader.tier;
		if (Tier.equalsIgnoreCase("STAGING/")) {
			String currentURL = getCurrentURL();
			logMessage("DEBUG : " + currentURL);
			if (!(currentURL.contains("pubstest.acs")) && (currentURL.contains("pubs.acs"))) {
				baseURl = currentURL.split(".org")[0];
				logMessage("DEBUG : " + baseURl);
				currentURL = currentURL.replaceAll(baseURl, "http://master-pubstest.acs");
				driver.get(currentURL);
				wait.waitForPageToLoad();
				logMessage("User is redirected to pubstest environment after landing on pubs - a known issue");
			}
		} else {
			logMessage("Script not running on pubstest, no need for any redirections");
		}
	}

	public void redirectionToPubsTestBooksEnvironment() {
		String baseURl = null;
		/*
		 * if
		 * (ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Staging")
		 * || ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Stag"))
		 */
		String Tier = ObjectFileReader.tier;
		if (Tier.equalsIgnoreCase("STAGING/")) {
			String currentURL = getCurrentURL();
			logMessage("DEBUG : " + currentURL);
			if (!(currentURL.contains("pubstest.acs")) && (currentURL.contains("pubs.acs"))) {
				baseURl = currentURL.split(".org")[0];
				logMessage("DEBUG : " + baseURl);
				currentURL = currentURL.replaceAll(baseURl, "http://pubstestbooks.acs");
				driver.get(currentURL);
				wait.waitForPageToLoad();
				logMessage("User is redirected to pubstestbooks environment after landing on pubs - a known issue");
			}

		} else {
			logMessage("Script not running on pubstest, no need for any redirections");
		}
	}

	public void redirectToACSPubsTestEnvironment() {
		/*
		 * if
		 * (ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Staging")
		 * || ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Stag"))
		 */
		String Tier = ObjectFileReader.tier;

		String baseURl = null;
		if (Tier.equalsIgnoreCase("STAGING/")) {
			String currentURL = getCurrentURL();
			logMessage("DEBUG : " + currentURL);
			if (!(currentURL.contains("acschemworxtest.acs"))) {
				baseURl = currentURL.split(".org")[0];
				logMessage("DEBUG : " + baseURl);
				currentURL = currentURL.replaceAll(baseURl, "https://pc.acschemworxtest.acs");
				driver.get(currentURL);
				logMessage("User is redirected to pubstest environment after landing on pubs - a known issue");
			}

		} else {
			logMessage("Script not running on pubstest, no need for any redirections");
		}
	}

	public String createRedirectionToPubsTestEnvironment(String currentURL) {
		String Tier = ObjectFileReader.tier;
		String baseURl = null;

		if (Tier.equalsIgnoreCase("STAGING/")) {
			logMessage("DEBUG : " + currentURL);
			if (!(currentURL.contains("pubstest.acs")) && (currentURL.contains("pubs.acs"))) {
				baseURl = currentURL.split(".org")[0];
				logMessage("DEBUG : " + baseURl);
				currentURL = currentURL.replaceAll(baseURl, "http://master-pubstest.acs");
				logMessage("User is redirected to pubstest environment after landing on pubs - a known issue");
				return currentURL;
			}

		} else {
			logMessage("Script not running on pubstest, no need for any redirections");
		}
		return currentURL;
	}

	public void redirectToACSAccountTestEnvironment() {
		String Tier = ObjectFileReader.tier;
		String baseURl = null;
		if (Tier.equalsIgnoreCase("STAGING/")) {
			String currentURL = getCurrentURL();
			logMessage("DEBUG : " + currentURL);
			if (!(currentURL.contains("accounttest.acs.org"))) {
				baseURl = currentURL.split(".org")[0];
				logMessage("DEBUG : " + baseURl);
				currentURL = currentURL.replaceAll(baseURl, "https://accounttest.acs");
				driver.get(currentURL);
				wait.waitForPageToLoad();
				logMessage(
						"User is redirected to accounttest (pubstest) environment after landing on pubs - a known issue");
			}

		} else {
			logMessage("Script not running on pubstest, no need for any redirections");
		}
	}

	protected WebElement getElement(String elementToken, String replacement1, String replacement2) {
		WebElement elem = null;
		elem = wait
				.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken, replacement1, replacement2)));
		return elem;
	}

	protected WebElement getElement(String elementToken) throws NoSuchElementException {
		WebElement elem = null;
		elem = wait.waitForElementToBeVisible(webdriver.findElement(getLocator(elementToken)));
		return elem;
	}

	protected boolean isElementDisplayed(String elementName, String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1, elementTextReplace2).isDisplayed();
		assertTrue(result, "TEST FAILED: element '" + elementName + "with text " + elementTextReplace1
				+ elementTextReplace2 + "' is not displayed.");
		logMessage("TEST PASSED: element " + elementName + " with text " + elementTextReplace1 + elementTextReplace2
				+ " is displayed.");
		return result;
	}

	public void openUrl(String url) {
		driver.get(url);
	}



	public boolean waitForAValueToAppearForAnElementBasedOnGivenTime(String element, String expectedValue,
			int maxTimeInSeconds) {
		int i;
		boolean flag = false;
		wait.waitForPageToLoadCompletely();

		for (i = 1; i <= maxTimeInSeconds; i++) {
			if (element(element).getText().trim().equalsIgnoreCase(expectedValue)) {
				flag = true;
				break;
			} else {
				wait.hardWait(1);
			}
		}

		return flag;
	}

	protected boolean checkIfElemenWithGivenTextIsNotThere(String eleString, String textToBeReplaced) {
		boolean flag = false;
		try {
			if (webdriver.findElement(getLocator(eleString, textToBeReplaced)).isDisplayed()) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (NoSuchElementException ex) {
			flag = true;
		}
		return flag;
	}
	
	/********* Get XPath from file **********/
	 public String getXPathFromFile(String elementToken) {
	  String[] locator = getELementFromFile(this.pageName, elementToken);
	  return locator[2].trim();
	 }
	 public String getXPathFromFile(String elementToken, String replacement) {
	  String[] locator = getELementFromFile(this.pageName, elementToken);
	  locator[2] = locator[2].replaceAll("\\$\\{.+?\\}", replacement);
	  return locator[2].trim();
	 }
	 public String getXPathFromFile(String elementToken, String replacement1, String replacement2) {
	  String[] locator = getELementFromFile(this.pageName, elementToken);
	  locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
	  locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
	  return locator[2].trim();
	 }

}
