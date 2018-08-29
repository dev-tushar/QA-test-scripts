package org.myProject.utils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SeleniumWait{
    
    WebDriver driver;
    WebDriverWait wait;
    
    int timeout;
    
    public SeleniumWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.timeout = timeout;
    }

    /**
     * Returns webElement found by the locator if element is visible
     *
     * @param locator
     * @return
     */
    public WebElement getWhenVisible(By locator) {
        WebElement element;
        element = (WebElement) wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator));
        return element;
    }
    
    public WebElement getWhenClickable(By locator) {
        WebElement element;
        element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }
    
    public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
        return wait.until(ExpectedConditions.titleIs(expectedPagetitle)) != null;
    }
    
    public boolean waitForPageTitleToContain(String expectedPagetitle) {
        return wait.until(ExpectedConditions.titleContains(expectedPagetitle))!= null;
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        return (WebElement) wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
    
    public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
        return (List<WebElement>) wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    
    public boolean waitForElementToBeInVisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)) != null;
    }
    
    public WebElement waitForElementToBeClickable(WebElement element) {
        return (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public boolean waitForElementToBeStale(WebElement element) {
        return  wait.until(ExpectedConditions.stalenessOf(element));
    }
    
    public void clickWhenReady(By locator) {
        WebElement element = (WebElement) wait.until(ExpectedConditions
                .elementToBeClickable(locator));
        element.click();
    }

    public void waitForPageTitleToAppearCompletely(String txtPageTitle) {
    	WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.titleIs(txtPageTitle));
    }
    
    public void waitForMsgToastToDisappear() {
        int i = 0;
        resetImplicitTimeout(1);
        try {
            while (driver.findElement(By.className("toast-message")).isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void waitForElementToDisappear(WebElement element) {
        int i = 0;
        resetImplicitTimeout(2);
        try {
            while (element.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void resetImplicitTimeout(int newTimeOut) {
        try {
            driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
        } catch (Exception e) {	
        }
    }
    
    public void resetExplicitTimeout(int newTimeOut) {
    	this.wait = new WebDriverWait(driver, newTimeOut);
    }   

    // TODO Implement Wait for page load for page synchronizations
    public void waitForPageToLoadCompletely() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//*")));
    
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void waitForPageToLoad(){
    	try{
	    	wait.until(new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver d) { 
	            	//hardWait(1);
	                return ((Boolean) ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));                
	            }
	        });
    	}catch (Exception e) {  }
    }
    
    public boolean waitForAjaxLoad(){
    	try{			
			((JavascriptExecutor)driver).executeScript("return jQuery()!=null");
		}catch(WebDriverException e){
			((JavascriptExecutor)driver).executeScript("var head = document.getElementsByTagName(\"head\")[0]; "				
					+ "var script = document.createElement('script');"
					+ "script.type = 'text/javascript';"
					+ "script.src = 'http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js';"
					+ "head.appendChild(script);"
					);			
		}    	
    	try{
	    	return wait.until(new ExpectedCondition<Boolean>() {	    		
	            public Boolean apply(WebDriver d) {	            	
	            		return  ((Boolean) ((JavascriptExecutor)driver).executeScript("return jQuery.active==0"));	            	
	            }    	
	        });  
    	}catch(Exception e1) {
    		return false;
        }
    }
    
    public int getTimeout() {
    	return timeout;
    }
    
    public void waitTillFileGetDownload(int attempt , String path)
	{
		boolean flag=false;
		for(int i=1; i<=attempt; i++)
		{
			System.out.println("Waiting for the File to get Download Atteampt No.: "+i);
			hardWait(5);
			File file=new File(path);
			if(file.list()!=null)
			{
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag, "[ASSERTIION FAILED]: File not downloaded in the path "+path);
		System.out.println("[ASSERTION PASSED]: Able to download file successfully. Path:"+path);
	}
    
	public void waitForPageLoad()
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	
   
}
