package com.example.exam2oakesja;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	public static final String CHOSEN_FRIENDS = "CHOSEN_FRIENDS";
	public static final String TAG = "EXAM";

	private FriendsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ArrayList<Friend> friends;
		if(savedInstanceState != null) {
			Log.d(TAG, "loading saved state");
			friends = savedInstanceState.getParcelableArrayList(CHOSEN_FRIENDS);
		} else {
			Log.d(TAG, "no saved state");
			friends = new ArrayList<Friend>();
		}

		mAdapter = new FriendsAdapter(this, friends);
		ListView lv = (ListView) findViewById(R.id.friendsList);
		lv.setAdapter(mAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d(TAG, "delete a friend");
				showDeleteFriendDialog(position);
			}
		});

		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			ArrayList<Friend> mChosenFriends;

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				Log.d(TAG, "prepare action menu");
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				Log.d(TAG, "destroying action menu");
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				Log.d(TAG, "create action menu");
				this.mChosenFriends = new ArrayList<Friend>();
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.action_menu, menu);
				mode.setTitle(R.string.contextual_choose_title);
				this.setSubtitle(mode);
				return true;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.selectGroup:
					Log.d(TAG, "creating chosen friend activity");
					Intent intent = new Intent(getBaseContext(),
							FriendDisplayActivity.class);
					intent.putExtra(CHOSEN_FRIENDS, mChosenFriends);
					startActivity(intent);
					return true;
				default:
					return false;
				}
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				Log.d(TAG, "item checked state changed " + position + " "
						+ checked);
				Friend chosen = MainActivity.this.mAdapter.getItem(position);
				if (checked) {
					this.mChosenFriends.add(chosen);
				} else {
					this.mChosenFriends.remove(chosen);
				}
				setSubtitle(mode);
			}

			private void setSubtitle(ActionMode mode) {
				mode.setSubtitle(getString(R.string.contextual_choose_format,
						this.mChosenFriends.size()));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addFriend:
			showFriendDialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void showFriendDialog() {
		DialogFragment df = new DialogFragment() {
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				final View v = getLayoutInflater().inflate(
						R.layout.add_friend_dialog, null);
				builder.setView(v);
				builder.setTitle(R.string.add_friend);
				builder.setNegativeButton(android.R.string.cancel, null);
				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String name = ((EditText) v
										.findViewById(R.id.edit_friend))
										.getText().toString();
								TimePicker tp = (TimePicker) v
										.findViewById(R.id.timePicker);
								int hour = tp.getCurrentHour();
								int minute = tp.getCurrentMinute();
								mAdapter.add(new Friend(name, hour, minute));
								mAdapter.notifyDataSetChanged();
							}
						});
				return builder.create();
			};
		};
		df.show(getFragmentManager(), "friend creator");
	}

	public void showDeleteFriendDialog(final int position) {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle(R.string.confirm_delete_title);
				builder.setMessage(R.string.confirm_delete_message);
				builder.setNegativeButton(android.R.string.cancel, null);
				builder.setPositiveButton(android.R.string.ok,
						new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mAdapter.remove(mAdapter.getItem(position));
								mAdapter.notifyDataSetChanged();
							}
						});
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "delete friend");
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		ArrayList<Friend> friends = new ArrayList<Friend>();
		for (int i = 0; i < mAdapter.getCount(); i++) {
			friends.add(mAdapter.getItem(i));
		}
		outState.putParcelableArrayList(CHOSEN_FRIENDS, friends);
		super.onSaveInstanceState(outState);
	}
}
