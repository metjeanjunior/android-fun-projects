package com.example.metje.todo;

/**
 * Created by metje on 3/21/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by metje on 3/21/2017.
 */

public class CustomAdapter extends BaseAdapter
{
	private Context context;
	private ArrayList<TaskObject> taskList;

	public CustomAdapter(Context context, ArrayList<TaskObject> taskList)
	{
		this.context = context;
		this.taskList = taskList;
	}

	@Override
	public int getViewTypeCount()
	{
		return getCount() < 1 ?  1 : getCount();
	}
	@Override
	public int getItemViewType(int position)
	{
		return position;
	}

	@Override
	public int getCount()
	{
		return taskList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return taskList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

//	public void updateList()

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_view, null, true);
			holder.task_name = (TextView) convertView.findViewById(R.id.task_name);
			convertView.setTag(holder);
		}else {
			// the getTag returns the viewHolder object set as a tag to the view
			holder = (ViewHolder)convertView.getTag();
		}

		holder.task_name.setText(taskList.get(position).getName());
//		holder.iv.setImageResource(imageModelList.get(position).getImage_drawable());

		return convertView;
	}

	private class ViewHolder
	{
		protected TextView task_name;
	}
}