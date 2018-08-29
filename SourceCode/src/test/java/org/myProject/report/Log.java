//package com.rtts.utilities;
//package org.ieee.selenium.poc;
package org.myProject.report;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;

public class Log {
	//Vital fields
	private int row = 1;
	private int step = 1;
	private static final String directory = "C:\\";
	private File xlsxFile;
	private Workbook wb;
	private Sheet sheet;
	private final int STEP_COLUMN = 0;
	private final int STEP_DESC_COL = 1;
	private final int STEP_DATA_COL = 2;
	private final int STEP_PASS_FAIL = 3;
	private CellStyle passed;
	private CellStyle failed;
	private CellStyle info;
	private CellStyle warning;
	private CellStyle CellHeading;
	private final String InfoTag = "Info";
	
	//Color fields
	private static final short PASS_COLOR = IndexedColors.GREEN.getIndex();
	private static final short FAIL_COLOR = IndexedColors.RED.getIndex();
	private static final short WARN_COLOR = IndexedColors.ORANGE.getIndex();
	private static final short INFO_COLOR = IndexedColors.INDIGO.getIndex();
	private static final short HEADING_COLOR = IndexedColors.BLACK.getIndex();
	//Counts for passes/failures/warnings
	private int passCount = 0;
	private int failCount = 0;
	private int warnCount = 0;
	
