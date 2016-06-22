package com.example.weimeng.lyric.ui.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weimeng.lyric.R;
import com.example.weimeng.lyric.node.User;
import com.example.weimeng.lyric.tools.SharedPreferencesTools;
import com.example.weimeng.lyric.tools.StringTools;
import com.example.weimeng.lyric.view.UnScrollableViewPager;

import cn.bmob.v3.listener.SaveListener;
import zq.mdlib.mdwidget.ButtonFlat;

/**
 * Created by weimeng on 2016/6/21.
 * 用户登录的dialog
 */
public class UserManageDialog extends Dialog
{
	private static final int PAGE_LOGIN = 0;
	private static final int PAGE_SIGNUP = 1;
	private static final int PAGE_LOAD = 2;

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private UnScrollableViewPager mViewPager;

	private View loginView;
	private ButtonFlat loginOkButton;
	private ButtonFlat loginCancelButton;
	private ButtonFlat loginSignUpButton;
	private EditText loginUserId;
	private EditText loginUserPwd;

	private View signUpView;
	private ButtonFlat signUpOkButton;
	private ButtonFlat signUpCancelButton;
	private ButtonFlat signUpLoginButton;
	private EditText signUpUserId;
	private EditText signUpUserPwd;
	private EditText signUpUserEmail;

	private View loadingView;

	public UserManageDialog(Context context)
	{
		super(context, R.style.theme_dialog);

		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_manage_dialog);

