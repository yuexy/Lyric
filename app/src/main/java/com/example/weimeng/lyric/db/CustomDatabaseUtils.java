package com.example.weimeng.lyric.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by weimeng on 2016/6/21.
 */
public class CustomDatabaseUtils extends SQLiteOpenHelper
{
	public static final String DATABASE_NAME = "lyric_db";
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_LYRIC_NAME = "lyric";


	public CustomDatabaseUtils(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public CustomDatabaseUtils(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
	{
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		createTableLyric(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

	}

	private void createTableLyric(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_LYRIC_NAME
				+ "(lyric TEXT,"
				+ "image TEXT,"
				+ "author TEXT)" //author TEXT)
				+ "year INT,"
				+ "month INT,"
				+ "day INT,"
				+ "hour INT,"
				+ "minute INT,"
				+ "comments TEXT";

		db.execSQL(sql);
	}
}
