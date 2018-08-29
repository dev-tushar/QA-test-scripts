package org.myProject.tests;

import static org.myProject.utils.YamlReader.getYamlValue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.myProject.MyprojectTestInitiator;
import org.myProject.TestSessionInitiator;
import org.myProject.utils.ALMInfrastructure.TestNGLog;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ org.myProject.tests.CustomReportCreator.class })

public class BaseTest {
	protected boolean isTestRunCreated = false;
	protected MyprojectTestInitiator collabratec;
	protected TestSessionInitiator test;
	protected String product;
	protected int counterForTests;
	protected int failCount;
	WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	// public Log reportFile = null;
	public String className = this.getClass().getSimpleName();
	// public String reportFileLoc = ConfigPropertyReader.getProperty("sLogPath");

	@BeforeClass
	public void beforeMethod() {
		collabratec = new MyprojectTestInitiator(this.getClass().getSimpleName());
		this.driver = collabratec.getDriver();
		collabratec.launchApplication(getYamlValue("baseUrl"));
		counterForTests = 0;
		failCount = 0;

		// reportFile = new Log(className,
		// ConfigPropertyReader.getProperty("sLogPath"));
		// runner.setOutputDirectory("C://Automation Log");
		// createFolder(System.getProperty("user.dir")+File.separator+"ALMLog");
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		// collabratec.stepStartMessage(method.getName());

	}

	@AfterMethod
	public void afterMethod(ITestResult result, Method method) throws IOException {
		/*
		 * if (result.getStatus() == ITestResult.FAILURE) {
		 * MyProject.customFunctions.takeScreenshot("Screenshots", testName); } if
		 * (result.getStatus() == ITestResult.FAILURE) { failCount = failCount + 1; }
		 */

		counterForTests = collabratec.takescreenshot.incrementcounterForTests(counterForTests, result);
		collabratec.takescreenshot.takeScreenShotOnException(result, counterForTests, method.getName());
		if (result.getStatus() == ITestResult.FAILURE) {
			failCount = failCount + 1;
		}

		/*
		 * try {
		 * 
		 * if (result.getStatus() == ITestResult.SUCCESS) { reportFile.write("",
		 * method.getName(), "", "Pass"); reportFile.save(); } else {
		 * //test.stepFailedMessage(result); reportFile.write("", method.getName(),
		 * result.getThrowable().getMessage(),"Fail");
		 * reportFile.takeSnapshot(test.getDriver()); reportFile.save(); if
		 * (method.getName().equals("Step_01_Launch_Application") ||
		 * method.getName().equals("Step_02_Sign_Into_Application") ) { Assert.fail(); }
		 * 
		 * } } catch (Exception e) { reportFile.write("", method.getName(),
		 * result.getThrowable().getMessage(),"Fail"); reportFile.save();
		 * collabratec.stepFailedMessage(result); }
		 */
	}

	@AfterClass(alwaysRun = true)
	public void close_Test_Session() throws IOException {

		// this method writes whether the test case as a whole passed or failed
		// and
		// report to ALM
		tearDown(Reporter.getCurrentTestResult(), this.getClass().getSimpleName());
		// collabratec.closeBrowserSession();
		/*
		 * reportFile.save(); reportFile.close();
		 */
		// rs.sendResultsMail();
	}

	public void tearDown(ITestResult result, String Name) {
		// this method writes whether the test case as a whole passed or failed
		// and
		// report to ALM
		TestNGLog slog = new TestNGLog();
		try {
			if (failCount > 0) {
				String sTestStatus = "FAILED";
				slog.pushResults(sTestStatus, System.getProperty("user.dir") + File.separator + "ALMLog", Name); // this.getClass().getSimpleName()
			} else {
				String sTestStatus = "PASSED";
				slog.pushResults(sTestStatus, System.getProperty("user.dir") + File.separator + "ALMLog", Name); // this.getClass().getSimpleName()
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception :" + e.getMessage());
		}
	}

	public void logIt(String message) {
		Reporter.log(message, true);
	}

}
