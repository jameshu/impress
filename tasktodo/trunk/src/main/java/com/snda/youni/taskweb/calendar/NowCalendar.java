package com.snda.youni.taskweb.calendar;

import java.util.Date;
import java.util.Calendar;
/**
 * This is a simple calendar used for days around today.
 * @author huchangcheng
 *
 */
public class NowCalendar {
	
	public String startDateStr = "";
	public String endDateStr = "";
	
	//public long currentTime = 0; 
	public Date currentDate = null;

	public int currentDate_year = 0;
	public int currentDate_month = 0;
	
	
	public int startDateNum = 0;
	public int endDateNum = 0;
	
	private Calendar cal = Calendar.getInstance();
	
	public Date currentMonth_firstDate = null;
	public Date currentMonth_lastDate = null;
	
	public Date startDate = null;
	public Date endDate = null;
	
	public void init(){
		
		Date date = new Date();
		cal.setTime(date);
		cal.get(Calendar.MONTH); //当月
		cal.get(Calendar.DAY_OF_MONTH); //当月多少天
		
		Calendar c=  Calendar.getInstance();
		c.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		c.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		c.set(Calendar.DATE, 1);
		currentMonth_firstDate = c.getTime();
		//sunday = 1 , saturday = 7
		int firstDayofWeek = c.get(Calendar.DAY_OF_WEEK);
		
		c.roll(Calendar.DATE, -1);
		//c.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH));
		currentMonth_lastDate = c.getTime();
		int lastDayofWeek = c.get(Calendar.DAY_OF_WEEK);

		startDate = getStartDateFromFirstDate(currentMonth_firstDate,firstDayofWeek);
		endDate = getEndDateFromLastDate(currentMonth_lastDate,lastDayofWeek);
		
	}
	
	public static Date getStartDateFromFirstDate(Date firstDate,int dayofWeek){
		if(dayofWeek==1){ 
			dayofWeek = 7;
		}else{
			dayofWeek = dayofWeek-1;
		}
		
		Calendar c=  Calendar.getInstance();
		c.setTime(firstDate);
		c.add(Calendar.DATE, 1-dayofWeek);
		return c.getTime();
	}
	
	public static Date getEndDateFromLastDate(Date lastDate,int dayofWeek){
		if(dayofWeek==1){ 
			dayofWeek = 7;
		}else{
			dayofWeek = dayofWeek-1;
		}
		
		Calendar c=  Calendar.getInstance();
		c.setTime(lastDate);
		c.add(Calendar.DATE, (7-dayofWeek));
		return c.getTime();
	}
	
//	public static void main(String[] args){
//		NowCalendar nc = new NowCalendar();
//		nc.init();
//		
//		int interval = Integer.parseInt(""+(nc.endDate.getTime() - nc.startDate.getTime())/(3600*1000*24));
//	
//		Calendar c = Calendar.getInstance();
//		c.setTime(nc.startDate);
//		for (int i = 0; i <= interval; i++) {
//			if(i%7==0) System.out.println();
//			System.out.print("date = "+c.getTime()+" ,,,, ");
//			c.add(Calendar.DATE, 1);
//			
//			
//			
//		}	
//	}
	
}
