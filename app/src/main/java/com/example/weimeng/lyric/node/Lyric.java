package com.example.weimeng.lyric.node;

import cn.bmob.v3.BmobObject;

/**
 * Created by weimeng on 2016/6/21.
 */
public class Lyric extends BmobObject
{
	private String lyric;
	private String image;
	private String author;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private String comments;

	public void setLyric(String l)
	{
		this.lyric = l;
	}

	public String getLyric()
	{
		return lyric.replace("\\n", "\n");
	}

	public void setImage(String i)
	{
		this.image = i;
	}

	public String getImage()
	{
		return image;
	}

	public void setAuthor(String a)
	{
		this.author = a;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setYear(int y)
	{
		this.year = y;
	}

	public int getYear()
	{
		return year;
	}

	public void setMonth(int m)
	{
		this.month = m;
	}

	public int getMonth()
	{
		return month;
	}

	public void setDay(int d)
	{
		this.day = d;
	}

	public int getDay()
	{
		return day;
	}

	public void setHour(int h)
	{
		this.hour = h;
	}

	public int getHour()
	{
		return hour;
	}

	public void setMinute(int m)
	{
		this.minute = m;
	}

	public int getMinute()
	{
		return minute;
	}

	public void setComments(String c)
	{
		this.comments = c;
	}

	public String getComments()
	{
		return comments.replace("\\n", "\n");
	}
}
