package com.example.jerseyoakesja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final int REQUEST_CODE_EDIT_BUTTON = 1;
	private static final String DEBUG_TAG = "JE";
	private TextView mNumberTv;
	private TextView mNameTv;
	private ImageView mJerseyImage;
	private boolean mIsRed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mIsRed = true;

		mNumberTv = (TextView) findViewById(R.id.jerseyNumberTextView);
		mNameTv = (TextView) findViewById(R.id.jerseyNameTextView);
		mJerseyImage = (ImageView) findViewById(R.id.jerseyImage);
		Button ediButton = (Button) findViewById(R.id.editButton);

		ediButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getBaseContext(),
						EditJerseyInfoActivity.class);
				intent.putExtra(EditJerseyInfoActivity.JERSEY_IS_RED, mIsRed);
				intent.putExtra(EditJerseyInfoActivity.JERSEY_NAME, mNameTv.getText().toString());
				intent.putExtra(EditJerseyInfoActivity.JERSEY_NUMBER, mNumberTv.getText().toString());
				startActivityForResult(intent, REQUEST_CODE_EDIT_BUTTON);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_EDIT_BUTTON
				&& resultCode == Activity.RESULT_OK) {
			Log.d(DEBUG_TAG, "got intent result");
			int number = data.getIntExtra(EditJerseyInfoActivity.JERSEY_NUMBER,
					Integer.parseInt(mNumberTv.getText().toString()));
			String name = data
					.getStringExtra(EditJerseyInfoActivity.JERSEY_NAME);
			mIsRed = data.getBooleanExtra(
					EditJerseyInfoActivity.JERSEY_IS_RED, mIsRed);
			updateJersey(number, name, mIsRed);
		} else {
			Log.d(DEBUG_TAG, "no intent result");
		}
	}
	
	private void updateJersey(int number, String name, boolean isRed){
		mNumberTv.setText("" + number);
		mNameTv.setText(name);
		if (isRed) {
			mJerseyImage.setImageResource(R.drawable.red_jersey);
		} else {
			mJerseyImage.setImageResource(R.drawable.blue_jersey);

		}
	}
}
