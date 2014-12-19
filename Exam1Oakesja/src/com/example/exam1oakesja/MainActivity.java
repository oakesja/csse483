package com.example.exam1oakesja;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button mGreenButton;
	private Button mRedButton;
	private Button mYellowButton;
	private Button mBlueButton;
	private Button mResetButton;
	private TextView mMessageTv;
	private String mSequence = "";
	private int mSequenceLength = 0;

	private final float STARTING_MESSAGE_SIZE = (float) 18;
	private final float SMALLER_MESSAGE_SIZE = (float) 14;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMessageTv = (TextView) findViewById(R.id.messageTextView);
		mGreenButton = (Button) findViewById(R.id.greenButton);
		mRedButton = (Button) findViewById(R.id.redButton);
		mYellowButton = (Button) findViewById(R.id.yellowButton);
		mBlueButton = (Button) findViewById(R.id.blueButton);
		mResetButton = (Button) findViewById(R.id.resetButton);

		mGreenButton.setOnClickListener(this);
		mRedButton.setOnClickListener(this);
		mYellowButton.setOnClickListener(this);
		mBlueButton.setOnClickListener(this);
		mResetButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.greenButton:
			mSequence = mSequence + " G";
			mSequenceLength++;
			displaySequence();
			break;
		case R.id.redButton:
			mSequence = mSequence + " R";
			mSequenceLength++;
			displaySequence();
			break;
		case R.id.yellowButton:
			mSequence = mSequence + " Y";
			mSequenceLength++;
			displaySequence();
			break;
		case R.id.blueButton:
			mSequence = mSequence + " B";
			mSequenceLength++;
			displaySequence();
			break;
		case R.id.resetButton: 
			mSequence = "";
			mSequenceLength = 0;
			mMessageTv.setText(getString(R.string.starting_message));
			mMessageTv.setTextSize(STARTING_MESSAGE_SIZE);
			break;
		default:
			break;
		}
	}

	private void displaySequence() {
		String message = getString(R.string.formatted_message, mSequence);
		mMessageTv.setText(message);
		// chose to keep track of sequence length to avoid confusion while using
		// mSequence.length() since mSequence has spaces in it for formatting
		if (mSequenceLength > 10) {
			mMessageTv.setTextSize(SMALLER_MESSAGE_SIZE);
		} else {
			mMessageTv.setTextSize(STARTING_MESSAGE_SIZE);
		}
	}
}
