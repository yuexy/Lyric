package zq.mdlib.mdviewpager;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by YueXy on 2015/5/7.
 */
public class MaterialViewPagerHelper
{
	private static ConcurrentHashMap<Object, MaterialViewPagerAnimator> hashMap = new ConcurrentHashMap<>();

	public static void register(Activity activity, MaterialViewPagerAnimator animator)
	{
		if (!hashMap.containsKey(activity))
			hashMap.put(activity, animator);
	}

	public static void registerRecyclerView(Activity activity, RecyclerView recyclerView, RecyclerView.OnScrollListener onScrollListener)
	{
		if (activity != null && hashMap.containsKey(activity))
		{
			MaterialViewPagerAnimator animator = hashMap.get(activity);
			if (animator != null)
			{
				animator.registerRecyclerView(recyclerView, onScrollListener);
			}
		}
	}

	public static void registerListView(Activity activity, ObservableListView listView, ObservableScrollViewCallbacks observableScrollViewCallbacks)
	{
		if (activity != null && hashMap.containsKey(activity))
		{
			MaterialViewPagerAnimator animator = hashMap.get(activity);
			if (animator != null)
			{
				animator.registerListView(listView, observableScrollViewCallbacks);
			}
		}
	}

	public static void registerScrollView(Activity activity, ObservableScrollView mScrollView, ObservableScrollViewCallbacks observableScrollViewCallbacks)
	{
		if (activity != null && hashMap.containsKey(activity))
		{
			MaterialViewPagerAnimator animator = hashMap.get(activity);
			if (animator != null)
			{
				animator.registerScrollView(mScrollView, observableScrollViewCallbacks);
			}
		}
	}

	public static MaterialViewPagerAnimator getAnimator(Context context)
	{
		return hashMap.get(context);
	}


}
