package com.example.weimeng.lyric.ui.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.example.weimeng.lyric.BasicInfo;
import com.example.weimeng.lyric.R;

import zq.mdlib.mdwidget.ButtonIcon;

/**
 * Created by weimeng on 2016/6/22.
 */
public class SearchActivity extends AppCompatActivity
{
	private ButtonIcon searchBack;
	private WebView content;

	private String key;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_layout);

		init();
	}

	private void init()
	{
		findViews();

		searchBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		key = getIntent().getStringExtra(BasicInfo.KEY);

		url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=baidu&wd=" + key + "%20歌词";

		content.loadUrl(url);
	}

	private void findViews()
	{
		searchBack = (ButtonIcon) findViewById(R.id.search_back);
		content = (WebView) findViewById(R.id.search_webview);
	}
}
