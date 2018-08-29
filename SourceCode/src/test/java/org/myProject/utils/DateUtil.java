package org.myProject.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.testng.Reporter;

public class DateUtil {
	
	Calendar cal;

	public static String getCurrentDateTime() {
		String ranNum="";
		DateFormat formatter = new SimpleDateFormat("MMM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH+1));
		String date = Integer.toString(cal.get(Calendar.DATE));
		try{
			ranNum = dformatter.format(dateParse.parse(date))+formatter.format(monthParse.parse(month))+"_"+Integer.toString(cal.get(Calendar.HOUR_OF_DAY))+":"+Integer.toString(cal.get(Calendar.MINUTE))+":"+Integer.toString(cal.get(Calendar.SECOND));
		}catch(Exception e){}
		return ranNum;
	}

	public static String getDate(){
		DateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE));
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String calDate = month+"/"+date+"/"+year;
		return calDate;
	}


	public static String getTommorrowsDate(){
		DateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+1);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String calDate = month+"/"+date+"/"+year;
		return calDate;
	}

	public static String getTommorrowsDateFne(){
		DateFormat formatter = new SimpleDateFormat("MMM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+1);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String calDate = month+" "+date;
		return calDate;
	}

	public static String getDayAfterTommorrowsDate(){
		DateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+2);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String calDate = month+"/"+date+"/"+year;
		return calDate;
	}

	public static String getDayAfterTommorrowsDateFne(){
		DateFormat formatter = new SimpleDateFormat("MMM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+2);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String calDate = month+" "+date;
		return calDate;
	}

	public static String getDayToDayAfterTommorrowsDate(){
		DateFormat formatter = new SimpleDateFormat("MM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+3);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String calDate = month+"/"+date+"/"+year;
		return calDate;
	}

	public static String getDayToDayAfterTommorrowsDateFne(){
		DateFormat formatter = new SimpleDateFormat("MMM");
		SimpleDateFormat monthParse = new SimpleDateFormat("MM");
		DateFormat dformatter = new SimpleDateFormat("DD");
		SimpleDateFormat dateParse = new SimpleDateFormat("DD");
		Calendar cal = Calendar.getInstance();
		String month = Integer.toString(cal.get(Calendar.MONTH)+1);
		String date = Integer.toString(cal.get(Calendar.DATE)+3);
		try {
			month = formatter.format(monthParse.parse(month));
			date=dformatter.format(dateParse.parse(date));
		} catch (ParseException e) {}
		String calDate = month+" "+date;
		Reporter.log("end date:"+calDate+getCurrentTime(),true);
		return calDate+getCurrentTime();
	}

	public static String getDate(String date){
		if (date.equalsIgnoreCase("TomorrowDate"))
			return getTommorrowsDate();
		if (date.equalsIgnoreCase("DayAfterTomorrowDate"))
			return getDayAfterTommorrowsDate();
		if (date.equalsIgnoreCase("DayToDayAfterTomorrowDate"))
			return getDayToDayAfterTommorrowsDate();
		return fixedDate();
	}

	public static String getDateForFne(String date){
		if (date.equalsIgnoreCase("TomorrowDate"))
			return getTommorrowsDateFne();
		if (date.equalsIgnoreCase("DayAfterTomorrowDate"))
			return getDayAfterTommorrowsDateFne();
		if (date.equalsIgnoreCase("DayToDayAfterTomorrowDate"))
			return getDayToDayAfterTommorrowsDateFne();
		return fixedDate();
	}

	
	public static String getTimeAsPerTimeZone(String time,String timeZOne){
		time = time.split("Minutes")[1];
		DateFormat formatter = new SimpleDateFormat("hh:mm a");
		TimeZone tz = TimeZone.getTimeZone("EST5EDT");
		Calendar cal = new GregorianCalendar(tz);
		cal.add(Calendar.MINUTE, Integer.parseInt(time));
		formatter.setTimeZone(tz);
		return formatter.format(cal.getTime());
	}

	public String getDateTwoDaysBefore()
    {
    	String _2daysDate;
    	    DateFormat formatter = new SimpleDateFormat("MM");
	        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
	        DateFormat dformatter = new SimpleDateFormat("DD");
	        SimpleDateFormat dateParse = new SimpleDateFormat("DD");
	        Calendar cal = Calendar.getInstance();
	             	
	        String month = Integer.toString(cal.get(Calendar.MONTH)+1);
	        System.out.println("month is :"+month);
	        String date = Integer.toString(cal.get(Calendar.DATE)-2);
	        
	        if(Integer.parseInt(date)<=0){
	        	cal.add(Calendar.MONTH, -1);
	        	date = Integer.toString(cal.getActualMaximum(Calendar.DAY_OF_MONTH)+Integer.parseInt(date));
	        	month = Integer.toString(Integer.parseInt(month)-1);
	        }
	        _2daysDate = date;
//	        _2daysDate=setDate(date, month);
	        
	        /*if(!(_2daysDate.equals(date)))
	        	month=String.valueOf((cal.get(Calendar.MONTH)+1)-1);*/
	        
	        System.out.println("2 days before date: "+_2daysDate);
	              
	        
	        try {
	            month = formatter.format(monthParse.parse(month));
	            date=dformatter.format(dateParse.parse(_2daysDate));
	        } catch (ParseException e) {}
	        String year = Integer.toString(cal.get(Calendar.YEAR));
	        String calDate = month+"/"+date+"/"+year;
	        return calDate;
    }
    	    
    public String setDate(String date,String mon)
     {
    	String d; int m1=0;
    	int m=Integer.parseInt(mon);
    	
    	if((m-1)==0)
    		m1=12;
    	else
    		m1=m-1;
    		    	
    	switch(m1) {
		case 2:
			 d=date(28,date);
			System.out.println("Date is :"+d);
			break;
		case 7: case 8:
			 d=date(31,date);
			System.out.println("Date is :"+d);
			break;
		
	    default:
	    	if(m1%2==0)
			   d=date(31,date);
	    	else
	    	   d=date(30,date);			
			break;
	 }
    	return d;
       
    }
    
    private String date(int days, String date)
    {
    	int date1=Integer.parseInt(date);
    	if(date1==0)
    		return String.valueOf(days-date1);
    	else if(date1==-1)
    		return String.valueOf(days+date1);
    	else
    		return date;
    		    		
    }

	public static String fixedDate(){
		return "12/31/2013";
	}

	public String getDesiredDate(int count){
    	_Add_OR_Subtract_Date(count);
    	String date = _get_Day_Date_Month_Year("date");
		if (date.startsWith("0")) {
			date = date.replaceFirst("0", "");
		}
		String month = _get_Day_Date_Month_Year("month");
		String year = _get_Day_Date_Month_Year("year");
		String calDate = month+"/"+date+"/"+year;
		System.out.println(calDate);
		return calDate;
    }
	
	private String _get_Day_Date_Month_Year(String value) {
		
		String str = cal.getTime().toString();
		String[] result = str.split(" ");

		if (value.equalsIgnoreCase("day")) {
			return result[0];
		} else if (value.equalsIgnoreCase("date")) {
			return result[2];
		} else if (value.equalsIgnoreCase("month")) {
			return result[1];
		} else if (value.equalsIgnoreCase("year")) {
			return result[5];
		} else {
			return "Invalid Value";
		}
	}

    private void _Add_OR_Subtract_Date(int noOfDays) {
		cal = Calendar.getInstance();
		 System.out.println("Today : " + cal.getTime());
		cal.add(Calendar.DATE, noOfDays);
		 System.out.println("7 days after: " + cal.getTime());
	}
	
	public static String getCurrentTime(){
		Date date = new Date();
		String strDateFormat = "HH:mm a";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		return(sdf.format(date));
	}

	public static boolean checkWhetherInputIsAValidDate(String format, String dateString){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
		      dateFormat.parse(dateString.trim());
		    } catch (ParseException pe) {
		      return false;
		    }
		    return true;
	}
	
	/**
	 * Method which gets the current date in a specific format
	 * 
	 * @param specificFormat - Format of Date
	 * @return 
	 * 
	 */
	public static String getDateInSpecificFormat(String specificFormat) {
		DateFormat dateFormat = new SimpleDateFormat(specificFormat);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getPreviousMonth(){
		 cal = Calendar.getInstance();
	     cal.add(Calendar.MONTH, -1);
	     return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );

	}
	
	
	public String getCurrentMonth(){
		 cal = Calendar.getInstance();
	     cal.add(Calendar.MONTH, 0);
	     return cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );

	}
	
	public String getCurrentYear(){
		cal = Calendar.getInstance();
		if(getPreviousMonth().contains("December")){
			cal.add(Calendar.YEAR, +1);
		}
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	public String getPreviousYear(){
		cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	public static String[] getArrayOfMonths(){
		String arr[] = new String[12];
		arr[0]="January";
		arr[1]="February";
		arr[2]="March";
		arr[3]="April";
		arr[4]="May";
		arr[5]="June";
		arr[6]="July";
		arr[7]="August";
		arr[8]="September";
		arr[9]="October";
		arr[10]="November";
		arr[11]="December";
		return arr;
	}
	
//    public static Date getDate(String calendarField, int period){
//      Calendar calendar = Calendar.getInstance();
//      if(calendarField.equalsIgnoreCase("day"))
//          calendar.add(Calendar.DAY_OF_MONTH,period);
//      else if(calendarField.equalsIgnoreCase("month"))
//          calendar.add(Calendar.MONTH,period);
//      else if(calendarField.equalsIgnoreCase("year"))
//          calendar.add(Calendar.YEAR,period);
//      return calendar.getTime();
//  }
    
    public static Date getDate(String calendarField, int period){
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, 0);
      calendar.clear(Calendar.AM_PM);
      calendar.clear(Calendar.MINUTE);
      calendar.clear(Calendar.SECOND);
      calendar.clear(Calendar.MILLISECOND);
      if(calendarField.equalsIgnoreCase("day"))
          calendar.add(Calendar.DAY_OF_MONTH,period);
      else if(calendarField.equalsIgnoreCase("month"))
          calendar.add(Calendar.MONTH,period);
      else if(calendarField.equalsIgnoreCase("year"))
          calendar.add(Calendar.YEAR,period);
      return calendar.getTime();
  }
	
	 public static boolean comaparDate(Date date1,Date date2,String operator){
		boolean result = false;
		if(operator.equals("="))
			result = date1.equals(date2);
		else if(operator.equals("<"))
			result =  date1.before(date2);
        else if(operator.equals(">"))
          result =  date1.after(date2);
        else if(operator.equals(">="))
          result =  date1.after(date2)||date1.equals(date2);
        else if(operator.equals("<="))
          result =  date1.before(date2)||date1.equals(date2);
		
		return result;
	}
	 
	 
	public String getUTCTodayDate(String Format, int days) {
		ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
		utc = utc.plusDays(days);
		return utc.format(DateTimeFormatter.ofPattern(Format));
	}
	 
	public String generateRandomDateBetween(String str_date1, String str_date2,
			String format) {

		String sRandomDate = null;
		try {

			DateFormat formatter = new SimpleDateFormat(format);
			Calendar cal = Calendar.getInstance();

			cal.setTime(formatter.parse(str_date1));
			Long value1 = cal.getTimeInMillis();

			cal.setTime(formatter.parse(str_date2));
			Long value2 = cal.getTimeInMillis();

			long value3 = (long) (value1 + Math.random() * (value2 - value1));
			cal.setTimeInMillis(value3);
			sRandomDate = formatter.format(cal.getTime());
		}

		catch (Exception e) {
			System.out.println("Parse Exception - Random date function :"
					+ e.getStackTrace());
		}
		return sRandomDate;

	}
	 
	public String getTodaysDateIncreaseBy(String Format, int days) {
		LocalDate today = LocalDate.now();
		LocalDate nextWeek = today.plus(days, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Format);
		return nextWeek.format(formatter);
	}
	 
	public String getTodaysDateDecreaseBy(String Format, int days) {
		LocalDate today = LocalDate.now();
		LocalDate next = today.minus(days, ChronoUnit.DAYS);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Format);
		return next.format(formatter);
	}
	
	public static String getCurrentdateInStringWithGivenFormate(String formate) {
		String date = new SimpleDateFormat(formate).format(new Date());		
		return date;
	}
	
	public static String stringToDate(String sDate, String sFormat) throws ParseException
	{
		 SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		 Date date = formatter.parse(sDate);
		 String newDateString = formatter.format(date);
		 return newDateString; }
	
	public String getDateIncreaseBy(String sDate, String Format, int days) throws ParseException {
		    
		SimpleDateFormat formatter = new SimpleDateFormat(Format);
		Date date = formatter.parse(sDate);
		LocalDate today =  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
		LocalDate nextWeek = today.plus(days, ChronoUnit.DAYS);
		DateTimeFormatter dtformatter = DateTimeFormatter.ofPattern(Format);
		return nextWeek.format(dtformatter);
		 
	}
}
