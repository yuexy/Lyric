package com.example.weimeng.lyric;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import cn.bmob.v3.Bmob;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weimeng.lyric.node.User;
import com.example.weimeng.lyric.tools.SharedPreferencesTools;
import com.example.weimeng.lyric.tools.UserTools;
import com.example.weimeng.lyric.ui.user.UserManageDialog;
import com.example.weimeng.lyric.view.CanaroTextView;
import com.example.weimeng.lyric.view.CircleImageView;
import com.example.weimeng.lyric.view.LyricImageView;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import zq.mdlib.mdwidget.ProgressBarCircular;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
	private static final long RIPPLE_DURATION = 250;

	private static final int HANDLER_LOGIN = 1;

	@InjectView(R.id.toolbar)
	Toolbar toolbar;

	@InjectView(R.id.root)
	FrameLayout root;

	@InjectView(R.id.content_hamburger)
	View contentHamburger;

	private View menuView;
	private CircleImageView headerImg;
	private RelativeLayout header;
	private CanaroTextView headerId;

	private RelativeLayout signOut;

	private ProgressBarCircular progressBarCircular;
	private LinearLayout wrongInfo;

	private SwipeRefreshLayout mSwipeRefreshLayout;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager linearLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initBmob();

		ButterKnife.inject(this);

		if (toolbar != null)
		{
			setSupportActionBar(toolbar);
			getSupportActionBar().setTitle(null);
		}

		menuView = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
		root.addView(menuView);

		new GuillotineAnimation.GuillotineBuilder(menuView, menuView.findViewById(R.id.guillotine_hamburger), contentHamburger)
				.setStartDelay(RIPPLE_DURATION)
				.setActionBarViewForAnimation(toolbar)
				.build().close();

		init();
	}

	private void initBmob()
	{
		Bmob.initialize(this, "fa98129ef2165693c9bfb743ecc47b48");
	}

	private void init()
	{
		findViews();

		initMenu();

		initRecyclerView();

		regBroadcastRecv();
	}

	private void findViews()
	{
		headerImg = (CircleImageView) menuView.findViewById(R.id.header);
		header = (RelativeLayout) menuView.findViewById(R.id.menu_header);
		headerId = (CanaroTextView) menuView.findViewById(R.id.header_userid);

		signOut = (RelativeLayout) menuView.findViewById(R.id.sign_out);

		progressBarCircular = (ProgressBarCircular) findViewById(R.id.lyric_progress_bar);
		wrongInfo = (LinearLayout) findViewById(R.id.lyric_wrong_info);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lyric_swipelayout);
		mRecyclerView = (RecyclerView) findViewById(R.id.lyric_recyclerview);
	}

	private void initMenu()
	{
		if (!"".equals(SharedPreferencesTools.getInstance().getUserId()))
		{
			headerId.setText(SharedPreferencesTools.getInstance().getUserId());
		}

		header.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new UserManageDialog(MainActivity.this).show();
			}
		});
		signOut.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				UserTools.signOut();
				Toast.makeText(MainActivity.this, "Sign out", Toast.LENGTH_SHORT).show();
				headerId.setText("Sign in");
			}
		});
	}

	private void initRecyclerView()
	{
		linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(linearLayoutManager);
		mRecyclerView.setHasFixedSize(true);
	}

	@Override
	public void onRefresh()
	{

	}

	///////////////////////////
	BroadcastReceiver refreshReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Message msg = handler.obtainMessage();
			msg.what = HANDLER_LOGIN;
			handler.sendMessage(msg);
		}
	};

	private void regBroadcastRecv()
	{
		IntentFilter intentFilter = new IntentFilter("com.example.weimeng.lyric.login");
		registerReceiver(refreshReceiver, intentFilter);
	}

	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case HANDLER_LOGIN:
				headerId.setText(SharedPreferencesTools.getInstance().getUserId());
				break;
			}
		}
	};
	///////////////////////////
}
