package com.example.exam2oakesja;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendsAdapter extends ArrayAdapter<Friend> {

	public FriendsAdapter(Context context, List<Friend> friends) {
		super(context, R.layout.list_item, R.id.friendNameTv, friends);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v =  super.getView(position, convertView, parent);
		Friend friend = this.getItem(position);
		TextView nameTv = (TextView)v.findViewById(R.id.friendNameTv);
		nameTv.setText(friend.getName());
		TextView timeTv = (TextView)v.findViewById(R.id.friendTimeTv);
		timeTv.setText(friend.getHour() + ":" + friend.getMinute());
		return v;
	}
}
