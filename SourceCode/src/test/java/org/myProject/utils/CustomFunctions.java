package org.myProject.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

public class CustomFunctions{
	static WebDriver driver;
	static String productName;

	public CustomFunctions(WebDriver driver) {
		CustomFunctions.driver = driver;
	}
	
	public static void setProduct(String product) {
		productName = product;
	}

	protected Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 5);
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void handleAlert() {
		try {
			switchToAlert().accept();
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("No Alert window appeared...");
		}
	}
	
	public String getCurrentDayDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getDateTimeString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_a");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String getDateTimeStringForReport() {
		DateFormat dateFormat = new SimpleDateFormat("h a z,MMMM dd,yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/*
	 * Takes a String and returns a String appended with current timestamp
	 */
	public static String getStringWithTimestamp(String name) {
		Long timeStamp = System.currentTimeMillis();
		return name + "_" + timeStamp;
	}
	
	/**
	 * If current month is January:
	 * returns 'Jan' if monthOffset is 0
	 * returns 'Feb' if monthOffset is 1
	 * returns 'Dec' if monthOffset 0s -1
	 */
	public String getMonth(int monthOffset) {
		DateFormat dateFormat = new SimpleDateFormat("MMM");
		Date newDate = DateUtils.addMonths(new Date(), monthOffset);
		String date_time = dateFormat.format(newDate);
		return date_time;
	}
	
	/**
	 * If current month is January:
	 * returns 'January' if monthOffset is 0
	 * returns 'February' if monthOffset is 1
	 * returns 'December' if monthOffset 0s -1
	 */
	public String getMonthFullName(int monthOffset) {
		DateFormat dateFormat = new SimpleDateFormat("MMMM");
		Date newDate = DateUtils.addMonths(new Date(), monthOffset);
		String date_time = dateFormat.format(newDate);
		return date_time;
	}
	
	/**
	 * If current month is January, 2015:
	 * returns '2015' if monthOffset is 0
	 * returns '2016' if monthOffset is 12
	 * returns '2014' if monthOffset 0s -1
	 */
	public String getYear(int monthOffset) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date newDate = DateUtils.addMonths(new Date(), monthOffset);
		String date_time = dateFormat.format(newDate);
		return date_time;
	}
	
    private List<String> getListOfSubdirectory(String path) {
    	File pageObjDir = new File(path);
    	File[] listOfFiles = pageObjDir.listFiles();
    	List<String> subdirList = new ArrayList<String>();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isDirectory()) {
    			subdirList.add(listOfFiles[i].getName());
    	    } 
    	}
    	return subdirList;
    }
    
    private List<String> getListOfFiles(String path) {
    	File pageObjDir = new File(path);
    	File[] listOfFiles = pageObjDir.listFiles();
    	List<String> fileList = new ArrayList<String>();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			fileList.add(listOfFiles[i].getName());
    	    } 
    	}
    	return fileList;
    }
    
    
    private List<String> getListOfElementNames(String actionFilePath) throws FileNotFoundException {
    	List<String> elemNames = new ArrayList<String>();
    	BufferedReader br = new BufferedReader(new FileReader(actionFilePath));
    	String line;
    	try {
			while ((line = br.readLine()) != null) {
			   List<String> elemNamesInLine = getElementNamesFromLine(line);
			   if (elemNamesInLine != null){
				   for (int i = 0; i < elemNamesInLine.size(); i++) {
				        if (!elemNames.contains(elemNamesInLine.get(i))) {
				        	elemNames.add(elemNamesInLine.get(i));
				        }
				    }
			   }   
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return elemNames;
    }
    
    private List<String> getElementNamesFromLine(String line) {
    	List<String> matches = new ArrayList<String>();
        String pattern = "(isElementDisplayed|element|elements|verifyCheckBoxIsChecked|waitForElementToBeVisible|"
        		+ "waitAndClick|waitScrollAndClick|waitAndScrollToElement|fillText|verifyTextOfElementIsCorrect|"
        		+ "verifyAttributeOfElementIsCorrect|waitForElementToDisappear|getTextFirstElementOfList|"
        		+ "verifyRadioButtonSelected|clickElementIfVisible|verifyElementNotDisplayed|verifyBackgroundColorHexCode)[(][\"](.*?)[\"]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        while(m.find()){
        	matches.add(m.group(2));
        }
    	return matches;
    }
    
    public void reportPageObjectDescrepancies(String projDir, String product){
    	System.out.println("Reporting any descrepencies in Page Object Files...");
    	String pageObjectDir = projDir + File.separator + "src" + File.separator + "test"
    			+ File.separator + "resources" + File.separator + "PageObjectRepository"+ File.separator + product.toUpperCase();
    	String keywordDir = projDir + File.separator + "src" + File.separator + "test"
    			+ File.separator + "java" + File.separator + "com" + File.separator + "qait"
    			+ File.separator + product + File.separator + "keywords";
    	List<String> envList = getListOfSubdirectory(pageObjectDir);
    	List<String> actionFileNames = getListOfFiles(keywordDir);
    	//printList(envList);
    	//printList(actionFileNames);    	
    	String result = "";
    	for (int i = 0; i < actionFileNames.size(); i++) {
    		try {
    			if (actionFileNames.get(i).equals("")) continue;
	    		String actionFilePath = keywordDir + File.separator + actionFileNames.get(i);
	    		String pageObjectFileName = getPageObjectFileName(actionFilePath) + ".txt";
	    		List<String> listOfElemsInActionFile = getListOfElementNames(actionFilePath);
	    		for (int j = 0; j < envList.size(); j++) {
	    			//System.out.println("Action file name : " + actionFileNames.get(i));
	    			//System.out.println("Environment : " + envList.get(j));
	    			String pageObjectFilePath = pageObjectDir + File.separator + envList.get(j) + 
	    					File.separator + pageObjectFileName;
	    			String resultPageObjFile = getDetailsOfMissingDuplicateExtraElements(pageObjectFilePath, listOfElemsInActionFile);
	    			if (!resultPageObjFile.equals("")) {
	    				result += "*****************************************\n";
	    				result += "Page Object File Name : " + pageObjectFileName + "\n";
	    				result += "Environment : " + envList.get(j) + "\n";
	    				result += "*****************************************\n";
	    				result += resultPageObjFile + "\n";
	    			}
	    		}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	if (result.equals(""))
    		System.out.println("All Page Object Files have correct Element Names\n");
    	else
    		System.out.println(result);
    }

	private String getDetailsOfMissingDuplicateExtraElements(String pageObjectFilePath,
			List<String> listOfElemsInActionFile) throws FileNotFoundException {
		String returnStr = "";
		for (int i = 0; i < listOfElemsInActionFile.size(); i++) {
			String elem = listOfElemsInActionFile.get(i);
			int occurance = getOccuranceCountOfElement(pageObjectFilePath, elem);
			if (occurance == 0) {
				returnStr += "Element '" + elem + "' is missing\n";
			} else if (occurance > 1) {
				returnStr += "Element '" + elem + "' is present " + occurance + " times\n";
			}
		}
		return returnStr;
	}


	private int getOccuranceCountOfElement(String pageObjectFilePath, String elem) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(pageObjectFilePath));
    	String line;
    	int count = 0;
    	try {
			while ((line = br.readLine()) != null) {
			   if (line.startsWith(elem + ":")) count++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	private String getPageObjectFileName(String actionFilePath) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(actionFilePath));
    	String line;
    	try {
			while ((line = br.readLine()) != null) {
			   String returnVal = getPageObjectFileNameFromLine(line);
			   if (!returnVal.equals("")){
				   br.close();
				   return returnVal;
			   }
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return "";
	}

	private String getPageObjectFileNameFromLine(String line) {
        String pattern = "super[(]driver,\\s?[\"](.*?)[\"][)]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(line);
        while(m.find()){
        	return m.group(1);
        }
        return "";
	}
	
	/**
	 * Get system date in MM/dd/YY format
	 * @return
	 */
	public String getSystemDate(){
		Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
        return ft.format(dNow);
	}
	
	/**
	 * Get current date in MM/dd/YYYY format
	 * @return
	 */
	public String getCurrentDate(){
		Date dNow = new Date( );
		@SuppressWarnings("unused")
		String date="";
        SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
        //if(ft.format(dNow).startsWith("0")) date = ft.format(dNow).substring(1, ft.format(dNow).length());
        //return date;
        return ft.format(dNow);
	}
    
	public String getPreviousDate(){
		 Date dNow = new Date( );
	     SimpleDateFormat ft = new SimpleDateFormat ("MMMM yyyy");
	     Calendar cal = Calendar.getInstance();
	     cal.add(Calendar.MONTH, -1);
	     dNow = cal.getTime();
	     return ft.format(dNow);
	}
	
	public static String readFile(String filePath) {
		String data = "";
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				if (strLine.trim().equals("")) continue;
				data += strLine.trim() + "\n";
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/*
	 * Takes screenshot. Creates Screenshot in path/Screenshots/<Date>
	 * with name <DateTime>_<Testname>.png
	 */
	public String takeScreenshot(String folderName, String testName) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
		Date date = new Date();
		String date_time = dateFormat.format(date);
		testName = testName.substring(testName.lastIndexOf(".") + 1);
		File file = new File(System.getProperty("user.dir") + File.separator + folderName + File.separator
				+ "Screenshots" + File.separator + date_time);
		boolean exists = file.exists();
		if (!exists) {
			new File(System.getProperty("user.dir") + File.separator + folderName + File.separator + "Screenshots"
					+ File.separator + testName + File.separator + date_time).mkdir();
		}

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String saveImgFile = System.getProperty("user.dir") + File.separator + folderName + File.separator
					+ "Screenshots" + File.separator + testName + File.separator + date_time + File.separator
					+ "screenshot.png";
			FileUtils.copyFile(scrFile, new File(saveImgFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String generateUniqueEmailId()
	{
		String username = getStringWithTimestamp("autoQA");
		return username+"@xyz.com";
	}
	
	public static String generateUniqueDocTitle()
	{
		String username = getStringWithTimestamp("autoQA");
		return username+"_docTitle";
	}
	
	
	
	
	
	
	
	
}