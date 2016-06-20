package zq.mdlib.mdviewpager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 2015/5/6.
 */
public class MaterialViewPager extends FrameLayout
{
	private ViewGroup headerBackgroundContainer;        //放置背景图片
	private ViewGroup pagerTitleStripContainer;
	private ViewGroup logoContainer;                    //放置logo图片

	protected ViewPager viewPager;

	protected View headerBackground;
	protected View topLayout;

	protected View topBackground;

	protected MaterialViewPagerHeader materialViewPagerHeader;

	public MaterialViewPager(Context context)
	{
		super(context);
	}

	public MaterialViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		MaterialViewPagerSetting.getInstance().handleAttributes(context, attrs);
	}

	public MaterialViewPager(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		MaterialViewPagerSetting.getInstance().handleAttributes(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MaterialViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		MaterialViewPagerSetting.getInstance().handleAttributes(context, attrs);
	}

	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();

		MaterialViewPagerSetting setting = MaterialViewPagerSetting.getInstance();

		addView(LayoutInflater.from(getContext()).inflate(R.layout.material_viewpager_base_layout,
				this, false));

		headerBackgroundContainer = (ViewGroup) findViewById(R.id.headerBackgroundContainer);
		pagerTitleStripContainer = (ViewGroup) findViewById(R.id.pagerTitleStripContainer);
		logoContainer = (ViewGroup) findViewById(R.id.logoContainer);

		headerBackground = findViewById(R.id.headerBackground);

		topLayout = findViewById(R.id.top_layout);
		topBackground = findViewById(R.id.top_background);

		viewPager = (ViewPager) findViewById(R.id.viewPager);

		if (setting.headerLayoutId != -1)
		{
			headerBackgroundContainer.addView(
					LayoutInflater.from(getContext()).inflate(setting.headerLayoutId,
							headerBackgroundContainer, false));
		}

		if (isInEditMode())
		{
			pagerTitleStripContainer.addView(LayoutInflater.from(getContext()).inflate(
					R.layout.tools_material_viewpager_pagertitlestrip, pagerTitleStripContainer,
					false));
		}
		else
		{
			if (setting.pagerTitleStripId != -1)
			{
				pagerTitleStripContainer.addView(
						LayoutInflater.from(getContext()).inflate(setting.pagerTitleStripId,
								pagerTitleStripContainer, false));
			}
		}

		if (setting.logoLayoutId != -1)
		{
			logoContainer.addView(
					LayoutInflater.from(getContext()).inflate(setting.logoLayoutId, logoContainer,
							false));
			if (setting.logoMarginTop != 0)
			{
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) logoContainer.getLayoutParams();
				layoutParams.setMargins(0, setting.logoMarginTop, 0, 0);
				logoContainer.setLayoutParams(layoutParams);
			}
		}

		initialiseHeights();

		if (!isInEditMode())
		{
			materialViewPagerHeader = MaterialViewPagerHeader.getInstance(
					getContext()).withHeaderBackground(headerBackground).withPagerTitleStrip(
					pagerTitleStripContainer).withStatusBackground(
					findViewById(R.id.statusBackground)).withTopLayout(topLayout).withTopBackground(
					topBackground).withLogo(logoContainer);

			MaterialViewPagerHelper.register((Activity) getContext(),
					new MaterialViewPagerAnimator(this));
		}
		else
		{
			View simple = LayoutInflater.from(getContext()).inflate(R.layout.tools_list_item,
					pagerTitleStripContainer, false);

			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) simple.getLayoutParams();
			int marginTop = Math.round(Utils.dpToPx(setting.headerHeight + 10, getContext()));
			params.setMargins(0, marginTop, 0, 0);
			super.setLayoutParams(params);

			addView(simple);
		}
	}

	private void initialiseHeights()
	{
		MaterialViewPagerSetting setting = MaterialViewPagerSetting.getInstance();

		if (headerBackground != null)
		{
			headerBackground.setBackgroundColor(setting.color);

			ViewGroup.LayoutParams layoutParams = headerBackground.getLayoutParams();
			layoutParams.height = (int) Utils.dpToPx(setting.headerHeight + 60, getContext());
			headerBackground.setLayoutParams(layoutParams);
		}
		if (pagerTitleStripContainer != null)
		{
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pagerTitleStripContainer.getLayoutParams();
			int marginTop = (int) Utils.dpToPx(setting.headerHeight - 40, getContext());
			layoutParams.setMargins(0, marginTop, 0, 0);
			pagerTitleStripContainer.setLayoutParams(layoutParams);
		}
		if (topBackground != null)
		{
			ViewGroup.LayoutParams params = topBackground.getLayoutParams();
			params.height = (int) Utils.dpToPx(setting.headerHeight, getContext());
			topBackground.setLayoutParams(params);
		}
	}

	public void setBackgroundImageURL(String URL, int fadeDuration)
	{
		if (URL != null)
		{
			final MaterialViewPagerImageHeader headerBackgroundImage = (MaterialViewPagerImageHeader) findViewById(
					R.id.materialviewpager_imageHeader);
			//if using MaterialViewPagerImageHeader
			if (headerBackgroundImage != null)
			{
				headerBackgroundImage.setAlpha(MaterialViewPagerSetting.getInstance().headerAlpha);
				headerBackgroundImage.setImageURL(URL, fadeDuration);
			}
		}
	}

	public void setBackgroudImageDrawable(Drawable drawable, int fadeDuration)
	{
		if (drawable != null)
		{
			final MaterialViewPagerImageHeader headerBackgroundImage = (MaterialViewPagerImageHeader) findViewById(
					R.id.materialviewpager_imageHeader);
			//if using MaterialViewPagerImageHeader
			if (headerBackgroundImage != null)
			{
				headerBackgroundImage.setAlpha(MaterialViewPagerSetting.getInstance().headerAlpha);
				headerBackgroundImage.setImageDrawable(drawable, fadeDuration);
			}
		}
	}

	public void setLogoImageDrawable(Drawable drawable, int duration)
	{
		if (drawable != null)
		{
			final MaterialViewPagerImageLogo logo = (MaterialViewPagerImageLogo) findViewById(
					R.id.materialviewpager_imageLogo);

			if (logo != null)
			{
				logo.setAlpha(MaterialViewPagerSetting.getInstance().headerAlpha);
				logo.setImageDrawable(drawable, duration);
			}
		}
	}

	public void setColor(int color, int fadeDuration)
	{
		MaterialViewPagerHelper.getAnimator(getContext()).setColor(color, fadeDuration * 2);
	}

	public ViewPager getViewPager()
	{
		return viewPager;
	}

	public PagerSlidingTabStrip getPagerTitleStrip()
	{
		return (PagerSlidingTabStrip) pagerTitleStripContainer.findViewById(
				R.id.materialviewpager_pagerTitleStrip);
	}
}
