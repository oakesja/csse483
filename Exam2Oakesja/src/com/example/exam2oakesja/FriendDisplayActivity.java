package com.example.exam2oakesja;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FriendDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_display);
		
		Intent intent = getIntent();
		ArrayList<Friend> friends = intent.getParcelableArrayListExtra(MainActivity.CHOSEN_FRIENDS);
		String s = "\n";
		for (Friend friend : friends) {
			s += friend.getName() + "\n";
		}
		
		TextView tv = (TextView)findViewById(R.id.chosenFriendTv);
		tv.setText(getString(R.string.friend_message, s));
	}
}
