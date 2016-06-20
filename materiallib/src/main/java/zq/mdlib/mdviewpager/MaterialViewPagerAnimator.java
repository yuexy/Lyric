package zq.mdlib.mdviewpager;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static zq.mdlib.Utils.Utils.dpToPx;
import static zq.mdlib.Utils.Utils.colorWithAlpha;

/**
 * Created by YueXy on 2015/5/7.
 */
public class MaterialViewPagerAnimator
{
	private final String TAG = MaterialViewPagerAnimator.class.getSimpleName();
	private Context context;
	private MaterialViewPagerHeader mHeader;

	private static final int DEFAULT_ANIMATOR_DURATION = 600;

	private MaterialViewPager materialviewPager;

	private final float elevation;
	private final float scrollMax;    //最大滑动距离
	private final float scrollMaxDp;

	private float lastYOffset = -1;
	private float lastPercent = 0;

	private MaterialViewPagerSetting settings;

	private List<Object> scrollViewList = new ArrayList<>();    //有滑动块的视图的集合
	private List<Object> calledScrollList = new ArrayList<>();    //被调用的视图的集合
	private HashMap<Object, Integer> yOffsets = new HashMap<>();    //记录每个视图偏移量

	private float headerYOffset = Float.MAX_VALUE;
	private ObjectAnimator headerAnimator;

	public MaterialViewPagerAnimator(MaterialViewPager m)
	{
		this.materialviewPager = m;
		this.settings = MaterialViewPagerSetting.getInstance();

		if (MaterialViewPagerHeader.getInstance() != null)
			mHeader = MaterialViewPagerHeader.getInstance();
		else
			mHeader = m.materialViewPagerHeader;

		this.context = mHeader.context;

		this.scrollMax = settings.headerHeight - 50;
		this.scrollMaxDp = dpToPx(this.scrollMax, context);

		this.elevation = dpToPx(4, context);
	}

	private void dispatchScrollOffset(Object source, float yOffset)
	{
		if (scrollViewList == null)
			return;

		for (Object scroll : scrollViewList)
		{
			if (scroll == null || scroll == source)
				continue;

			calledScrollList.add(scroll);

			if (scroll instanceof RecyclerView)
			{
				RecyclerView.LayoutManager layoutManager = ((RecyclerView) scroll).getLayoutManager();
				if (layoutManager instanceof LinearLayoutManager)
				{
					LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
					linearLayoutManager.scrollToPositionWithOffset(0, (int) -yOffset);
				}
			}

			if (scroll instanceof ScrollView)
			{
				((ScrollView) scroll).scrollTo(0, (int) yOffset);
			}

			if (scroll instanceof ListView)
			{
				((ListView) scroll).scrollTo(0, (int) yOffset);
			}

			yOffsets.put(scroll, (int) yOffset);

			calledScrollList.remove(scroll);
		}
	}