		init();
	}

	private void init()
	{
		// 点击外围不会取消
		setCanceledOnTouchOutside(false);

		mViewPager = (UnScrollableViewPager) findViewById(R.id.user_manage_viewpager);

		initLoginView();
		initSignUpView();
		initLoadView();

		PagerAdapter pagerAdapter = new PagerAdapter()
		{
			@Override
			public int getCount()
			{
				return 3;
			}

			@Override
			public boolean isViewFromObject(View view, Object object)
			{
				return view == object;
			}

			public View getItem(int position)
			{
				if (position == PAGE_LOGIN)
					return loginView;
				else if (position == PAGE_SIGNUP)
					return signUpView;
				else
					return loadingView;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object)
			{
				//container.removeView(getItem(position));

			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				View view = getItem(position);

				if (view.getTag() == null)
				{
					view.setTag(true);
					container.addView(view);
				}

				return view;
			}
		};

		mViewPager.setAdapter(pagerAdapter);

		mViewPager.setCurrentItem(PAGE_LOGIN);
	}

	private void initLoginView()
	{
		loginView = mLayoutInflater.inflate(R.layout.user_manage_dialog_login, null, false);
		loginOkButton = (ButtonFlat) loginView.findViewById(R.id.user_manage_login_login);
		loginSignUpButton = (ButtonFlat) loginView.findViewById(R.id.user_manage_login_signup);
		loginCancelButton = (ButtonFlat) loginView.findViewById(R.id.user_manage_login_cancel);
		loginUserId = (EditText) loginView.findViewById(R.id.user_manage_login_userid);
		loginUserPwd = (EditText) loginView.findViewById(R.id.user_manage_login_pwd);

		if (!"".equals(SharedPreferencesTools.getInstance().getUserId()))
		{
			loginUserId.setText(SharedPreferencesTools.getInstance().getUserId());
			loginUserPwd.setText(SharedPreferencesTools.getInstance().getUserPwd());
		}

		initLoginViewButton();
	}

	private void initSignUpView()
	{
		signUpView = mLayoutInflater.inflate(R.layout.user_manage_dialog_signup, null, false);
		signUpOkButton = (ButtonFlat) signUpView.findViewById(R.id.user_manage_signup_signup);
		signUpLoginButton = (ButtonFlat) signUpView.findViewById(R.id.user_manage_signup_login);
		signUpCancelButton = (ButtonFlat) signUpView.findViewById(R.id.user_manage_signup_cancel);
		signUpUserId = (EditText) signUpView.findViewById(R.id.user_manage_signup_userid);
		signUpUserPwd = (EditText) signUpView.findViewById(R.id.user_manage_signup_pwd);
		signUpUserEmail = (EditText) signUpView.findViewById(R.id.user_manage_signup_email);

		initSignUpViewButton();
	}

	private void initLoadView()
	{
		loadingView = mLayoutInflater.inflate(R.layout.user_manage_dialog_load, null, false);
	}

	private void initLoginViewButton()
	{
		loginOkButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String userId = loginUserId.getText().toString();
				final String userPwd = loginUserPwd.getText().toString();

				if (StringTools.isEmpty(userId))
				{
					Toast.makeText(mContext, "wrong user id", Toast.LENGTH_SHORT).show();
					return;
				}

				if (StringTools.isEmpty(userPwd))
				{
					Toast.makeText(mContext, "wrong pwd", Toast.LENGTH_SHORT).show();
					return;
				}

				// 滑动到加载界面
				mViewPager.setCurrentItem(PAGE_LOAD, true);

				User user = new User();
				user.setUsername(userId);
				user.setPassword(userPwd);

				user.login(mContext, new SaveListener()
				{
					@Override
					public void onSuccess()
					{
						Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

						SharedPreferencesTools.getInstance().setUserId(userId);
						SharedPreferencesTools.getInstance().setUserPwd(userPwd);

						// send message
						Intent intent = new Intent("com.example.weimeng.lyric.login");
						mContext.sendBroadcast(intent);
						//

						dismiss();
					}

					@Override
					public void onFailure(int i, String s)
					{
						Toast.makeText(mContext, "Failure:" + s, Toast.LENGTH_SHORT).show();
						mViewPager.setCurrentItem(PAGE_LOGIN, true);
					}
				});
			}
		});

		loginCancelButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		});

		loginSignUpButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mViewPager.setCurrentItem(PAGE_SIGNUP, true);
			}
		});
	}

	private void initSignUpViewButton()
	{
		signUpCancelButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dismiss();
			}
		});

		signUpOkButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final String userId = signUpUserId.getText().toString();
				final String userPwd = signUpUserPwd.getText().toString();
				final String userEmail = signUpUserEmail.getText().toString();

				if (StringTools.isEmpty(userId))
				{
					Toast.makeText(mContext, "wrong user id", Toast.LENGTH_SHORT).show();
					return;
				}

				if (StringTools.isEmpty(userPwd))
				{
					Toast.makeText(mContext, "wrong pwd", Toast.LENGTH_SHORT).show();
					return;
				}

				User user = new User();
				user.setUsername(userId);
				user.setPassword(userPwd);

				if (!StringTools.isEmpty(userEmail))
				{
					if (!StringTools.isEmail(userEmail))
					{
						Toast.makeText(mContext, "wrong email address", Toast.LENGTH_SHORT).show();
						return;
					}
					else
					{
						user.setEmail(userEmail);
					}
				}

				mViewPager.setCurrentItem(PAGE_LOAD, true);

				user.signUp(mContext, new SaveListener()
				{
					@Override
					public void onSuccess()
					{
						Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

						SharedPreferencesTools.getInstance().setUserId(userId);
						SharedPreferencesTools.getInstance().setUserPwd(userPwd);

						// send message
						Intent intent = new Intent("com.example.weimeng.lyric.login");
						mContext.sendBroadcast(intent);
						//

						dismiss();
					}

					@Override
					public void onFailure(int i, String s)
					{
						Toast.makeText(mContext, "Failure:" + s, Toast.LENGTH_SHORT).show();
						mViewPager.setCurrentItem(PAGE_SIGNUP, true);
					}
				});
			}
		});

		signUpLoginButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mViewPager.setCurrentItem(PAGE_LOGIN, true);
			}
		});
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return false;
	}
}
