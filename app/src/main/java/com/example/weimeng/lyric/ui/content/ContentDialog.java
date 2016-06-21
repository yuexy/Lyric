package com.example.weimeng.lyric.ui.content;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.example.weimeng.lyric.R;
import com.example.weimeng.lyric.node.Lyric;
import com.example.weimeng.lyric.tools.ImageTools;
import com.example.weimeng.lyric.view.LyricImageView;

/**
 * Created by weimeng on 2016/6/21.
 */
public class ContentDialog extends Dialog
{
	private Context mContext;
	private Lyric lyric;

	private LyricImageView lyricImageView;
	private TextView contentTime;
	private TextView contentAuthor;
	private TextView contentContent;

	public ContentDialog(Context context, Lyric l)
	{
		super(context,  R.style.theme_dialog);

		this.mContext = context;
		this.lyric = l;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_dialog);

		init();
	}

	private void init()
	{
		lyricImageView = (LyricImageView) findViewById(R.id.content_image);
		contentTime = (TextView) findViewById(R.id.content_time);
		contentAuthor = (TextView) findViewById(R.id.content_author);
		contentContent = (TextView) findViewById(R.id.content_content);

		lyricImageView.setImageDrawable(new BitmapDrawable(ImageTools.string2Img(lyric.getImage())), 400);
		lyricImageView.setText(lyric.getLyric());

		contentTime.setText("" + lyric.getYear() + "-" + lyric.getMonth() + "-" + lyric.getDay() + " " + lyric.getHour() + ":" + lyric.getMinute());
		contentContent.setText(lyric.getComments());
		contentAuthor.setText(lyric.getAuthor());
	}
}
