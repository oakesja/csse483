package com.example.lightsoutmenuoakesja;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private static final String PREFS = "PREFS";
	private Button playButton;
	private Button changeButton;
	private Button aboutButton;
	private Button exitButton;
	private static final String LOM = "LOM";
	public static final String KEY_NUM_BUTTONS = "KEY_NUM_BUTTONS";
	private static final int REQUEST_CODE_CHANGE_BUTTON = 1;
	private int mNumButtons = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		playButton = (Button) findViewById(R.id.playButton);
		changeButton = (Button) findViewById(R.id.changeButton);
		aboutButton = (Button) findViewById(R.id.aboutButton);
		exitButton = (Button) findViewById(R.id.closeButton);

//		SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
//		mNumButtons = prefs.getInt(KEY_NUM_BUTTONS, 7);
		if (savedInstanceState != null) {
	        // Restore value of members from saved state
	        mNumButtons = savedInstanceState.getInt(KEY_NUM_BUTTONS);
			playButton.setText(getString(R.string.play_button_format, mNumButtons));
		}

		playButton.setOnClickListener(this);
		changeButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playButton:
			Log.d(LOM, "Play button clicked");
			Intent playIntent = new Intent(this, LightsOutActivity.class);
			playIntent.putExtra(KEY_NUM_BUTTONS, mNumButtons);
			startActivity(playIntent);
			break;
		case R.id.changeButton:
			Log.d(LOM, "Change button clicked");
			Intent changeButtonsActivity = new Intent(this,
					ChangeNumButtonsActivity.class);
			changeButtonsActivity.putExtra(KEY_NUM_BUTTONS, mNumButtons);
			startActivityForResult(changeButtonsActivity,
					REQUEST_CODE_CHANGE_BUTTON);
			break;
		case R.id.aboutButton:
			Log.d(LOM, "About button clicked");
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
			break;
		case R.id.closeButton:
			Log.d(LOM, "Exit button clicked");
			break;
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// See which child activity is calling us back.
		switch (requestCode) {
		case REQUEST_CODE_CHANGE_BUTTON:
			if (resultCode == Activity.RESULT_OK) {
				Log.d(LOM, "Result ok!");
				mNumButtons = data.getIntExtra(KEY_NUM_BUTTONS, -1);
				String s = getString(R.string.play_button_format, mNumButtons);
				playButton.setText(s);
			} else {
				Log.d(LOM, "Result not okay.  User hit back without a button");
			}
			break;
		default:
			Log.d(LOM, "Unknown result code");
			break;
		}
	}

	// @Override
	// protected void onPause() {
	// super.onPause();
	// SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
	// SharedPreferences.Editor editor = prefs.edit();
	// editor.putInt(KEY_NUM_BUTTONS, mNumButtons);
	// editor.commit();
	// }

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		Log.d(LOM, "save num buttons " + mNumButtons);
		savedInstanceState.putInt(KEY_NUM_BUTTONS, mNumButtons);
		super.onSaveInstanceState(savedInstanceState);
	}

}
