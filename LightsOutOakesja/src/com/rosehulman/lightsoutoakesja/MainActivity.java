package com.rosehulman.lightsoutoakesja;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private final String DEBUG_TAG = "LO";
	private final int NUM_BUTTONS = 7;

	private LightsOutGame mGame;
	private Button[] mButtons;
	private Button mNewGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButtons = new Button[NUM_BUTTONS];
		mGame = new LightsOutGame(NUM_BUTTONS);

		for (int i = 0; i < NUM_BUTTONS; i++) {
			int id = getResources().getIdentifier("button" + i, "id",
					getPackageName());
			mButtons[i] = (Button) findViewById(id);
			mButtons[i].setOnClickListener(this);
		}
		mNewGameButton = (Button) findViewById(R.id.newGameButton);
		mNewGameButton.setOnClickListener(this);
		updateGame();
	}

	@Override
	public void onClick(View v) {
		Log.d(DEBUG_TAG, "clicked button");
		if (v.getId() == mNewGameButton.getId()) {
			Log.d(DEBUG_TAG, "new game");
			mGame = new LightsOutGame(NUM_BUTTONS);
		} else {
			for (int i = 0; i < NUM_BUTTONS; i++) {
				if (v.getId() == mButtons[i].getId()) {
					Log.d(DEBUG_TAG, "button" + i + "pressed");
					mGame.pressedButtonAtIndex(i);
				}
			}
		}
		updateGame();
	}

	private void updateGame() {
		boolean won = mGame.checkForWin();
		for (int i = 0; i < NUM_BUTTONS; i++) {
			if (won) {
				mButtons[i].setEnabled(false);
			} else {
				mButtons[i].setEnabled(true);
			}
			String buttonState = new Integer(mGame.getValueAtIndex(i)).toString();
			mButtons[i].setText(buttonState);
		}

		TextView tv = (TextView) findViewById(R.id.gameStateTextView);
		if (won) {
			tv.setText(getString(R.string.winner));
		} else {
			tv.setText(getString(R.string.game_text));
		}
	}
}
