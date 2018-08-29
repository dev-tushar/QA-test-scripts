/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myProject;

import static org.myProject.utils.DataPropertFileUtil.getProperty;
import static org.myProject.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.myProject.utils.SeleniumWebDriverEventListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestSessionInitiator {

	protected WebDriver driver, originalDriver;
	private WebDriverFactory wdfactory;
	Map<String, Object> chromeOptions = null;
	protected static String product;
	Properties props = new Properties();

	public TestSessionInitiator() {
		wdfactory = new WebDriverFactory();
		props.setProperty("product", "Collabratec");
	}

	protected void configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		int timeout;
		if (System.getProperty("timeout") != null)
			timeout = Integer.parseInt(System.getProperty("timeout"));
		else
			timeout = Integer.parseInt(_getSessionConfig().get("timeout"));
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		originalDriver = driver;
		EventFiringWebDriver efwd = new EventFiringWebDriver(driver);
		SeleniumWebDriverEventListener myListener = new SeleniumWebDriverEventListener(driver);
		efwd.register(myListener);
		driver = efwd;
	}

	private static Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "timeout", "operatingSystem", "wDriverpathIE", "wDriverpathChrome",
				"wDriverpathFirefox", "driverpathFirefox", "driverpathChrome", "otherFilesPath", "screenshot-path" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			config.put(string, getProperty("./Config.properties", string));
		}
		return config;
	}

	public static String getEnv() {
		String tier = System.getProperty("env");
		if (tier == null)
			tier = _getSessionConfig().get("tier");
		return tier;
	}

	public static String getDatabaseURL() {
		return _getSessionConfig().get("dbURL");
	}

	public static String getDatabaseName() {
		return _getSessionConfig().get("databaseName");
	}

	public static String getDbUsername() {
		return _getSessionConfig().get("user");
	}

	public static String getDbUserPassword() {
		return _getSessionConfig().get("password");
	}

	public String getDebugObjects() {
		return _getSessionConfig().get("debugObjects");
	}

	public String getBrowser() {
		String browser = System.getProperty("browser");
		if (browser == null)
			browser = _getSessionConfig().get("browser");
		return browser;
	}

	public String getUploadScreenshotToFtp() {
		return _getSessionConfig().get("uploadImage");
	}

	public String getTakeScreenshot() {
		return _getSessionConfig().get("takeScreenshot");
	}

	public String getAutoITScriptPath() {
		return _getSessionConfig().get("autoITPath");
	}

	public static String getProduct() {
		if (System.getProperty("product") != null)
			product = System.getProperty("product");
		return product;
	}

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String applicationpath) {
		Reporter.log("The application url is :- " + applicationpath, true);
		Reporter.log("The test browser is :- " + getBrowser(), true);
		driver.get(applicationpath);
	}

	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		driver.get(url);
	}

	public void closeBrowserSession() {
		driver.quit();
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName.toUpperCase() + " *****", true);
		Reporter.log(" ", true);
	}

	public void waitForPageLoad() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void printMethodExecutionTime(long startTime, long endTime) {
		long totalExecutionTimeInSeconds = (endTime - startTime) / 1000;
		long hours = totalExecutionTimeInSeconds / 3600;
		long minutes = (totalExecutionTimeInSeconds % 3600) / 60;
		long seconds = totalExecutionTimeInSeconds % 60;

		String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		Reporter.log("\n---------- METHOD EXECUTION TIME: " + timeString + " ----------\n", true);
	}

	public void closeWindow() {
		driver.close();
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void stepFailedMessage(ITestResult result) {
		Reporter.log("***STEP FAILED****: " + result.getThrowable().getMessage() + "", true);
		Reporter.log(" ", true);
	}

}
