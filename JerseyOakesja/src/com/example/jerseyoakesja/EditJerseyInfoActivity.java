package com.example.jerseyoakesja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class EditJerseyInfoActivity extends Activity implements OnClickListener {
	public static final String JERSEY_NAME = "JERSEY_NAME";
	public static final String JERSEY_NUMBER = "JERSEY_NUMBER";
	public static final String JERSEY_IS_RED = "JERSEY_IS_RED";

	private static final String DEBUG_TAG = "JE";

	private ToggleButton mColorButton;
	private EditText mNameEditText;
	private EditText mNumberEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_jersey_info);

		mNameEditText = (EditText) findViewById(R.id.editJerseyName);
		mNumberEditText = (EditText) findViewById(R.id.editJerseyNum);

		Button okButton = (Button) findViewById(R.id.okButton);
		Button cancelButton = (Button) findViewById(R.id.cancelButton);
		mColorButton = (ToggleButton) findViewById(R.id.jerseryColorButton);

		okButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		mColorButton.setOnClickListener(this);
		
		Intent intent = getIntent();
		mNameEditText.setText(intent.getStringExtra(JERSEY_NAME));
		mNumberEditText.setText(intent.getStringExtra(JERSEY_NUMBER));
		mColorButton.setChecked(intent.getBooleanExtra(JERSEY_IS_RED, true));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.okButton:
			returnResults();
			finish();
			break;
		case R.id.cancelButton:
			finish();
			break;
		case R.id.jerseryColorButton:

			break;
		default:
			break;
		}

	}

	private void returnResults() {
		Intent returnIntent = new Intent();
		Log.d(DEBUG_TAG, "number is" + mNumberEditText.getText().toString());
		Log.d(DEBUG_TAG, "name is" + mNameEditText.getText().toString());
		returnIntent.putExtra(JERSEY_NAME, mNameEditText.getText().toString());
		tryToPutNumberIntoIntent(returnIntent);
		returnIntent.putExtra(JERSEY_IS_RED, mColorButton.isChecked());
		setResult(RESULT_OK, returnIntent);
	}

	private void tryToPutNumberIntoIntent(Intent intent) {
		try {
			int number = Integer.parseInt(mNumberEditText.getText().toString());
			intent.putExtra(JERSEY_NUMBER, number);
		} catch (NumberFormatException e) {
			Log.d(DEBUG_TAG, "invalid number inputted");
		}
	}
}
