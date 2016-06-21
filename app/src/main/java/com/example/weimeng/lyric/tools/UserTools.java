package com.example.weimeng.lyric.tools;

/**
 * Created by weimeng on 2016/6/21.
 */
public class UserTools
{
	public static void signOut()
	{
		SharedPreferencesTools.getInstance().setUserId("");
		SharedPreferencesTools.getInstance().setUserPwd("");
	}
}
