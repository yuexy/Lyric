package com.example.weimeng.lyric.ui.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.weimeng.lyric.R;

import zq.mdlib.mdwidget.ButtonIcon;

/**
 * Created by weimeng on 2016/6/21.
 */
public class AboutActivity extends AppCompatActivity
{
	private ButtonIcon aboutBack;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);

		init();
	}

	private void init()
	{
		aboutBack = (ButtonIcon) findViewById(R.id.about_back);

		aboutBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}
}
