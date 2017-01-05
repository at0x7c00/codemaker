package com.lenovots.crm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {

	public static final String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMD = "yyyy-MM-dd";
	public static final String YM = "yyyy-MM";
	
	public static Map<String,String> patterns = new HashMap<String,String>();
	
	static {
		patterns.put("yyyy-MM-dd HH:mm:ss", "EN_YEAR_MONTH_DAY_HOUR_MIN_SEC");
		patterns.put("yyyy-MM-dd HH:mm", "EN_YEAR_MONTH_DAY_HOUR_MIN");
		patterns.put("yyyy-MM-dd HH", "EN_YEAR_MONTH_DAY_HOUR");
		patterns.put("yyyy-MM-dd", "EN_YEAR_MONTH_DAY");
		patterns.put("yyyy-MM", "EN_YEAR_MONTH");
		patterns.put("HH:mm:ss", "EN_HOUR_MIN_SEC");
		patterns.put("HH:mm", "EN_HOUR_MIN");
		patterns.put("yyyy年MM月dd日 HH时mm分ss秒", "CN_YEAR_MONTH_DAY_HOUR_MIN_SEC");
		patterns.put("yyyy年MM月dd日 HH时mm分", "CN_YEAR_MONTH_DAY_HOUR_MIN");
		patterns.put("yyyy年MM月dd日 HH时", "CN_YEAR_MONTH_DAY_HOUR");
		patterns.put("yyyy年MM月dd日", "CN_YEAR_MONTH_DAY");
		patterns.put("yyyy年MM月", "CN_YEAR_MONTH");
		patterns.put("HH时mm分ss秒", "CN_HOUR_MIN_SEC");
		patterns.put("HH时mm分", "CN_HOUR_MIN");
	}
    
	public static final long DAY = 24L * 60L * 60L * 1000L;// 一天的毫秒数
	
	
	public static Date tryStr2Date(String str){
		for(Map.Entry<String, String> entry : patterns.entrySet()){
			try{
				new SimpleDateFormat(entry.getKey()).parse(str);
			}catch(Exception e){
			}
		}
		return null;
	}
	
	public static Date getDayBeforeToday(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return getEndDate(sdf.format(c.getTime()));
	}
	
	/**
	 * 将输入框的字符串日期转成查询需要的开始日期 输入框日期的格式必须满足 yyyy-MM-dd
	 * 
	 * @param yyyyMMdd
	 *            输入的日期字符串
	 * @return 开始日期
	 */
	public static Date getBeginDate(String yyyyMMdd) {
		return strToDate(yyyyMMdd + " 00:00:00", YMD_HMS);
	}
	public static Date getBeginDate(Date date) {
		return getBeginDate(dateToStr(date, YMD));
	}
	
	/**
	 * 获取当天最后一秒的时刻
	 * @return
	 */
	public static Date endOfToday(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String prefix = sdf.format(date);
		try {
			return sdf2.parse(prefix+" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date startOfBefore(int hours){
		
		return null;
	}
	
	/**
	 * 获取本月第一天日期
	 * @return
	 */
	public static String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(YMD);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// ��Ϊ��ǰ�µ�1��
		str = sdf.format(lastDate.getTime());
		return str;
	}
	/**
	 * 获取指定月份第一天日期
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date getFirstDayOfMonth(String dateStr,String pattern) {
		Date date = strToDate(dateStr, pattern);
		return getFirstDayOfMonth(date);
	}
	/**
	 * 获取指定月份第一天日期
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar firstDate = Calendar.getInstance();
		firstDate.setTime(date);
		firstDate.set(Calendar.DATE,1);
		return firstDate.getTime();
	}
	/**
	 * 获取指定月份最后一天日期
	 * @param dateStr 日期字符串
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date getLastDayOfMonth(String dateStr,String pattern) {
		Date date = strToDate(dateStr, pattern);
		return getLastDayOfMonth(date);
	}
	/**
	 * 获取指定月份最后一天日期
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MONTH,1);//月份加一
		lastDate.set(Calendar.DATE,1);//下月第一天
		lastDate.add(Calendar.DATE,-1);//倒退一天，即上月最后一天
		return lastDate.getTime();
	}

	/**
	 * 获得本周一的日期
	 * 
	 * @return
	 */
	public static String getMondayOFWeek() {
		int mondayPlus =getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获得本季度第一天
	 * @param month
	 * @return
	 */
	public static Date getThisSeasonFirstTime() {
		SimpleDateFormat sdf =new SimpleDateFormat(YMD);
		Date date=new Date();
		return getSessionFirstTime(sdf.format(date));
	}
	public static Date getSessionFirstTime(Date d){
		SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		String seasonDate=null;
		int month=d.getMonth()+1;
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(d);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		seasonDate = years_value + "-" + start_month + "-" + start_days;
		try {
			return sdf.parse(seasonDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Date getSessionFirstTime(String date){
		SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		try {
			return getSessionFirstTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 获取本年第一天的日期
	 * @return
	 */
	public static  String getCurrentYearFirst() {
		return getCurrentYearFirst(new Date());
	}
	
	public static  String getCurrentYearFirst(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		
		SimpleDateFormat sdf = new SimpleDateFormat(YMD);
		String preYearDay = sdf.format(cal.getTime());
		return preYearDay;
	}
	private static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 获取某年某月的最后一天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 最后一天
	 */
	private static  int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 是否闰年
	 * 
	 * @param year
	 *            年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 获得当前日期与本周一相差的天数
	 * 
	 * @return
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1; // 因为按中国礼拜一作为第一天所以这里减1
		
		int result;
		if (dayOfWeek == 0) {
			result=-7;
		} else {
			result =  1-dayOfWeek;
		}
		if(result>0){
			result=0-result;
		}
		return result;
	}

	/**
	 * 将输入框的字符串日期转成查询需要的结束日期 输入框日期的格式必须满足 yyyy-MM-dd
	 * 
	 * @param yyyyMMdd
	 *            输入的日期字符串
	 * @return 结束日期
	 */
	public static Date getEndDate(String yyyyMMdd) {
		return strToDate(yyyyMMdd + " 23:59:59", YMD_HMS);
	}
	/**
	 * 将输入框的字符串日期转成查询需要的结束日期 输入框日期的格式必须满足 yyyy-MM-dd
	 * 
	 * @param yyyyMMdd
	 *            输入的日期字符串
	 * @return 结束日期
	 */
	public static Date getEndDate(Date date) {
		return getEndDate(dateToStr(date, YMD));
	}

	/**
	 * 将字符串格式的日期以指定的格式转成日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 日期
	 */
	public static Date strToDate(String dateStr, String pattern) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 把日期转换成指定格式的字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		String dateStr = null;
		try {
			dateStr = new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("把日期转成指定格式字符串失败");
		}
		return dateStr;
	}

	/**
	 * 计算两个字符串格式的日期相差多少天 日期格式必须满足 yyyy-MM-dd
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @return 相差的天数
	 */
	public static int diffDay(String begin, String end) {
		int day = 0;
		Long ms = null;
		try {
			ms = DateUtil.getEndDate(end).getTime()
					- DateUtil.getBeginDate(begin).getTime();
			day = ((Long) (ms / DAY)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("计算字符串日期相差天数失败");
		}
		return day;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getTodayDate(){
		return new SimpleDateFormat(YMD).format(new Date());
	}
	
	
	/**
	 * 获取当前周的每一天日期列表 
	 * @return
	 */
	public static List<Date[]> getDayInCurrentWeek(){
		List<Date[]> list=new ArrayList<Date[]>();
		String monday=getMondayOFWeek();
		SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		SimpleDateFormat sdf2=new SimpleDateFormat(YMD_HMS);
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(monday));
			for(int i=0;i<7;i++){
				Date[] date=new Date[2];
				date[0]=cal.getTime();
				date[1]=sdf2.parse(sdf.format(date[0])+" 23:59:59");
				cal.add(Calendar.DATE, 1);
				list.add(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取当前周的每一天日期列表 
	 * @return
	 */
	public static List<Date[]> getDayInCurrentMonth(){
		List<Date[]> list=new ArrayList<Date[]>();
		String firstday=getFirstDayOfMonth();
		SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		SimpleDateFormat sdf2=new SimpleDateFormat(YMD_HMS);
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(firstday));
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			Date lastDay=cal.getTime();//获取本月最后一天日期
			cal.setTime(sdf.parse(firstday));
			while(cal.getTime().getTime()<=lastDay.getTime()){
				Date[] date=new Date[2];
				date[0]=cal.getTime();
				date[1]=sdf2.parse(sdf.format(date[0])+" 23:59:59");
				cal.add(Calendar.DATE, 1);
				list.add(date);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取本季度每个月的开始日期和结束日期
	 * @return
	 */
	public static List<Date[]>getMonthInCurrentSeason(){
		List<Date[]> list=new ArrayList<Date[]>();
		Date firstInSession=getThisSeasonFirstTime();
		Calendar cal=Calendar.getInstance();
		cal.setTime(firstInSession);
		for(int i=0;i<3;i++){
			Date date[]=new Date[2];
			date[0]=cal.getTime();
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			date[1]=cal.getTime();
			cal.add(Calendar.DATE, 1);
			list.add(date);
		}
		return list;
	}
    /**
     * 获取当前月的开始时间和结束时间
     * @return
     */
    public static Date[] getFirstAndLastDayInCurrentMonth(){
    	String first=getFirstDayOfMonth();
    	Date date[]=new Date[2];
    	SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(first));
			date[0]=cal.getTime();
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);
			date[1]=cal.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
    }
    /**
     * 获取本年每个月的开始日期和结束日期
     * @return
     */
    public static List<Date[]> getMonthInCurrentYear(){
    	List<Date[]> list=new ArrayList<Date[]>();
		String firstInSession=getCurrentYearFirst();
		SimpleDateFormat sdf=new SimpleDateFormat(YMD);
		Calendar cal=Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(firstInSession));
			for(int i=0;i<12;i++){
				Date date[]=new Date[2];
				date[0]=cal.getTime();
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DATE, -1);
				date[1]=cal.getTime();
				cal.add(Calendar.DATE, 1);
				list.add(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
    }
    /**
     * 获取本年每个季度的开始日期和结束日期
     * @return
     */
    public static List<Date[]> getSeasonInCurrentYear(){
    	List<Date[]> list=new ArrayList<Date[]>();
    	String firstInSession=getCurrentYearFirst();
    	SimpleDateFormat sdf=new SimpleDateFormat(YMD);
    	Calendar cal=Calendar.getInstance();
    	try {
    		cal.setTime(sdf.parse(firstInSession));
    		for(int i=0;i<12;i+=3){
    			Date date[]=new Date[2];
    			date[0]=cal.getTime();
    			cal.add(Calendar.MONTH, 3);
    			cal.add(Calendar.DATE, -1);
    			date[1]=cal.getTime();
    			cal.add(Calendar.DATE, 1);
    			list.add(date);
    		}
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return list;
    }
    /**
     * 获取自定义开始结束时间的时间段信息
     * @param startTime  
     * @param endTime
     * @param type 0:不间隔，  1:期间的每一天， 2:期间的每一月, 3:期间的每一季度, 4:期间的一年
     * @return
     */
    public static List<Date[]> getDateOfCustomers(String startTime,String endTime,int type){
    	List<Date[]> list=new ArrayList<Date[]>();
    	SimpleDateFormat sdf = new SimpleDateFormat(YMD);
    	SimpleDateFormat sdf2=new SimpleDateFormat(YMD_HMS);
    	Calendar cal=Calendar.getInstance();
    	Date start=null;
    	Date end=null;
    	try {
			start=sdf.parse(startTime);
			end=sdf.parse(endTime);
		    if(start.getTime()>end.getTime()){
		    	return list;
		    }
		}catch (ParseException e) {
			e.printStackTrace();
			return list;
		}
		cal.setTime(start);
    	switch(type){
    	    case 0://期间的开始时间和结束时间
    	    	while(1==1){
	    	    	Date[] date=new Date[2];
	    	    	try {
						date[0]=sdf2.parse(startTime+" 00:00:00");
						date[1]=sdf2.parse(endTime+" 23:59:59");
					} catch (ParseException e) {
						e.printStackTrace();
					}
	    	    	list.add(date);
	    	    	break;
    	    	}
    	    	break;
	    	case 1://期间每一天 
		    	while(cal.getTime().getTime()<=end.getTime()){
		    		Date[] date=new Date[2];
		    		date[0]=cal.getTime();
		    		try {
						date[1]=sdf2.parse(sdf.format(cal.getTime())+" 23:59:59");
					} catch (ParseException e){
						e.printStackTrace();
					}
		    		cal.add(Calendar.DATE, 1);
		    		list.add(date);
		    	}
	    		break;
	    	case 2://期间每一月
	    		int index=0;
	    		while(cal.getTime().getTime()<=end.getTime()){
		    		Date[] date=new Date[2];
		    		date[0]=cal.getTime();
		    		if(index==0){
			    		cal.set(Calendar.DATE, 1);
		    		}
		    		cal.add(Calendar.MONTH, 1);
		    		cal.add(Calendar.DATE, -1);
		    		index++;
		    		if(cal.getTime().getTime()>end.getTime()){
		    			try {
							date[1]=sdf2.parse(sdf.format(end)+" 23:59:59");
						}catch (ParseException e){
							e.printStackTrace();
						}
		    		}else{
		    			try {
							date[1]=sdf2.parse(sdf.format(cal.getTime())+" 23:59:59");
						} catch (ParseException e){
							e.printStackTrace();
						}
		    		}
		    		cal.add(Calendar.DATE, 1);
		    		list.add(date);
		    	}
	    		break;
	    	case 3://期间每一季度
	    		while(cal.getTime().getTime()<=end.getTime()){
		    		Date[] date=new Date[2];
		    		date[0]=cal.getTime();
		    		Date d=getSessionFirstTime(date[0]);
		    		cal.setTime(d);
		    		cal.add(Calendar.MONTH, 3);
		    		cal.add(Calendar.DATE, -1);
		    		if(cal.getTime().getTime()>end.getTime()){
		    			try {
							date[1]=sdf2.parse(sdf.format(end)+" 23:59:59");
						} catch (ParseException e){
							e.printStackTrace();
						}
		    		}else{
		    			try {
							date[1]=sdf2.parse(sdf.format(cal.getTime())+" 23:59:59");
						} catch (ParseException e){
							e.printStackTrace();
						}
		    		}
		    		cal.add(Calendar.DATE, 1);
		    		list.add(date);
		    	}
	    		break;
	    	case 4://期间每一年
	    		while(cal.getTime().getTime()<=end.getTime()){
		    		Date[] date=new Date[2];
		    		date[0]=cal.getTime();
					try {
						cal.setTime(sdf.parse(getCurrentYearFirst(date[0])));
						cal.add(Calendar.YEAR, 1);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
		    		cal.add(Calendar.DATE, -1);
		    		if(cal.getTime().getTime()>end.getTime()){
		    			try {
							date[1]=sdf2.parse(sdf.format(end)+" 23:59:59");
						} catch (ParseException e){
							e.printStackTrace();
						}
		    		}else{
		    			try {
							date[1]=sdf2.parse(sdf.format(cal.getTime())+" 23:59:59");
						} catch (ParseException e){
							e.printStackTrace();
						}
		    		}
		    		cal.add(Calendar.DATE, 1);
		    		list.add(date);
		    	}
	    		break;
    	}
    	return list;
    }
    /**
     * 计算date相对于beginDate而言是第几周,如果beginDate为null，则计算date是一年中的第几周
     * @param beginDate
     * @param date
     * @return
     */
    public static int getWeekCount(Date beginDate,Date date){
    	Calendar date1 = Calendar.getInstance();
    	date1.setTime(beginDate);
    	Calendar date2 = Calendar.getInstance();
    	date2.setTime(date);
    	return getWeeksBetweenDates(date1, date2) + 1;
//    	if(beginDate == null || date == null || beginDate.after(date))
//    		return -1;
//    	Date date1 = getFirstDateOfWeek(beginDate);
//    	Date date2 = getFirstDateOfWeek(date);
//    	long timeDiff = date2.getTime() - date1.getTime();
//    	System.out.println(timeDiff);
//    	System.out.println(DAY * 7);
//    	if(timeDiff % DAY * 7 == 0)
//    		return new Long(timeDiff / (DAY * 7)).intValue() + 1;
//    	else
//    		return new Long(timeDiff / (DAY * 7)).intValue() + 2;
    }
    
    public static Date getFirstDateOfWeek(Date date){
    	if(date == null)
    		return null;
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
    	c.set(Calendar.DAY_OF_WEEK, 3);
    	return c.getTime();
    }
    
    /**
    * @NOTE:Java中的日期起始于1900年1月1日
    * 两年之间的交界处周数从1开始计算，即使12月31号是星期六，也归并到下一年的第1周中。
    *（这跟手机中不同，手机中采用类似的“四舍五入”处理，如果12月31号是 < 周四，归并到下一年的第1周，如果是 >= 周四，归并到本年的最后一周。）
    */
    public static int getWeeksBetweenDates(Calendar dateFrom, Calendar dateTo) {
		dateFrom.setFirstDayOfWeek(Calendar.MONDAY);
		dateTo.setFirstDayOfWeek(Calendar.MONDAY);
		int yearFrom = dateFrom.get(Calendar.YEAR);
		int yearTo = dateTo.get(Calendar.YEAR);
		int weeksBetween = 0;
		// the year of dateFrom
		int weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR);
		if (weekFrom == 1 && dateFrom.get(Calendar.MONTH) == Calendar.DECEMBER)// 腊月
		{
			dateFrom.set(yearFrom, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween -= dateFrom.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween -= weekFrom;
		// years between dates
		for (int i = 1; i <= yearTo - yearFrom; i++) {
			Calendar _lastDay = Calendar.getInstance();
			_lastDay.setFirstDayOfWeek(Calendar.MONDAY);
			_lastDay.set(yearFrom + i, 11, 25, 23, 59, 59);
			weeksBetween += _lastDay.get(Calendar.WEEK_OF_YEAR);
		}
		// the year of dateTo
		int weekTo = dateTo.get(Calendar.WEEK_OF_YEAR);
		if (weekTo == 1 && dateTo.get(Calendar.MONTH) == Calendar.DECEMBER)// 腊月
		{
			dateTo.set(yearTo, Calendar.DECEMBER, 25, 23, 59, 59);
			weeksBetween += dateTo.get(Calendar.WEEK_OF_YEAR);
		}
		weeksBetween += weekTo;
		return weeksBetween;
	}

	public static Object format(Date addTime, String string) {
		return new SimpleDateFormat(string).format(addTime);
	}


}
