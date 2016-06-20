package zq.mdlib.mdviewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import zq.mdlib.Utils.Utils;

import static android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * Created by YueXy on 2015/5/7.
 */
public class MaterialViewPagerHeader
{
	protected static Context context;

	protected static View pagerTitleStrip;
	protected static View topLayout;
	protected static View topBackground;

	protected static View headerBackground;
	protected static View statusBackground;
	protected static View logoContainer;

	public static float finalTabsY;

	public static float finalTitleY;
	public static float finalTitleHeight;
	public static float finalTitleX;

	public static float originTitleY;
	public static float originTitleHeight;
	public static float originTitleX;

	public static float finalScale;

	private static MaterialViewPagerHeader header;

	public static MaterialViewPagerHeader getInstance()
	{
		if (context == null)
			return null;
		return header;
	}

	public static MaterialViewPagerHeader getInstance(Context context)
	{
		if (header == null)
			header = new MaterialViewPagerHeader(context);
		return header;
	}

	private MaterialViewPagerHeader(Context c)
	{
		context = c;
	}

	public MaterialViewPagerHeader withPagerTitleStrip(View pagerStrip)
	{
		pagerTitleStrip = pagerStrip;

		pagerTitleStrip.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener()
		{
			@Override
			public boolean onPreDraw()
			{
				finalTabsY = Utils.dpToPx(-140, context);

				pagerTitleStrip.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});
		return this;
	}

	public MaterialViewPagerHeader withHeaderBackground(View bg)
	{
		headerBackground = bg;
		return this;
	}

	public MaterialViewPagerHeader withStatusBackground(View sb)
	{
		statusBackground = sb;
		return this;
	}

	public MaterialViewPagerHeader withTopLayout(View tl)
	{
		topLayout = tl;
		return this;
	}

	public MaterialViewPagerHeader withTopBackground(View tb)
	{
		topBackground = tb;
		return this;
	}

	public MaterialViewPagerHeader withLogo(View l)
	{
		logoContainer = l;

		logoContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@Override
			public boolean onPreDraw()
			{
				finalTitleY = Utils.dpToPx(34f, context);
				finalTitleHeight = Utils.dpToPx(21, context);

				originTitleX = logoContainer.getX();
				originTitleY = logoContainer.getY();
				originTitleHeight = logoContainer.getHeight();

				finalScale = finalTitleHeight / originTitleHeight;
				finalTitleX = Utils.dpToPx(52f, context) - (logoContainer.getWidth()/2) *(1-finalScale);

				logoContainer.getViewTreeObserver().removeOnPreDrawListener(this);
				return false;
			}
		});
		return this;
	}
}