	public void onMaterialScrolled(Object source, float yOffset)
	{

		if (yOffset == lastYOffset)
			return;

		float scrollTop = -yOffset;

		{ //parallax scroll of ImageView 背景图片向上移动
			if (mHeader.headerBackground != null)
				mHeader.headerBackground.setTranslationY(scrollTop / 1.5f);
		}

		//yOffset = ;
		//Log.d("yOffset", "" + yOffset);

		//让所有的list界面都滚动
		dispatchScrollOffset(source, minMax(0, yOffset, scrollMaxDp));

		float percent = yOffset / scrollMax;

		percent = minMax(0, percent, 1);
		{
			//			Log.d("scrollMax", scrollMax + "");
			//			Log.d("percent", percent + "");
			{
				// change color of
				// toolbar & viewpager indicator &  statusBaground
				setColorPercent(percent);
				lastPercent = percent;

			}

			if (mHeader.pagerTitleStrip != null)
			{ //move the viewpager indicator
				float newY = mHeader.pagerTitleStrip.getY() + scrollTop;
				if (newY >= mHeader.finalTabsY)
				{
					mHeader.pagerTitleStrip.setTranslationY(scrollTop);
					mHeader.topBackground.setTranslationY(scrollTop);
				}
				else
				{
					mHeader.pagerTitleStrip.setTranslationY(mHeader.finalTabsY);
					mHeader.topBackground.setTranslationY(mHeader.finalTabsY);
				}
			}

			if (mHeader.logoContainer != null)
			{ //move the header logo to toolbar

				if (settings.hideLogoWithFade)
				{
					mHeader.logoContainer.setAlpha(1 - percent);
					mHeader.logoContainer.setTranslationY((mHeader.finalTitleY - mHeader.originTitleY) * percent);
				}
				else
				{
					mHeader.logoContainer.setTranslationY((mHeader.finalTitleY - mHeader.originTitleY) * percent);
					mHeader.logoContainer.setTranslationX((mHeader.finalTitleX - mHeader.originTitleX) * percent);

					float scale = (1 - percent) * (1 - mHeader.finalScale) + mHeader.finalScale;

					setScale(scale, mHeader.logoContainer);
				}
			}

			if (settings.hideTitle && mHeader.topLayout != null)
			{
				boolean scrollUp = lastYOffset < yOffset;

				if (scrollUp)
				{
					//Log.d(TAG, "scrollUp");
					followScrollToolbarLayout(yOffset);
				}
				else
				{
					//Log.d(TAG, "scrollDown");
					if (yOffset > mHeader.topLayout.getHeight())
					{
						animateEnterToolbarLayout(yOffset);
					}
					else if (yOffset <= mHeader.topLayout.getHeight())
					{
						if (headerAnimator != null)
						{
							mHeader.topLayout.setTranslationY(0);
						}
						else
						{
							headerYOffset = Float.MAX_VALUE;
							followScrollToolbarLayout(yOffset);
						}
					}
				}
			}
		}

		if (headerAnimator != null && percent < 1)
		{
			headerAnimator.cancel();
			headerAnimator = null;
		}

		lastYOffset = yOffset;
	}

