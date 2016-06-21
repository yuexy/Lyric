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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weimeng.lyric.db.LyricDatabaseUtils;
import com.example.weimeng.lyric.node.Lyric;
import com.example.weimeng.lyric.node.User;
import com.example.weimeng.lyric.tools.SharedPreferencesTools;
import com.example.weimeng.lyric.tools.UserTools;
import com.example.weimeng.lyric.ui.about.AboutActivity;
import com.example.weimeng.lyric.ui.add.AddActivity;
import com.example.weimeng.lyric.ui.user.UserManageDialog;
import com.example.weimeng.lyric.view.CanaroTextView;
import com.example.weimeng.lyric.view.CircleImageView;
import com.example.weimeng.lyric.view.LoadingDialog;
import com.example.weimeng.lyric.view.LyricImageView;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import zq.mdlib.mdwidget.ProgressBarCircular;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
{
	private static final long RIPPLE_DURATION = 250;

	private static final int HANDLER_LOGIN = 1;
	private static final int HANDLER_ADDED = 2;

	@InjectView(R.id.toolbar)
	Toolbar toolbar;

	@InjectView(R.id.root)
	FrameLayout root;

	@InjectView(R.id.content_hamburger)
	View contentHamburger;

	private ImageView addButton;

	private View menuView;
	private CircleImageView headerImg;
	private RelativeLayout header;
	private CanaroTextView headerId;

	private RelativeLayout signOut;
	private RelativeLayout about;

	private ProgressBarCircular progressBarCircular;
	private LinearLayout wrongInfo;

	private SwipeRefreshLayout mSwipeRefreshLayout;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager linearLayoutManager;

	private GuillotineAnimation guillotineAnimation;

	private BmobQuery<Lyric> lyricBmobQuery;
	private List<Lyric> lyricList;

	private LyricListAdapter lyricListAdapter;

	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initBmob();//初始化服务器

		ButterKnife.inject(this);

		if (toolbar != null)
		{
			setSupportActionBar(toolbar);
			getSupportActionBar().setTitle(null);
		}

		menuView = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
		root.addView(menuView);

		guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(menuView, menuView.findViewById(R.id.guillotine_hamburger), contentHamburger)
				.setStartDelay(RIPPLE_DURATION)
				.setActionBarViewForAnimation(toolbar)
				.build();

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

		lyricBmobQuery = new BmobQuery<Lyric>();
		lyricBmobQuery.setLimit(5);

		lyricList = new ArrayList<>();

		lyricListAdapter = new LyricListAdapter(MainActivity.this, lyricList);
		mRecyclerView.setAdapter(lyricListAdapter);

		// 首先尝试从网络获取资源
		progressBarCircular.setVisibility(View.VISIBLE);
		mSwipeRefreshLayout.setRefreshing(true);
		mSwipeRefreshLayout.setOnRefreshListener(this);

		lyricBmobQuery.findObjects(MainActivity.this, new FindListener<Lyric>()
		{
			@Override
			public void onSuccess(List<Lyric> list)
			{
				mSwipeRefreshLayout.setRefreshing(false);
				progressBarCircular.setVisibility(View.GONE);
				for (Lyric l : list)
				{
					lyricList.add(l);
				}

				lyricListAdapter.notifyListChange(lyricList);

				LyricDatabaseUtils.getInstance(MainActivity.this).deleteAllLyric();
				LyricDatabaseUtils.getInstance(MainActivity.this).insert(lyricList);
			}

			@Override
			public void onError(int i, String s)
			{
				Toast.makeText(MainActivity.this, "check your networkd", Toast.LENGTH_SHORT).show();

				getDataFromDB();

				mSwipeRefreshLayout.setRefreshing(false);
				progressBarCircular.setVisibility(View.GONE);

				if (lyricList.size() != 0)
				{
					lyricListAdapter.notifyListChange(lyricList);
				}
			}
		});

		//////
//		getDataFromDB();
//
//		mSwipeRefreshLayout.setRefreshing(false);
//		progressBarCircular.setVisibility(View.GONE);
//
//		if (lyricList.size() != 0)
//		{
//			lyricListAdapter.notifyListChange(lyricList);
//		}
		//////
	}

	private void findViews()
	{
		headerImg = (CircleImageView) menuView.findViewById(R.id.header);
		header = (RelativeLayout) menuView.findViewById(R.id.menu_header);
		headerId = (CanaroTextView) menuView.findViewById(R.id.header_userid);

		signOut = (RelativeLayout) menuView.findViewById(R.id.sign_out);
		about = (RelativeLayout) menuView.findViewById(R.id.about);

		progressBarCircular = (ProgressBarCircular) findViewById(R.id.lyric_progress_bar);
		wrongInfo = (LinearLayout) findViewById(R.id.lyric_wrong_info);

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.lyric_swipelayout);
		mRecyclerView = (RecyclerView) findViewById(R.id.lyric_recyclerview);

		addButton = (ImageView) findViewById(R.id.main_add_button);

		loadingDialog = new LoadingDialog(MainActivity.this);
	}

	private void initMenu()
	{
		if (!"".equals(SharedPreferencesTools.getInstance().getUserId()))
		{
			headerId.setText(SharedPreferencesTools.getInstance().getUserId());
			guillotineAnimation.close();
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

		about.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AboutActivity.class);
				startActivity(intent);
			}
		});

		addButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if ("".equals(SharedPreferencesTools.getInstance().getUserId()))
				{
					Toast.makeText(MainActivity.this, "please sign in", Toast.LENGTH_SHORT).show();
					guillotineAnimation.open();
					return;
				}

				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AddActivity.class);
				startActivity(intent);
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
		lyricBmobQuery.findObjects(MainActivity.this, new FindListener<Lyric>()
		{
			@Override
			public void onSuccess(List<Lyric> list)
			{
				mSwipeRefreshLayout.setRefreshing(false);
				progressBarCircular.setVisibility(View.GONE);
				lyricList.clear();
				for (Lyric l : list)
				{
					lyricList.add(l);
				}

				lyricListAdapter.notifyListChange(lyricList);

				LyricDatabaseUtils.getInstance(MainActivity.this).deleteAllLyric();
				LyricDatabaseUtils.getInstance(MainActivity.this).insert(lyricList);
			}

			@Override
			public void onError(int i, String s)
			{
				mSwipeRefreshLayout.setRefreshing(false);
				progressBarCircular.setVisibility(View.GONE);
				Toast.makeText(MainActivity.this, "check your networkd", Toast.LENGTH_SHORT).show();
			}
		});

		//////
