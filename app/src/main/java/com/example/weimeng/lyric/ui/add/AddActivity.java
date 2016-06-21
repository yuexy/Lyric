package com.example.weimeng.lyric.ui.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weimeng.lyric.BasicInfo;
import com.example.weimeng.lyric.R;
import com.example.weimeng.lyric.node.Lyric;
import com.example.weimeng.lyric.tools.DateTools;
import com.example.weimeng.lyric.tools.ImageTools;
import com.example.weimeng.lyric.tools.SharedPreferencesTools;
import com.example.weimeng.lyric.tools.StringTools;
import com.example.weimeng.lyric.ui.search.SearchActivity;
import com.example.weimeng.lyric.view.LoadingDialog;
import com.example.weimeng.lyric.view.LyricImageView;

import cn.bmob.v3.listener.SaveListener;
import zq.mdlib.mdwidget.ButtonFlat;
import zq.mdlib.mdwidget.ButtonIcon;

/**
 * Created by weimeng on 2016/6/21.
 */
public class AddActivity extends AppCompatActivity
{
	private ButtonIcon backButton;
	private ButtonIcon searchButton;
	private EditText lyricEditText;
	private EditText commentEditText;
	private ButtonFlat chooseButton;
	private LyricImageView previewImage;
	private ButtonFlat postButton;

	private Drawable drawable;
	private Bitmap bitmap;

	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity_layout);

		init();
	}

	private void init()
	{
		findViews();

		initWidget();

		loadingDialog = new LoadingDialog(AddActivity.this);
	}

	private void findViews()
	{
		backButton = (ButtonIcon) findViewById(R.id.add_back);
		searchButton = (ButtonIcon) findViewById(R.id.add_search);
		lyricEditText = (EditText) findViewById(R.id.add_lyric);
		commentEditText = (EditText) findViewById(R.id.add_comment);
		chooseButton = (ButtonFlat) findViewById(R.id.add_choose_button);
		previewImage = (LyricImageView) findViewById(R.id.add_preview_image);
		postButton = (ButtonFlat) findViewById(R.id.add_post_button);
	}

	private void initWidget()
	{
		backButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		searchButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String lyric = lyricEditText.getText().toString();

				if (StringTools.isEmpty(lyric))
				{
					Toast.makeText(AddActivity.this, "key", Toast.LENGTH_SHORT).show();
					return;
				}

				Intent intent = new Intent();
				intent.putExtra(BasicInfo.KEY, lyric);
				intent.setClass(AddActivity.this, SearchActivity.class);
				startActivity(intent);
			}
		});

		lyricEditText.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				//
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
//				System.out.println(s);
				previewImage.setText(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				//
			}
		});

		chooseButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 1);
			}
		});

		postButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (bitmap == null)
				{
					Toast.makeText(AddActivity.this, "Please select a picture", Toast.LENGTH_SHORT).show();
					return;
				}

				loadingDialog.show();

				String lyricStr = lyricEditText.getText().toString();
				String commentStr = commentEditText.getText().toString();

				if (StringTools.isEmpty(lyricStr))
				{
					Toast.makeText(AddActivity.this, "Please enter lyric", Toast.LENGTH_SHORT).show();
					return;
				}

				DateTools.getInstance().refreshTime();

				Lyric lyric = new Lyric();

				lyric.setLyric(lyricStr);

				System.out.println("begin");

				lyric.setImage(ImageTools.img2String(bitmap));

				System.out.println("end");

				lyric.setComments(commentStr);
				lyric.setAuthor(SharedPreferencesTools.getInstance().getUserId());

				lyric.setYear(DateTools.getInstance().getYear());
				lyric.setMonth(DateTools.getInstance().getMonth());
				lyric.setDay(DateTools.getInstance().getDay());
				lyric.setHour(DateTools.getInstance().getHour());
				lyric.setMinute(DateTools.getInstance().getMinute());

				lyric.save(AddActivity.this, new SaveListener()
				{
					@Override
					public void onSuccess()
					{
						loadingDialog.dismiss();
						Toast.makeText(AddActivity.this, "Success", Toast.LENGTH_SHORT).show();

						Intent intent = new Intent("com.example.weimeng.lyric.added");
						sendBroadcast(intent);

						finish();
					}

					@Override
					public void onFailure(int i, String s)
					{
						loadingDialog.dismiss();
						Toast.makeText(AddActivity.this, "Failure:" + s, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			Uri uri = data.getData();

			System.out.println("the path is: " + ImageTools.getRealPathFromURI_API19(AddActivity.this, uri));

//			System.out.println(ImageTools.getRealPathFromURI(AddActivity.this, uri));
			bitmap = ImageTools.compressImageFromFile(ImageTools.getRealPathFromURI_API19(AddActivity.this, uri));
			drawable = new BitmapDrawable(bitmap);
			previewImage.setImageDrawable(drawable, 400);
		}
	}
}
