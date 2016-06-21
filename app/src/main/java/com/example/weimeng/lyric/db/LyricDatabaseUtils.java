package com.example.weimeng.lyric.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.weimeng.lyric.node.Lyric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weimeng on 2016/6/21.
 */
public class LyricDatabaseUtils extends CustomDatabaseUtils
{
	public static final String TABLE_NAME = CustomDatabaseUtils.TABLE_LYRIC_NAME;

	public static LyricDatabaseUtils lyricDatabaseUtils;

	public static LyricDatabaseUtils getInstance(Context context)
	{
		if (lyricDatabaseUtils == null)
			lyricDatabaseUtils = new LyricDatabaseUtils(context);

		return lyricDatabaseUtils;
	}

	public LyricDatabaseUtils(Context context)
	{
		super(context);
	}

	public LyricDatabaseUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}

	public boolean insert(List<Lyric> lyricList)
	{
		for (Lyric l : lyricList)
		{
			insert(l);
		}

		return true;
	}

	public boolean insert(Lyric lyric)
	{
		try
		{
			ContentValues values = new ContentValues();
			values.put("lyric", lyric.getLyric());
			values.put("image", lyric.getImage());
			values.put("author", lyric.getAuthor());
			values.put("year", lyric.getYear());
			values.put("month", lyric.getMonth());
			values.put("day", lyric.getDay());
			values.put("hour", lyric.getHour());
			values.put("minute", lyric.getMinute());
			values.put("comments", lyric.getComments());

			SQLiteDatabase db = getWritableDatabase();
			db.insert(TABLE_NAME, null, values);
			db.close();
		}
		catch (Exception e)
		{
			return false;
		}

		return true;
	}

	public List<Lyric> getAllLyric()
	{
		List<Lyric> lyricList = new ArrayList<>();

		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

		while (cursor.moveToNext())
		{
			Lyric lyric = new Lyric();
			lyric.setLyric(cursor.getString(cursor.getColumnIndex("lyric")));
			lyric.setImage(cursor.getString(cursor.getColumnIndex("image")));
			lyric.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
			lyric.setYear(cursor.getInt(cursor.getColumnIndex("year")));
			lyric.setMonth(cursor.getInt(cursor.getColumnIndex("month")));
			lyric.setDay(cursor.getInt(cursor.getColumnIndex("day")));
			lyric.setHour(cursor.getInt(cursor.getColumnIndex("hour")));
			lyric.setMinute(cursor.getInt(cursor.getColumnIndex("minute")));
			lyric.setComments(cursor.getString(cursor.getColumnIndex("comments")));

			lyricList.add(lyric);
		}

		db.close();

		return lyricList;
	}

	public void deleteAllLyric()
	{
		getWritableDatabase().delete(TABLE_NAME, null, null);
	}
}
