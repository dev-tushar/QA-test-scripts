/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Reporter;


public class WebDriverFactory {

    private static String browser;
    static Process p=null;
   
    private static final DesiredCapabilities capabilities = new DesiredCapabilities();

    public WebDriver getDriver(Map<String, String> seleniumconfig) {
    	 browser = System.getProperty("browser");
    	  if (browser == null || browser.isEmpty()) {
    	   browser = seleniumconfig.get("browser");
    	  }
    	  System.out.println("Browser="+browser);
    	  Reporter.log(browser, true);
        if (seleniumconfig.get("operatingSystem").equalsIgnoreCase("window")) {
            if (browser.equalsIgnoreCase("firefox")) {
            	System.out.println(seleniumconfig.get("wDriverpathFirefox"));
            	return getFirefoxDriver(seleniumconfig.get("wDriverpathFirefox"),seleniumconfig.get("otherFilesPath"));
            } else if (browser.equalsIgnoreCase("chrome")) {
            	System.out.println(seleniumconfig.get("wDriverpathChrome"));
                return getChromeDriver(seleniumconfig.get("wDriverpathChrome"),seleniumconfig.get("otherFilesPath"));
            } else if (browser.equalsIgnoreCase("Safari")) {
                return getSafariDriver();
            } else if ((browser.equalsIgnoreCase("ie"))
                    || (browser.equalsIgnoreCase("internetexplorer"))
                    || (browser.equalsIgnoreCase("internet explorer"))) {
                return getInternetExplorerDriver(seleniumconfig.get("wDriverpathFirefox"));
            }
        }
        else if(seleniumconfig.get("operatingSystem").equalsIgnoreCase("linux")) 
        {
        	if (browser.equalsIgnoreCase("firefox")) {
            	System.out.println(seleniumconfig.get("driverpathFirefox"));
            	return getFirefoxDriver(seleniumconfig.get("driverpathFirefox"),seleniumconfig.get("otherFilesPath"));
            } 
        	else if (browser.equalsIgnoreCase("chrome")) {
            	System.out.println(seleniumconfig.get("driverpathChrome"));
                return getChromeDriver(seleniumconfig.get("driverpathChrome"),seleniumconfig.get("otherFilesPath"));
            }
        }	
        else if(seleniumconfig.get("operatingSystem").equalsIgnoreCase("mac")){
        	if (browser.equalsIgnoreCase("Safari"))
                return getSafariDriver();
        }
        	
        return new FirefoxDriver();
    }

    private WebDriver setRemoteDriver(Map<String, String> selConfig) {
        DesiredCapabilities cap = null;
        browser = selConfig.get("browser");
        if (browser.equalsIgnoreCase("firefox")) {
            cap = DesiredCapabilities.firefox();
        } else if (browser.equalsIgnoreCase("chrome")) {
            cap = DesiredCapabilities.chrome();
        } else if (browser.equalsIgnoreCase("Safari")) {
            cap = DesiredCapabilities.safari();
        } else if ((browser.equalsIgnoreCase("ie"))
                || (browser.equalsIgnoreCase("internetexplorer"))
                || (browser.equalsIgnoreCase("internet explorer"))) {
            cap = DesiredCapabilities.internetExplorer();
        }
        String seleniuhubaddress = selConfig.get("seleniumserverhost");
        URL selserverhost = null;
        try {
            selserverhost = new URL(seleniuhubaddress);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        cap.setJavascriptEnabled(true);
        
        return new RemoteWebDriver(selserverhost, cap);
    }
    
 
    private static WebDriver getChromeDriver(String driverpath, String downloadFilePath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();

		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("credentials_enable_service",false);
		chromePrefs.put("profile.password_manager_enabled", false);
		
		// disable flash and the PDF viewer
		chromePrefs.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer","plugins.always_open_pdf_externally" });
		//((Object) options).AddUserProfilePreference("plugins.always_open_pdf_externally", true);
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", System.getProperty("user.dir") + File.separator+downloadFilePath);
		chromePrefs.put("credentials_enable_service", false);
		chromePrefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		options.addArguments("--disable-impl-side-painting");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		cap.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		ChromeDriver driver= new ChromeDriver(cap);
		driver.manage().deleteAllCookies();
		return driver;
	}

    private static WebDriver getInternetExplorerDriver(String driverpath) {
        System.setProperty("webdriver.ie.driver", driverpath);
        capabilities.setCapability("ignoreZoomSetting", true);
        capabilities.setCapability("ignoreZoomLevel", true);
        return new InternetExplorerDriver(capabilities);
    }

    private static WebDriver getSafariDriver() {
        return new SafariDriver();
    }
//
//    private static WebDriver getFirefoxDriver() {
//        FirefoxProfile profile = new FirefoxProfile();
//        profile.setPreference("browser.cache.disk.enable", false);
//        return new FirefoxDriver(profile);
//    }
    
    private static WebDriver getFirefoxDriver(String driverpath, String downloadFilePath) {
  	  FirefoxProfile profile = new FirefoxProfile();
  	  System.setProperty("webdriver.gecko.driver", driverpath);
  	  DesiredCapabilities capabilities = DesiredCapabilities.firefox();
  	  capabilities.setCapability("marionette", true);
  	  profile.setPreference("browser.cache.disk.enable", false);
//  	  profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
//  	  profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
//  	  profile.setPreference("browser.download.useDownloadDir", true);
//  	  profile.setPreference("browser.download.folderList", 2);
//  	  profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
//  	  profile.setPreference("security.mixed_content.block_active_content", true);
//  	  profile.setPreference("security.mixed_content.block_display_content", false);
//
//  	  profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
//  	    "application/msword, application/csv, application/ris, text/csv, application/pdf, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
//  	  profile.setPreference("browser.download.manager.showWhenStarting", false);
//  	  profile.setPreference("browser.download.manager.focusWhenStarting", false);
///   	 profile.setPreference("browser.download.dir", downloadFilePath);
//
//  	  profile.setPreference("browser.download.manager.showAlertOnComplete", false);
//  	  profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
//  	  profile.setPreference("plugin.disable_full_page_plugin_for_types",
//  	    "application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
//  	  profile.setPreference("pdfjs.disabled", true);
  	 // FirefoxOptions options = new FirefoxOptions().setProfile(profile).addCapabilities(capabilities);
  	  return new FirefoxDriver();
  }
}