	//Below are the constants for text formatting
	public final static String AUTOMATIC = "AUTOMATIC";
	public final static String BLACK = "BLACK";
	public final static String WHITE = "WHITE";
	public final static String RED = "RED";
	public final static String BRIGHT_GREEN = "BRIGHT_GREEN";
	public final static String BLUE = "BLUE";
	public final static String YELLOW = "YELLOW";
	public final static String PINK = "PINK";
	public final static String TURQUOISE = "TURQUOISE";
	public final static String DARK_RED = "DARK_RED";
	public final static String GREEN = "GREEN";
	public final static String DARK_BLUE = "DARK_BLUE";
	public final static String DARK_YELLOW = "DARK_YELLOW";
	public final static String VIOLET = "VIOLET";
	public final static String TEAL = "TEAL";
	public final static String GREY_25_PERCENT = "GREY_25_PERCENT";
	public final static String GREY_50_PERCENT = "GREY_50_PERCENT";
	public final static String GREY_80_PERCENT = "GREY_80_PERCENT";
	public final static String CORNFLOWER_BLUE = "CORNFLOWER_BLUE";
	public final static String MAROON = "MAROON";
	public final static String LEMON_CHIFFON = "LEMON_CHIFFON";
	public final static String ORCHID = "ORCHID";
	public final static String CORAL = "CORAL";
	public final static String ROYAL_BLUE = "ROYAL_BLUE";
	public final static String LIGHT_CORNFLOWER_BLUE = "LIGHT_CORNFLOWER_BLUE";
	public final static String SKY_BLUE = "SKY_BLUE";
	public final static String LIGHT_TURQUOISE = "LIGHT_TURQUOISE";
	public final static String LIGHT_GREEN = "LIGHT_GREEN";
	public final static String LIGHT_YELLOW = "LIGHT_YELLOW";
	public final static String PALE_BLUE = "PALE_BLUE";
	public final static String ROSE = "ROSE";
	public final static String LAVENDER = "LAVENDER";
	public final static String TAN = "TAN";
	public final static String LIGHT_BLUE = "LIGHT_BLUE";
	public final static String AQUA = "AQUA";
	public final static String LIME = "LIME";
	public final static String GOLD = "GOLD";
	public final static String LIGHT_ORANGE = "LIGHT_ORANGE";
	public final static String ORANGE = "ORANGE";
	public final static String BLUE_GREY = "BLUE_GREY";
	public final static String GREY_40_PERCENT = "GREY_40_PERCENT";
	public final static String DARK_TEAL = "DARK_TEAL";
	public final static String SEA_GREEN = "SEA_GREEN";
	public final static String DARK_GREEN = "DARK_GREEN";
	public final static String OLIVE_GREEN = "OLIVE_GREEN";
	public final static String BROWN = "BROWN";
	public final static String PLUM = "PLUM";
	public final static String INDIGO = "INDIGO";
	public FileWriter oResultsFile;
	
	
	/**
	 * Constructor to specify the name of the log file and its path
	 * 
	 * @param logName Name of the log file
	 * @param path Path for the log file 
	 */
	public Log(String logName, String path){
		//Ensure you have rights to write to that directory
		File dir = new File(path);
		
		if (dir.exists()){
			if (!dir.canWrite()){
				dir.setWritable(true);
			}
		}
		
		//Using a date & time stamp for the name and keep the file unique
		SimpleDateFormat date = new SimpleDateFormat("EEE_MMM_d_yy_hh_mm_ss_a");
		//SimpleDateFormat date = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss");
		String formatedDate = date.format(new Date());
		xlsxFile = new File(dir, logName + "-" + formatedDate + ".xlsx");
		try {
			xlsxFile.createNewFile();
			String sLoc = dir + "\\" + logName + "-" + formatedDate + ".xlsx";
			System.out.println("Report location is : " + sLoc);
			outputReportFileLocation(sLoc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Define the workbook objects
		this.wb = new XSSFWorkbook();
		this.sheet = wb.createSheet("Log");
		
		//Adjust the column widths
		sheet.setColumnWidth(STEP_COLUMN, 15*256);
		sheet.setColumnWidth(STEP_DESC_COL, 104*256);
		sheet.setColumnWidth(STEP_DATA_COL, 44*256);
		sheet.setColumnWidth(STEP_PASS_FAIL, 10*256);
		
		//Define header style
		CellStyle headerStyle = wb.createCellStyle();
		Font headerFont = wb.createFont();
		
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(headerFont);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		
		//Create the header row
		Row headerRow = sheet.createRow(0);
		
		//Create the column headers
		Cell stepCol = headerRow.createCell(STEP_COLUMN);
		Cell descCol = headerRow.createCell(STEP_DESC_COL);
		Cell dataCol = headerRow.createCell(STEP_DATA_COL);
		Cell pfCol = headerRow.createCell(STEP_PASS_FAIL);
		
		//Populate Line #
		stepCol.setCellValue("Line #");
		stepCol.setCellStyle(headerStyle);
		
		//Populate Line #
		stepCol.setCellValue("DetailedStep");
		stepCol.setCellStyle(headerStyle);
		
		//Populate Description
		descCol.setCellValue("Description");
		descCol.setCellStyle(headerStyle);
		
		//Populate Test Data
		dataCol.setCellValue("Test Data");
		dataCol.setCellStyle(headerStyle);
		
		//Populate Pass/Fail
		pfCol.setCellValue("Pass/Fail");
		pfCol.setCellStyle(headerStyle);
		
		//Define Passed Style
		passed = wb.createCellStyle();
		Font fontPassed = wb.createFont();
		fontPassed.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontPassed.setColor(PASS_COLOR);
		passed.setFont(fontPassed);
		
		//Define Failed Style
		failed = wb.createCellStyle();
		Font fontFailed = wb.createFont();
		fontFailed.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontFailed.setColor(FAIL_COLOR);
		failed.setFont(fontFailed);
		
		//Define Warning Style
		warning = wb.createCellStyle();
		Font fontWarn = wb.createFont();
		fontWarn.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontWarn.setColor(WARN_COLOR);
		warning.setFont(fontWarn);
		
		//Define Info style
		info = wb.createCellStyle();
		Font infoFont = wb.createFont();
		infoFont.setColor(INFO_COLOR);
		info.setFont(infoFont);
		
		//Define Heading Style 
		CellHeading = wb.createCellStyle();
		Font CellHeadingFont = wb.createFont();
		CellHeadingFont.setColor(HEADING_COLOR);
		CellHeadingFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellHeading.setFont(CellHeadingFont);
	}
	
	/**
	 * Constructor that allows the log file name to be specified. 
	 *  
	 * @param logName Name of the log file 
	 */
	public Log(String logName){
		this(logName, directory);
	}
	
	/**
	 * Default constructor
	 */
	public Log(){
		this("testLog");
	}
	
	/**
	 * Method for writing to the log file, including a step name and a pass or fail for the step
	 * 
	 * @param description Log item description
	 * @param testData The test data
	 * @param passed Passing or failing for this test step
	 * @throws IOException
	 */
	public void write(String sDetailedStep, String description, String testData, String sStatus)
			throws IOException {
		String pfValue; //String for determining the text to be entered or the Pass/Fail field
		CellStyle style;
		if (sStatus.equals("Pass")){
			pfValue = "Pass";
			style = this.passed;
			this.passCount++;
		} else if (sStatus.equals("Fail")) {
			pfValue = "Fail";
			style = this.failed;
			this.failCount++;
		} else {
			pfValue = " ";
			style = this.CellHeading;
		//	this.failCount++;	
		}
		
		//Create the row and populate the data
		Row stepRow = this.sheet.createRow(row);
		
		//Line number
		//Cell lineNum = stepRow.createCell(STEP_COLUMN);
		//lineNum.setCellType(Cell.CELL_TYPE_NUMERIC);
		//lineNum.setCellValue(this.step);
		
		//Detailed Step
		Cell sDetailStep = stepRow.createCell(STEP_COLUMN);
		sDetailStep.setCellValue(sDetailedStep);
		sDetailStep.setCellStyle(style);
		
		//Description
		Cell desc = stepRow.createCell(STEP_DESC_COL);
		desc.setCellValue(description);
		desc.setCellStyle(style);
		
		//Test data
		Cell tData = stepRow.createCell(STEP_DATA_COL);
		tData.setCellValue(testData);
		tData.setCellStyle(style);
		
		//Pass/Fail
		Cell pfFlag = stepRow.createCell(STEP_PASS_FAIL);
		pfFlag.setCellValue(pfValue);
		pfFlag.setCellStyle(style);
		
		this.row++;
		//this.step++;
	}
	
	/**
	 * Writes a warning message to the log
	 * @param text {@link String} containing text to write in the warning tag
	 */
	public void writeWarning(String text){
		this.warnCount++;
		this.writeInfo(text, this.warning, "Warning", this.warning);
	}
	
	/**
	 * Writes a generic Info message to the log.
	 * 
	 * @param text Message to write to the log.
	 */
	public void write(String text)  {
		this.writeInfo(text, this.info, this.InfoTag, null);
	}
	
	/**
	 * Custom info message to be written to the console. The available colors are: <br>
	 * BLACK, WHITE, RED, BRIGHT_GREEN, BLUE, YELLOW, PINK, TURQUOISE, DARK_RED, GREEN, DARK_BLUE, 
	 * DARK_YELLOW, VIOLET, TEAL, GREY_25_PERCENT, GREY_50_PERCENT, CORNFLOWER_BLUE, MAROON, LEMON_CHIFFON, 
	 * ORCHID, CORAL, ROYAL_BLUE, LIGHT_CORNFLOWER_BLUE, SKY_BLUE, LIGHT_TURQUOISE, LIGHT_GREEN, LIGHT_YELLOW, 
	 * PALE_BLUE, ROSE, LAVENDER, TAN, LIGHT_BLUE, AQUA, LIME, GOLD, LIGHT_ORANGE, ORANGE, BLUE_GREY, GREY_40_PERCENT, 
	 * DARK_TEAL, SEA_GREEN, DARK_GREEN, OLIVE_GREEN, BROWN, PLUM, INDIGO, GREY_80_PERCENT, and AUTOMATIC
	 * 
	 * @param text Message to be written to the log
	 * @param textFontColor The color of the text in the log
	 * @param bgColor The color of the cell background of this log message
	 * @param bold {@code true} for bold, {@code false} otherwise
	 */
	public void write(String text, String textFontColor, String bgColor,
			boolean bold) {
		//Define the style to pass to writeInfo
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		
		//Define the font to be bold if requested
		if (bold){
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		
		//Define the text color if possible
		try {
			Class<?> c1 = Class.forName("org.apache.poi.ss.usermodel.IndexedColors");
			Field fontColor = c1.getDeclaredField(textFontColor.toUpperCase());
			font.setColor(((IndexedColors)fontColor.get(null)).getIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Define background color
		try {
			Class<?> c1 = Class.forName("org.apache.poi.ss.usermodel.IndexedColors");
			Field fontColor = c1.getDeclaredField(bgColor.toUpperCase());
			style.setFillForegroundColor(((IndexedColors) fontColor.get(null)).getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		style.setFont(font);
		this.writeInfo(text, style, InfoTag, null);
	}
	
	/**
	 * Performs the task of writing information to the log
	 * 
	 * @param text Text to be entered into the info tag
	 * @param style The {@link CellStyle} to format the cell
	 * @param tag Text to be entered as the pass/fail tag
	 * @param tagStyle The {@link CellStyle} defining the format for the Pass/Fail column. 
	 * For the default format, set to {@code null}
	 */
	private void writeInfo(String text, CellStyle style, String tag, CellStyle tagStyle){
		Row infoRow = sheet.createRow(row);
		infoRow.createCell(STEP_COLUMN).setCellValue(step);
		
		Cell tagCell = infoRow.createCell(this.STEP_PASS_FAIL);
		tagCell.setCellValue(tag);
		if (tagStyle != null){
			tagCell.setCellStyle(tagStyle);
		}
		
		Cell infoCell = infoRow.createCell(STEP_DESC_COL);
		infoCell.setCellValue(text);
		infoCell.setCellStyle(style);
		
		sheet.addMergedRegion(new CellRangeAddress(row, row, STEP_DESC_COL, STEP_DATA_COL));
		row++;
		step++;
	}
	
	/**
	 * Closes and saves the log
	 * @throws IOException
	 */
	public void close() throws IOException {
		//Write the counts to the log
		this.write("Summary of Test Results", WHITE, BLUE, true);
		this.writeInfo("Passed Count:  " + this.passCount, this.passed, this.InfoTag, null);
		this.writeInfo("Failed Count:  " + this.failCount, this.failed, this.InfoTag, null);
		this.writeInfo("Warning Count: " + this.warnCount, this.warning, this.InfoTag, null);
		
		//Line Number
		Row stepRow = this.sheet.createRow(row);
		
		//Description Column
		Cell desc = stepRow.createCell(this.STEP_DESC_COL);
		desc.setCellValue("Final Result: ");
		
		CellStyle descStyle = wb.createCellStyle();
		descStyle.cloneStyleFrom(this.info);
		Font bold = wb.createFont();
		bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		descStyle.setFont(bold);
		desc.setCellStyle(descStyle);
		
		//Test Data
		Cell testData = stepRow.createCell(this.STEP_DATA_COL);
		
		if (this.failCount > 0 || this.passCount == 0){
			testData.setCellValue("FAILED");
			testData.setCellStyle(this.failed);
			pushResults("FAILED");
		} else if (this.warnCount > 0){
			testData.setCellValue("WARNING");
			testData.setCellStyle(this.warning);
		} else {
			testData.setCellValue("PASSED");
			testData.setCellStyle(this.passed);
			pushResults("PASSED");
		}
		
		FileOutputStream out = new FileOutputStream(xlsxFile);
		this.wb.write(out);
		out.close();
	}
	
	/**
	 * Saves the log
	 * @throws IOException
	 */
	public void save() throws IOException {
		//Saves the file
		FileOutputStream out = new FileOutputStream(xlsxFile);
		this.wb.write(out);
		out.close();
	}
	
	/**
	 * Captures a snapshot of the entire desktop and stores the image in the log
	 * @param filePath {@link String} argument specifying the temporary location of the snapshot image.
	 * @param fileName {@link String} argument specifying the base name of the temporary image.
	 * 		This file will be deleted once the image is added to the Excel log 
	 */
	public void takeSnapshot(String filePath, String fileName) {
		//Define the Date & Time stamp for the image
		SimpleDateFormat date = new SimpleDateFormat("EEE, MMM d ''yy hh_mm_ss a");
		String formattedDate = date.format(new Date());
		
		try {
			//Use the Robot class to capture the snapshot
			Robot robot = new Robot();
			File imgFile = new File(filePath + "\\" + fileName +  "_" + formattedDate + ".jpeg"); 
			
			//Write the snapshot out to a file
			BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(screenShot, "jpeg", imgFile);
			
			int pictureIdx = 0;
			
		    //add picture data to this workbook.
		    InputStream is;
			try {
				is = new FileInputStream(imgFile.getAbsolutePath());
				byte[] bytes = IOUtils.toByteArray(is);
				pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
				is.close();
				//imgFile.delete(); <---Uncomment this if you want the image deleted
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Drawing drawing = sheet.createDrawingPatriarch();
			ClientAnchor anchor = wb.getCreationHelper().createClientAnchor();
			
			//Set to the top-left corner and define the size of the picture
			anchor.setCol1(STEP_DESC_COL);
			anchor.setRow1(row);
			anchor.setCol2(STEP_PASS_FAIL);
			anchor.setRow2(row + 30);
			
			drawing.createPicture(anchor, pictureIdx);
			
			row+=30;
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Captures an image of the webpage currently loaded in the {@link WebDriver} and inserts it into the log.
	 * @param driver {@link WebDriver} containing page for image capture
	 */
	public void takeSnapshot(WebDriver driver){
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		int pictureIdx = 0;
		try {
			InputStream is = new FileInputStream(scrFile);
			byte[] bytes = IOUtils.toByteArray(is);
			pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = wb.getCreationHelper().createClientAnchor();
		
		//Set to the top-left corner and define the size of the picture
		anchor.setCol1(STEP_DESC_COL);
		anchor.setRow1(row);
		anchor.setCol2(STEP_DATA_COL);
		anchor.setRow2(row + 30);
		
		drawing.createPicture(anchor, pictureIdx);
		
		row+=30;
	}

	public void outputReportFileLocation(String sFileLocation) {
		try{
			oResultsFile = new FileWriter("C:\\IT-QA\\Automation\\Collabratec\\Data\\ReportLocation.txt");
			PrintWriter  printWriter = new PrintWriter(oResultsFile);
			printWriter.println(sFileLocation);
			printWriter.close();
		} catch (IOException e) {
			System.out.println("File Location additon filed : " + e.getMessage());
		}
		
	
	}
	public void pushResults(String sTestStatus) {
		try { 
			oResultsFile = new FileWriter("C:\\IT-QA\\Automation\\Collabratec\\Data\\ReportLocation.txt", true);
			//oResultsFile.write("\n");
			oResultsFile.write(sTestStatus);
			oResultsFile.close();
		} catch(IOException e) {
			System.out.println("File Location additon filed : " + e.getMessage());
		}
	}
}
