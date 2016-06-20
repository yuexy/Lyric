package zq.mdlib.mdviewpager.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by YueXy on 7/10/2015.
 */
public abstract class AbstractRecyclerViewMaterialAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<VH>
{
	static final int PLACEHOLDER_SIZE = 1;

	@Override
	public VH onCreateViewHolder(ViewGroup parent, int viewType)
	{

		return null;
	}

	@Override
	public void onBindViewHolder(VH holder, int position)
	{

	}

	@Override
	public int getItemCount()
	{
		return GetItemCount() + PLACEHOLDER_SIZE;
	}

	public abstract VH OnCreateViewHolder(ViewGroup parent, int viewType);
	public abstract void OnBindViewHolder(VH holder, int position);
	public abstract int GetItemCount();
}
