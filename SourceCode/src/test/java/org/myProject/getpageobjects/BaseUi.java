/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myProject.getpageobjects;

import static org.myProject.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static org.myProject.utils.DataPropertFileUtil.getProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Reporter.log;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.myProject.utils.SeleniumWait;
import org.myProject.utils.YamlReader;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class BaseUi {

	WebDriver driver, driverToUploadImage;
	protected SeleniumWait wait;
	private String pageName;
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int RANDOM_STRING_LENGTH = 5;
	protected String browser;

	protected BaseUi() {

	}

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		browser = (String)executeJavascript("return navigator.userAgent;");
		int timeout;
		if (System.getProperty("timeout") != null)
			timeout = Integer.parseInt(System.getProperty("timeout"));
		else
			timeout = Integer.parseInt(getProperty("Config.properties",
					"timeout"));
		this.wait = new SeleniumWait(driver, timeout);
		browser= (String)executeJavascript("return navigator.userAgent;");
	}

	protected BaseUi(WebDriver driver, WebDriver driverToUploadImage,
			String pageName) {
		PageFactory.initElements(driver, this);
		this.driverToUploadImage = driverToUploadImage;
		this.driver = driver;
		this.pageName = pageName;
		int timeout;
		if (System.getProperty("timeout") != null)
			timeout = Integer.parseInt(System.getProperty("timeout"));
		else
			timeout = Integer.parseInt(getProperty("Config.properties",
					"timeout"));
		this.wait = new SeleniumWait(driver, timeout);
	}

	protected String getPageTitle() {
		return driver.getTitle().trim();
	}

	public void logMessage(String message) {
		Reporter.setEscapeHtml(true);
		Reporter.log(message, true);
	}

	public String getCurrentURL() {
		hardWait(3);
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle);
	}

	public void verifyPageTitleExactD2l() {
		String pagetitle = getPageTitleFromFile(pageName);
		assertEquals(getPageTitle(), pagetitle,
				"Verifying Page title to validate right " + pageName + " - ");
		log("Assertion Passed: PageTitle for " + pageName + " is exactly: '"
				+ pagetitle + "'.");
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		wait.waitForPageTitleToContain(expectedPagetitle);
		Assert.assertEquals(getPageTitle(), expectedPagetitle,
				"Test Failed due to page title check on " + pageName);
		logMessage("Assertion Passed: PageTitle for " + pageName
				+ " is exactly: '" + expectedPagetitle + "'");
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 */
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	/**
	 * this method will get page title of current window and match it partially
	 * with the param provided
	 *
	 * @param expectedPagetitle
	 *            partial page title text
	 */
	protected void verifyPageTitleContains(String expectedPagetitle) {
		//wait.waitForPageTitleToContain(expectedPagetitle);
		String actualPageTitle = getPageTitle().trim();
		Assert.assertTrue(
				actualPageTitle.contains(expectedPagetitle),
				"Verifying Actual Page Title: '" + actualPageTitle
				+ "' contains expected Page Title : '"
				+ expectedPagetitle + "'.");
		logMessage("Assertion Passed: PageTitle for " + actualPageTitle
				+ " contains: '" + expectedPagetitle + "'.");
	}

	protected WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		hardWait(1);
	}

	protected Object executeJavascript(String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}

	protected String handleAlert() {
		String alertBoxText = null;
		try {
			alertBoxText = switchToAlert().getText();
			switchToAlert().accept();
			logMessage("Alert handled...");
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("No Alert window appeared...");
		}
		return alertBoxText;
	}

	protected Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void changeWindow(int i) {
		hardWait(1);
		Set<String> windows = driver.getWindowHandles();
		if (i > 0) {
			for (int j = 0; j < 9; j++) {
				System.out.println("Windows: " + windows.size());
				hardWait(1);
				if (windows.size() >= 2) {
					try {
						Thread.sleep(5000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					break;
				}
				windows = driver.getWindowHandles();
			}
		}
		String wins[] = windows.toArray(new String[windows.size()]);
		driver.switchTo().window(wins[i]);
		hardWait(1);
		System.out.println("Title: "
				+ driver.switchTo().window(wins[i]).getTitle());
	}

	// FIXME Remove hard Wait option
	protected void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	protected void hardWait(double seconds) {
		try {
			Thread.sleep((long) (seconds * 1000));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void closeWindow() {
		hardWait(1);
		driver.close();
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		wait.waitForElementToBeVisible(el);
		Select sel = new Select(el);
		sel.selectByVisibleText(text);
	}

	protected void selectByValueFromDropDown(WebElement el, String value) {
		wait.waitForElementToBeVisible(el);
		Select sel = new Select(el);
		sel.selectByValue(value);;
	}

	protected String verifySelectedTextInDropdown(WebElement el) {
		wait.waitForElementToBeVisible(el);
		Select sel = new Select(el);
		String value = sel.getFirstSelectedOption().getText();
		return value;
	}

	protected void scrollToTop() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-10000)");
	}

	protected void scrollToBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,10000)");
	}

	protected void scrollBy(String num) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + num + ")");
	}

	protected void scrollDown(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0, -100000)");
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
		jse.executeScript("window.scrollBy(0,-350)");
		hardWait(5);
	}

	protected void scroll(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void fillText(WebElement element, String text) {
		element.click();
		element.clear();
		element.sendKeys(text);
		logMessage("Filled '" + text + "' in Text Field");
	}

	protected void selectText(WebElement element) {
		Actions action = new Actions(driver);
		action.clickAndHold(element).build().perform();
		action.release().perform();
		action.clickAndHold(element).build().perform();
		action.moveToElement(element, 0, 0).build().perform();
		action.release().build().perform();
	}

	protected boolean areHtmlTagsDisplayed(WebElement el) {
		String text = el.getText();
		String pattern = "<\\w+?>\\w+?<\\/\\w+?>|<[\\w]+?\\/>";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(text);
		if (m.find())
			return true;
		return false;
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING : " + expected);
		logMessage("String compare Assertion passed.");
	}

	public void refreshPage() {
		driver.navigate().refresh();
		logMessage("Page refreshed by Webdriver");
	}

	public void reloadPage() {
		driver.findElement(By.xpath("//body")).sendKeys(Keys.F5);
		wait.waitForPageToLoadCompletely();
	}

	public void reloadPageUsingJs() {
		executeJavascript("location.reload()");
		logMessage("Page reloaded using Js");
	}

	public void clearCookies() {
		driver.manage().deleteAllCookies();
	}

	public void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			
			if(browser.contains("Chrome"))
			{
				new Actions(driver).click(element).build().perform();
				logMessage("Clicked element "+element);
			}
			else
			{
				element.click();
				logMessage("Clicked element "+element);
			}
		} catch (StaleElementReferenceException ex1) {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element
					+ " after catching Stale Element Exception");
		} catch (UnhandledAlertException u) {
			handleAlert();
			element.click();
			logMessage("Clicked element "+element+ " after handling alert");

		}
	}

	public void enterText(WebElement element, String text) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.clear();
			element.sendKeys(text);
		} catch (StaleElementReferenceException ex1) {
			scrollDown(element);
			element.clear();
			element.sendKeys(text);
			logMessage("Entered Text '" + text + "' in Element " + element
					+ " after catching Stale Element Exception");
		} catch (UnhandledAlertException u) {
			handleAlert();
			scrollDown(element);
			element.clear();
			element.sendKeys(text);
		}
	}

	public void hoverOverElement(WebElement ele) {
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(ele).build().perform();
	}

	protected void scrollDownPX(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scroll(0, -100000)");
		hardWait(1);
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
		hardWait(1);
		jse.executeScript("window.scrollBy(0,-350)");
	}

	protected void doubleClick(WebElement element) {
		  Actions act = new Actions(driver);
		  act.moveToElement(element).build().perform();
		  wait.hardWait(2);
		  act.doubleClick(element).build().perform();
		  logMessage("Step: Double clicked on element given");
	}

	protected boolean enterData(WebElement element, String text) {
		try {
			wait.waitForElementToBeVisible(element);
			element.click();
			element.clear();
			element.sendKeys(text);
			logMessage("Entered Text :" + text);
			return true;
		} catch (StaleElementReferenceException e) {
			wait.waitForElementToBeVisible(element);
			element.click();
			element.clear();
			element.sendKeys(text);
			logMessage("Data Entered after handling Stale Element");
			return true;
		} catch (Exception ex) {
			logMessage("In Catch Block: " + ex.getMessage());
			return false;
		}
	}

	public String getUniqueName(String name) {
		int i = YamlReader.generateRandomNumber(1, 25000);
		return name + String.valueOf(i);
	}

	protected String[] getLastNameFirstNameOfUser(String userName) {
		String[] user = userName.split(" ");
		return user;
	}

	/**
	 * This method generates random string
	 * 
	 * @return
	 */
	public String generateRandomString() {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * This method generates random numbers
	 * 
	 * @return int
	 */
	public int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	public void dragAndDropElement(WebElement fromElem, WebElement toElem) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(fromElem)
				.moveToElement(toElem).release(toElem).build();

		dragAndDrop.perform();
	}
	protected void holdExecution(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void switchWindow() {
		System.out.println("window size " + driver.getWindowHandles().size());
		int windowSize = driver.getWindowHandles().size();
		if (windowSize == 1) {
			holdExecution(4000);
		}
		for (String current : driver.getWindowHandles()) {
			driver.switchTo().window(current);
		}
	}
	
	public void clickUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		try {
			executor.executeScript("arguments[0].click();", element);
			Reporter.log("User clicked on element"+element);

		} catch (StaleElementReferenceException sre) {
			System.out.println("Stale Exeception handled.");
			wait.hardWait(1);

		}
	}

	public void closeCurrentWindow() {
	
		driver.close();
		
	}
	public void switchToMainWindow(String mainWindow) {
		driver.switchTo().window(mainWindow);
	}
	
	public void isFileDownloadedwithExtension(int attempt ,String ext) {
		hardWait(5);
		
		boolean flag = false;
		String downloadPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles";
		wait.waitTillFileGetDownload(attempt, downloadPath);
		File dir = new File(downloadPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			flag = false;
		}
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getName();
			logMessage("Downloaded file: " + files[i].getName());
			if (files[i].getName().contains(ext)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag, "[Assertion Fail]: File with extension " + ext + " is not saved ");
		logMessage("[Assertion Pass]: File with extension " + ext + " is saved");
	}
	

	public void hoverAndClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}
	
	public void navigateToPreviousPage() {
		driver.navigate().back();
		wait.waitForPageToLoad();
	}
	
	public void clearFieldAndSendData(WebElement el , String data){
		wait.waitForElementToBeVisible(el);
		clickUsingJS(el);
		el.clear();
//		el.sendKeys(Keys.ENTER);
		el.sendKeys(data);
		logMessage("[INFO]: Values Entered: "+ data);
	}
}