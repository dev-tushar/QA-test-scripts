package org.myProject.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<String>{

	public int compare(String date1, String date2) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");

		try {
			Date date3 = formatter.parse(date1);
			Date date4 = formatter.parse(date2);
			return date4.compareTo(date3);


		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

	}
}
