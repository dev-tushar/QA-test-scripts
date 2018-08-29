/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package org.myProject.utils;

import static org.myProject.utils.ConfigPropertyReader.getProperty;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.screentaker.ViewportPastingStrategy;

/**
 * @author QAIT
 * 
 */
public class TakeScreenshot {

  WebDriver driver;
  String testname;
  String screenshotPath = "target" + File.separator + "Screenshots";

  public TakeScreenshot(String testname, WebDriver driver) {
    this.driver = driver;
    this.testname = testname;
  }

  public void takeScreenshot() {
    screenshotPath = (getProperty("screenshot-path") != null) ? getProperty("screenshot-path") : screenshotPath;
    DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
    Date date = new Date();
    String date_time = dateFormat.format(date);
    File file =
        new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time);
    boolean exists = file.exists();
    if (!exists) {
      new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time)
          .mkdir();
    }

    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    try {
      String saveImgFile =
          System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time
              + File.separator + "_Screenshot.png";
      Reporter.log("Save Image File Path : " + saveImgFile, true);
      FileUtils.copyFile(scrFile, new File(saveImgFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void takeScreenshot(String methodName) {
    screenshotPath = (getProperty("screenshot-path") != null) ? getProperty("screenshot-path") : screenshotPath;
    DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
    Date date = new Date();
    String date_time = dateFormat.format(date);
    File file =
        new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time);
    boolean exists = file.exists();
    if (!exists) {
      File file1 =
          new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time);
      file1.mkdirs();
    }
    final Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(500)).takeScreenshot(driver);
    final BufferedImage image = screenshot.getImage();
    try {
      String saveImgFile =
          System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname + File.separator + date_time
              + File.separator + methodName + ".jpg";
      Reporter.log("Save Image File Path : " + saveImgFile, true);
      ImageIO.write(image, "PNG", new File(saveImgFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void takeScreenShotOnException(ITestResult result) {

    String takeScreenshot = getProperty("take-screenshot");
    if (result.getStatus() == ITestResult.FAILURE) {
      if (takeScreenshot.equalsIgnoreCase("true") || takeScreenshot.equalsIgnoreCase("yes")) {
        try {
          if (driver != null) {
            DataPropertFileUtil.setProperty("URL", driver.getCurrentUrl());
            takeScreenshot();
          }
        } catch (Exception ex) {
          Reporter.log("Driver is null while taking screen shot", true);
        }
      }
    }
  }

  public void report(String stepText, String stepDetails) {
    Reporter.log(stepText + " " + stepDetails, true);
  }

  public void takeScreenShotOnException(ITestResult result, int i) {

    String takeScreenshot = getProperty("take-screenshot");
    if (result.getStatus() == ITestResult.FAILURE) {
//      DataPropertFileUtil.setProperty("URLAtFailureOfTest" + i, driver.getCurrentUrl());
      if (takeScreenshot.equalsIgnoreCase("true") || takeScreenshot.equalsIgnoreCase("yes")) {
        try {
          if (driver != null) {
            System.out.println("Failure URL : " + driver.getCurrentUrl());
            takeScreenshot();
          }
        } catch (Exception ex) {
          Reporter.log("Driver is null while taking screen shot", true);
        }
      }
    }
  }

  public int incrementcounterForTests(int counterForTests, ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE)
      counterForTests++;
    return counterForTests;
  }

  public void takeScreenShotOnException(ITestResult result, int i, String methodName) {

    String takeScreenshot = getProperty("take-screenshot");
    if (result.getStatus() == ITestResult.FAILURE) {
//      DataPropertFileUtil.setProperty("URLAtFailureOfTest" + methodName + i, driver.getCurrentUrl());
      if (takeScreenshot.equalsIgnoreCase("true") || takeScreenshot.equalsIgnoreCase("yes")) {
        try {
          if (driver != null) {
            System.out.println("Failure URL : " + driver.getCurrentUrl());
            takeScreenshot(methodName);
          }
        } catch (Exception ex) {
          Reporter.log("Driver is null while taking screen shot", true);
        }
      }
    }
  }
}
