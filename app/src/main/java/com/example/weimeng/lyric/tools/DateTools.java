package com.example.weimeng.lyric.tools;

import java.util.Calendar;

/**
 * Created by weimeng on 2016/6/21.
 */
public class DateTools
{
	private static DateTools dateTools;
	private Calendar calendar;

	public static DateTools getInstance()
	{
		if (null == dateTools)
			dateTools = new DateTools();


		return dateTools;
	}

	public void refreshTime()
	{
		calendar = Calendar.getInstance();
	}

	public int getYear()
	{
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth()
	{
		return calendar.get(Calendar.MONTH);
	}

	public int getDay()
	{
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public int getHour()
	{
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public int getMinute()
	{
		return calendar.get(Calendar.MINUTE);
	}
}
