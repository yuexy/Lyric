package zq.mdlib.mdviewpager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zswd.zq.materiallib.R;

/**
 * Created by YueXy on 6/15/2015.
 */
public abstract class AbstractListViewMaterialAdapter extends BaseAdapter
{
	public static final int TYPE_PLACEHOLDER = Integer.MIN_VALUE;
	public static final int PLACEHOLDER_SIZE = 1;

	@Override
	public int getCount()
	{
		return getCounts() + PLACEHOLDER_SIZE;
	}

	@Override
	public Object getItem(int position)
	{
		return getItems(position - PLACEHOLDER_SIZE);
	}

	@Override
	public long getItemId(int position)
	{
		return getItemIds(position - PLACEHOLDER_SIZE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		switch (position)
		{
		case 0:
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_viewpager_placeholder, parent, false);

			return convertView;

		default:
			return getViews(position - PLACEHOLDER_SIZE, convertView, parent);
		}
	}

	public abstract int getCounts();

	public abstract Object getItems(int position);

	public abstract long getItemIds(int position);

	public abstract View getViews(int position, View convertView, ViewGroup parent);
}