//		getDataFromDB();
//
//		if (lyricList.size() != 0)
//		{
//			lyricListAdapter.notifyListChange(lyricList);
//		}
//
//		mSwipeRefreshLayout.setRefreshing(false);
//		progressBarCircular.setVisibility(View.GONE);
		//////
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

	BroadcastReceiver addedReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Message msg = handler.obtainMessage();
			msg.what = HANDLER_ADDED;
			handler.sendMessage(msg);
		}
	};

	private void regBroadcastRecv()
	{
		IntentFilter intentFilter = new IntentFilter("com.example.weimeng.lyric.login");
		registerReceiver(refreshReceiver, intentFilter);

		IntentFilter intentFilterAdded = new IntentFilter("com.example.weimeng.lyric.added");
		registerReceiver(addedReceiver, intentFilterAdded);
	}

	Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case HANDLER_LOGIN:
				headerId.setText(SharedPreferencesTools.getInstance().getUserId());
				guillotineAnimation.close();
				break;

			case HANDLER_ADDED:
				//
				onAdded();
				break;
			}
		}
	};
	///////////////////////////

	private void getDataFromIntenet()
	{
		lyricBmobQuery.findObjects(MainActivity.this, new FindListener<Lyric>()
		{
			@Override
			public void onSuccess(List<Lyric> list)
			{
				for (Lyric l : list)
				{
					lyricList.add(l);
				}
			}

			@Override
			public void onError(int i, String s)
			{

			}
		});
	}

	private void getDataFromDB()
	{
		lyricList = LyricDatabaseUtils.getInstance(MainActivity.this).getAllLyric();
	}

	private void onAdded()
	{
		loadingDialog.show();

		lyricBmobQuery.findObjects(MainActivity.this, new FindListener<Lyric>()
		{
			@Override
			public void onSuccess(List<Lyric> list)
			{
				loadingDialog.dismiss();
				lyricList.clear();
				for (Lyric l : list)
				{
					lyricList.add(l);
				}

				lyricListAdapter.notifyListChange(lyricList);

				LyricDatabaseUtils.getInstance(MainActivity.this).deleteAllLyric();
				LyricDatabaseUtils.getInstance(MainActivity.this).insert(lyricList);
			}

			@Override
			public void onError(int i, String s)
			{
				loadingDialog.dismiss();
				Toast.makeText(MainActivity.this, "check your networkd", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
