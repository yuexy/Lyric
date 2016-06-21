package com.example.weimeng.lyric.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.weimeng.lyric.R;

import zq.mdlib.mdviewpager.MaterialViewPagerImageHeader;

/**
 * Created by weimeng on 2016/6/21.
 */
public class LyricImageView extends FrameLayout
{
	private Context mContext;

	private MaterialViewPagerImageHeader materialViewPagerImageHeader;

	private TextView lyricText;

	public LyricImageView(Context context)
	{
		super(context);

		this.mContext = context;
	}

	public LyricImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		this.mContext = context;
	}

	public LyricImageView(Context context, AttributeSet attrs, int defStylerAttr)
	{
		super(context, attrs, defStylerAttr);

		this.mContext = context;
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		addView(LayoutInflater.from(mContext).inflate(R.layout.lyric_imageview_base_layout, this, false));

		materialViewPagerImageHeader = (MaterialViewPagerImageHeader) findViewById(R.id.lyric_imageview);
		lyricText = (TextView) findViewById(R.id.lyric_textview);
	}

	public void setImageURL(final String URL, final int fadeDuration)
	{
		materialViewPagerImageHeader.setImageURL(URL, fadeDuration);
	}

	public void setImageDrawable(final Drawable drawable, final int fadeDuration)
	{
		materialViewPagerImageHeader.setImageDrawable(drawable, fadeDuration);
	}

	public void setText(String text)
	{
		lyricText.setText(text);
	}
}