	public void registerRecyclerView(final RecyclerView recyclerView,
									 final RecyclerView.OnScrollListener onScrollListener)
	{
		if (recyclerView != null)
		{
			scrollViewList.add(recyclerView);
			yOffsets.put(recyclerView, 0);
			recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener()
			{

				@Override
				public void onScrollStateChanged(RecyclerView recyclerView, int newState)
				{
					super.onScrollStateChanged(recyclerView, newState);
					if (onScrollListener != null)
						onScrollListener.onScrollStateChanged(recyclerView, newState);
				}

				@Override
				public void onScrolled(RecyclerView recyclerView, int dx, int dy)
				{
					super.onScrolled(recyclerView, dx, dy);

					if (onScrollListener != null)
						onScrollListener.onScrolled(recyclerView, dx, dy);

					int scrollY = yOffsets.get(recyclerView);

					if (calledScrollList.contains(recyclerView))
					{
						calledScrollList.remove(recyclerView);
						return;
					}

					int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
					if (firstVisibleItem == 0 && dy < 0)
					{
						// 当前recyclerview在最顶部
						scrollY = 0;
					}
					else
					{
						scrollY += dy;
					}

					yOffsets.put(recyclerView, scrollY);
					onMaterialScrolled(recyclerView, scrollY);
				}
			});
		}
	}

	public void registerScrollView(final ObservableScrollView scrollView,
								   final ObservableScrollViewCallbacks observableScrollViewCallbacks)
	{
		if (scrollView != null)
		{
			scrollViewList.add(scrollView);
			scrollView.setScrollViewCallbacks(new ObservableScrollViewCallbacks()
			{
				@Override
				public void onScrollChanged(int i, boolean b, boolean b2)
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onScrollChanged(i, b, b2);
					if (calledScrollList.contains(scrollView))
					{
						calledScrollList.remove(scrollView);
						return;
					}
					onMaterialScrolled(scrollView, i);
				}

				@Override
				public void onDownMotionEvent()
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onDownMotionEvent();
				}

				@Override
				public void onUpOrCancelMotionEvent(ScrollState scrollState)
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onUpOrCancelMotionEvent(scrollState);
				}
			});
		}
	}

	public void registerListView(final ObservableListView listView,
								 final ObservableScrollViewCallbacks observableScrollViewCallbacks)
	{
		if (listView != null)
		{
			scrollViewList.add(listView);
			listView.setScrollViewCallbacks(new ObservableScrollViewCallbacks()
			{
				@Override
				public void onScrollChanged(int i, boolean b, boolean b2)
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onScrollChanged(i, b, b2);
					if (calledScrollList.contains(listView))
					{
						calledScrollList.remove(listView);
						return;
					}
					onMaterialScrolled(listView, i);
				}

				@Override
				public void onDownMotionEvent()
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onDownMotionEvent();
				}

				@Override
				public void onUpOrCancelMotionEvent(ScrollState scrollState)
				{
					if (observableScrollViewCallbacks != null)
						observableScrollViewCallbacks.onUpOrCancelMotionEvent(scrollState);
				}
			});
		}
	}

	private static float minMax(float min, float value, float max)
	{
		value = Math.min(value, max);
		value = Math.max(min, value);
		return value;
	}

	public void setColorPercent(float percent)
	{
		// change color of
		// toolbar & viewpager indicator &  statusBaground

		setBackgroundColor(colorWithAlpha(settings.color, percent), mHeader.statusBackground);

		if (percent >= 1)
		{
			setBackgroundColor(colorWithAlpha(settings.color, percent), mHeader.topBackground, mHeader.pagerTitleStrip);
		}
		else
		{
			setBackgroundColor(colorWithAlpha(settings.color, 0), mHeader.topBackground, mHeader.pagerTitleStrip);
		}

		if (settings.enableElevation)
			setElevation((percent == 1) ? elevation : 0, mHeader.topLayout, mHeader.pagerTitleStrip, mHeader.logoContainer);
	}

	private static void setBackgroundColor(int color, View... views)
	{
		for (View view : views)
		{
			if (view != null)
				view.setBackgroundColor(color);
		}
	}

	private static void setScale(float scale, View... views)
	{
		for (View view : views)
		{
			if (view != null)
			{
				view.setScaleX(scale);
				view.setScaleY(scale);
			}
		}
	}

	private void followScrollToolbarLayout(float yOffset)
	{
		if (headerYOffset == Float.MAX_VALUE)
			headerYOffset = scrollMax;

		float diffOffsetScrollMax = headerYOffset - yOffset;
		if (diffOffsetScrollMax <= 0)
		{
			mHeader.topLayout.setTranslationY(diffOffsetScrollMax);
		}
	}

	private void animateEnterToolbarLayout(float yOffset)
	{
		if (headerAnimator == null)
		{
			headerAnimator = ObjectAnimator.ofFloat(mHeader.topLayout, "translationY", 0).setDuration(DEFAULT_ANIMATOR_DURATION);
			headerAnimator.start();
			headerYOffset = yOffset;
		}
	}

	private static void setElevation(float elevation, View... views)
	{
		//		for (View view : views)
		//		{
		//			if (view != null)
		//				ViewCompat.setElevation(view, elevation);
		//		}
	}

	public void setColor(int color, int duration)
	{
		ValueAnimator colorAnim = ObjectAnimator.ofInt(mHeader.headerBackground, "backgroundColor", new int[]{
				settings.color, color});
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setDuration(duration);
		colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator animation)
			{
				int colorAlpha = colorWithAlpha((Integer) animation.getAnimatedValue(), lastPercent);
				mHeader.statusBackground.setBackgroundColor(colorAlpha);

				if (lastPercent >= 1)
				{
					mHeader.topBackground.setBackgroundColor(colorAlpha);
					mHeader.pagerTitleStrip.setBackgroundColor(colorAlpha);
				}

			}
		});
		colorAnim.start();
		settings.color = color;
	}

	public int getHeaderHeight()
	{
		return settings.headerHeight;
	}
}
