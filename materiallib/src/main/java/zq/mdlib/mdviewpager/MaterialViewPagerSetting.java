package zq.mdlib.mdviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zswd.zq.materiallib.R;

import zq.mdlib.Utils.Utils;

/**
 * Created by YueXy on 2015/5/6.
 * 负责保存应用中所有的设置
 * 单例模式，获取方法 MaterialViewPagerSetting.getInstance()
 */
public class MaterialViewPagerSetting
{
	private static MaterialViewPagerSetting setting;

	protected static int headerLayoutId;		//背景容器，用于add一个ImageView
	protected static int pagerTitleStripId;

	protected static int logoLayoutId;			//logo容器，用于add一个ImageView
	protected static int logoMarginTop;			//logo距离顶部的距离

	protected static int headerHeight;			//顶部视图的高度
	protected static int titleMarginTop;
	protected static int color;

	protected static float headerAlpha;

	protected static boolean hideTitle;
	protected static boolean hideLogoWithFade;
	protected static boolean enableElevation;

	protected static boolean pagerStripMiddle;

	public static MaterialViewPagerSetting getInstance()
	{
		if (setting == null)
			setting = new MaterialViewPagerSetting();

		return setting;
	}

	public void handleAttributes(Context context, AttributeSet attrs)
	{
		try
		{
			TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.MaterialViewPager);

			headerLayoutId = styledAttrs.getResourceId(R.styleable.MaterialViewPager_viewpager_header, -1);
			if (headerLayoutId == -1)
				headerLayoutId = R.layout.material_viewpager_default_header;

			pagerStripMiddle = styledAttrs.getBoolean(R.styleable.MaterialViewPager_viewpager_pagerStripMiddle, false);

			pagerTitleStripId = styledAttrs.getResourceId(R.styleable.MaterialViewPager_viewpager_pagerTitleStrip, -1);
			if (pagerTitleStripId == -1)
			{
				if (!pagerStripMiddle)
					pagerTitleStripId = R.layout.material_viewpager_default_pagertitlestrip;
				else
					pagerTitleStripId = R.layout.material_viewpager_new_pagertitlestrip;
			}

			logoLayoutId = styledAttrs.getResourceId(R.styleable.MaterialViewPager_viewpager_logo, -1);
			if (logoLayoutId == -1)
				logoLayoutId = R.layout.material_viewpager_default_logo;

			logoMarginTop = styledAttrs.getDimensionPixelSize(R.styleable.MaterialViewPager_viewpager_logoMarginTop, 0);

			color = styledAttrs.getColor(R.styleable.MaterialViewPager_viewpager_color, 0);

			headerHeight = styledAttrs.getDimensionPixelOffset(R.styleable.MaterialViewPager_viewpager_headerHeight, 200);
			headerHeight = Math.round(Utils.pxToDp(headerHeight, context));

			headerAlpha = styledAttrs.getFloat(R.styleable.MaterialViewPager_viewpager_headerAlpha, 0.5f);

			hideTitle = styledAttrs.getBoolean(R.styleable.MaterialViewPager_viewpager_hideTitle, false);
			hideLogoWithFade = styledAttrs.getBoolean(R.styleable.MaterialViewPager_viewpager_hideLogoWithFade, true);

			enableElevation = styledAttrs.getBoolean(R.styleable.MaterialViewPager_viewpager_enableElevation, false);

			styledAttrs.recycle();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
