package com.example.weimeng.lyric.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by weimeng on 2016/6/21.
 * 一个不能左右滑动的ViewPager
 */
public class UnScrollableViewPager extends ViewPager
{
	private boolean noScroll = true;

	public UnScrollableViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public UnScrollableViewPager(Context context)
	{
		super(context);
	}

	public void setNoScroll(boolean noScroll)
	{
		this.noScroll = noScroll;
	}

	@Override
	public void scrollTo(int x, int y)
	{
		super.scrollTo(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0)
	{
		/* return false;//super.onTouchEvent(arg0); */
		if (noScroll)
			return false;
		else
			return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0)
	{
		if (noScroll)
			return false;
		else
			return super.onInterceptTouchEvent(arg0);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll)
	{
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item)
	{
		super.setCurrentItem(item);
	}
}
