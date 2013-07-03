package id.fruity.usg.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.US);
	private static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US);
	public static long ONE_WEEK = 1000 * 60 * 60 * 24 * 7; 
	
	public static GregorianCalendar getCalendarFromLong(long date){
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
	    calendar.setTimeInMillis(date);
	    return calendar;
	}
	
	public static String getStringOfCalendarFromLong(long date){
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return formatter.format(calendar.getTime());
	}
	
	public static String getSimpleStringOfCalendarFromLong(long date){
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		calendar.setTimeInMillis(date);
		return formatter2.format(calendar.getTime());
	}
	
	public static String getCurrentString(){
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
	
	public static String getSimpleCurrentString(){
		GregorianCalendar calendar = (GregorianCalendar)Calendar.getInstance();
		return formatter2.format(calendar.getTime());
	}
	
	public static Long getCurrentLong(){
		return (new Date()).getTime();
	}
	
}
