package com.example.listviewoakesja;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RowNumberAdapter extends BaseAdapter {

	private Context mContext;
	private int mNumRows;
	private String[] mMonths;

	public RowNumberAdapter(Context context) {
		mContext = context;
		mNumRows = 5;
		mMonths = context.getResources().getStringArray(R.array.monthNames);
	}

	@Override
	public int getCount() {
		return mNumRows;
	}

	@Override
	public Object getItem(int position) {
		return mMonths[position % 12];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RowView view = null;
		if (convertView == null) {
			// create a new one
			view = new RowView(mContext);
		} else {
			// reuse it
			view = (RowView) convertView;
		}
		// customize it
		// view.setText("Row " + position);
		// int color = Color.rgb(position*10, 255-position*10, 100);
		// view.setTextColor(color);
		view.setLeftText((position + 1) + ".");
		view.setRightText(mMonths[position % 12]);
		return view;
	}

	public void addView() {
		mNumRows++;
	}

}
