package com.example.weimeng.lyric.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.weimeng.lyric.MyApplication;

/**
 * Created by weimeng on 2016/6/21.
 */
public class CanaroTextView extends TextView
{
	public CanaroTextView(Context context)
	{
		this(context, null);
	}

	public CanaroTextView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		setTypeface(MyApplication.canaroExtraBold);
	}

}