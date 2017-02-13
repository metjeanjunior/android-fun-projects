package com.example.metje.fixedpricemeal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by metje on 2/12/2017.
 */

public class CustomAdaptor extends BaseAdapter
{
	private Context context;
	private ArrayList<ImageModel> imageModelList;

	public CustomAdaptor(Context context, ArrayList<ImageModel> imageModelList)
	{
		this.context = context;
		this.imageModelList = imageModelList;
	}

	@Override
	public int getViewTypeCount()
	{
		return getCount();
	}
	@Override
	public int getItemViewType(int position)
	{
		return position;
	}

	@Override
	public int getCount()
	{
		return imageModelList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return imageModelList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_item, null, true);

			holder.tvname = (TextView) convertView.findViewById(R.id.name);
			holder.iv = (ImageView) convertView.findViewById(R.id.imgView);

			convertView.setTag(holder);
		}else {
			// the getTag returns the viewHolder object set as a tag to the view
			holder = (ViewHolder)convertView.getTag();
		}

		holder.tvname.setText(imageModelList.get(position).getName());
		holder.iv.setImageResource(imageModelList.get(position).getImage_drawable());

		return convertView;
	}

	private class ViewHolder
	{
		protected TextView tvname;
		private ImageView iv;

	}
}
