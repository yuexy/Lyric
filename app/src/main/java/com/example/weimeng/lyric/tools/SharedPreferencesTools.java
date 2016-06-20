package com.example.weimeng.lyric.tools;

import android.content.SharedPreferences;

import com.example.weimeng.lyric.BasicInfo;
import com.example.weimeng.lyric.MyApplication;

/**
 * Created by weimeng on 2016/6/20.
 */
public class SharedPreferencesTools
{
	private static SharedPreferencesTools sharedPreferencesTools;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	public static SharedPreferencesTools getInstance()
	{
		if (null == sharedPreferencesTools)
			sharedPreferencesTools = new SharedPreferencesTools();

		return sharedPreferencesTools;
	}

	private SharedPreferencesTools()
	{
		sharedPreferences = MyApplication.getInstance().getSharedPreferences(BasicInfo.SPID, 0);
		editor = sharedPreferences.edit();
	}

	public SharedPreferencesTools setUserId(String uid)
	{
		editor.putString(BasicInfo.SP_USER_ID, uid);
		editor.commit();

		return this;
	}

	public String getUserId()
	{
		return sharedPreferences.getString(BasicInfo.SP_USER_ID, "");
	}

	public SharedPreferencesTools setUserPwd(String pwd)
	{
		editor.putString(BasicInfo.SP_USER_PWD, pwd);
		editor.commit();

		return this;
	}

	public String getUserPwd()
	{
		return sharedPreferences.getString(BasicInfo.SP_USER_PWD, "");
	}
}
