package com.example.weimeng.lyric;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Created by weimeng on 2016/6/20.
 */
public class MyApplication extends Application
{
	private static final String TAG = MyApplication.class.getSimpleName();
	private static MyApplication myApplication = null;

	private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
	public static Typeface canaroExtraBold;

	public static MyApplication getInstance()
	{
		return myApplication;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		myApplication = this;
		initTypeface();
	}

	private void initTypeface()
	{
		canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);

	}
}
