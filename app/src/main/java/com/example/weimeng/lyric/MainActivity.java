package com.example.weimeng.lyric;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import cn.bmob.v3.Bmob;

import android.view.View;
import android.widget.FrameLayout;

import com.example.weimeng.lyric.node.User;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.listener.SaveListener;


public class MainActivity extends AppCompatActivity
{
	private static final long RIPPLE_DURATION = 250;

	@InjectView(R.id.toolbar)
	Toolbar toolbar;

	@InjectView(R.id.root)
	FrameLayout root;

	@InjectView(R.id.content_hamburger)
	View contentHamburger;

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

		View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.menu_layout, null);
		root.addView(guillotineMenu);

		new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
				.setStartDelay(RIPPLE_DURATION)
				.setActionBarViewForAnimation(toolbar)
				.build();

		test();
	}

	private void initBmob()
	{
		Bmob.initialize(this, "fa98129ef2165693c9bfb743ecc47b48");
	}

	private void test()
	{
		User user = new User();
		user.setUsername("testid");
		user.setPassword("testpwddd");

		user.signUp(this, new SaveListener()
		{
			@Override
			public void onSuccess()
			{
				System.out.println("success");
			}

			@Override
			public void onFailure(int i, String s)
			{
				System.out.println("failure" + s);
			}
		});
	}
}
