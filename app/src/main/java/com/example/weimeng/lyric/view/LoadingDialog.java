package com.example.weimeng.lyric.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.weimeng.lyric.R;

/**
 * Created by weimeng on 2016/6/21.
 */
public class LoadingDialog extends Dialog
{
	public LoadingDialog(Context context)
	{
		this(context, R.style.theme_dialog);
	}

	public LoadingDialog(Context context, int theme)
	{
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog);

		setCanceledOnTouchOutside(false);
	}

	/**
	 * 取消按键事件
	 *
	 * @param keyCode
	 * @param event
	 * @return
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}
}