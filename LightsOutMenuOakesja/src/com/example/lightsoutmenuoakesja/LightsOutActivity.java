package com.example.lightsoutmenuoakesja;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LightsOutActivity extends Activity implements OnClickListener {

	private int mNumButtons;
	private LightsOutGame mGame;
	private ArrayList<Button> mButtons;
	private TextView mGameStateTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lights_out);

		Intent intent = getIntent();
		mNumButtons = intent.getIntExtra(MainActivity.KEY_NUM_BUTTONS, 7);

		mGame = new LightsOutGame(mNumButtons);

		mButtons = new ArrayList<Button>();
		TableRow buttonRow = new TableRow(this);

		for (int i = 0; i < mNumButtons; i++) {
			Button button = new Button(this);
			button.setTag(Integer.valueOf(i));
			mButtons.add(button);
			buttonRow.addView(button);
			button.setOnClickListener(this);
		}

		TableLayout tableLayout = (TableLayout) findViewById(R.id.button_table);
		tableLayout.addView(buttonRow);

		Button newGame = (Button) findViewById(R.id.new_game_button);
		newGame.setOnClickListener(this);
		
		mGameStateTV = (TextView)findViewById(R.id.game_state);

		updateView();
	}

	private void updateView() {
		for (int i = 0; i < mNumButtons; i++) {
			mButtons.get(i).setText("" + mGame.getValueAtIndex(i));
			mButtons.get(i).setEnabled(!mGame.checkForWin());
		}

		Resources r = getResources();
		String s;
		int nPresses = mGame.getNumPresses();
		boolean isWin = mGame.checkForWin();

		if (isWin) {
			if(nPresses == 1){
				s = r.getString(R.string.you_won_one_move);
			} else {
				s = r.getString(R.string.you_won_format, nPresses);
			}
		} else {
			if(nPresses == 0){
				s = r.getString(R.string.game_start);
			} else if(nPresses == 1){
				s = r.getString(R.string.game_one_move);
			} else {
				s = r.getString(R.string.game_format, nPresses);
			}
		}
		mGameStateTV.setText(s);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.new_game_button) {
			mGame = new LightsOutGame(mNumButtons);
		} else {
			// game button
			mGame.pressedButtonAtIndex((Integer) (v.getTag()));
		}
		updateView();
	}
}
