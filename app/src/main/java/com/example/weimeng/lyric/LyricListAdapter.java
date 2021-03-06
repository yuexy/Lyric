package com.example.weimeng.lyric;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weimeng.lyric.node.Lyric;
import com.example.weimeng.lyric.tools.ImageTools;
import com.example.weimeng.lyric.ui.content.ContentDialog;
import com.example.weimeng.lyric.view.LyricImageView;

import java.util.List;

/**
 * Created by weimeng on 2016/6/21.
 * 告诉列表如何显示以及显示什么内容
 */
public class LyricListAdapter extends RecyclerView.Adapter<LyricListAdapter.LyricListHolder>
{
	private Context mContext;
	private List<Lyric> lyricList;
	private LayoutInflater layoutInflater;

	public LyricListAdapter(Context c, List<Lyric> l)
	{
		this.mContext = c;
		this.lyricList = l;

		layoutInflater = LayoutInflater.from(mContext);
	}

	public void notifyListChange(List<Lyric> list)
	{
		lyricList = list;

		notifyDataSetChanged();
	}

	@Override
	public LyricListHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new LyricListHolder(layoutInflater.inflate(R.layout.lyric_list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(LyricListHolder holder, final int position)
	{
		holder.lyricImageView.setImageDrawable(new BitmapDrawable(ImageTools.string2Img(lyricList.get(position).getImage())), 400);
		holder.lyricImageView.setText(lyricList.get(position).getLyric());

		holder.lyricImageView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new ContentDialog(mContext, lyricList.get(position)).show();
			}
		});
	}

	@Override
	public int getItemCount()
	{
		return lyricList.size();
	}

	public class LyricListHolder extends RecyclerView.ViewHolder
	{	//自定义的视图类
		public LyricImageView lyricImageView;

		public LyricListHolder(View itemView)
		{
			super(itemView);

			lyricImageView = (LyricImageView) itemView.findViewById(R.id.lyric_list_item_image);
		}
	}
}
