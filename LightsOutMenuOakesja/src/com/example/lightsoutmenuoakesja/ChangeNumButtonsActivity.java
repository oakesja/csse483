package com.example.lightsoutmenuoakesja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeNumButtonsActivity extends Activity implements
		OnClickListener {
	private static final String LOM = "LOM";
	private int[] mRadioButtonLabels = new int[] {3,5,7,9};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_num_buttons);
		
		Intent data = getIntent();
		int nButtons = data.getIntExtra(MainActivity.KEY_NUM_BUTTONS, 7);

		((RadioButton) findViewById(R.id.radio3)).setOnClickListener(this);
		((RadioButton) findViewById(R.id.radio5)).setOnClickListener(this);
		((RadioButton) findViewById(R.id.radio7)).setOnClickListener(this);
		((RadioButton) findViewById(R.id.radio9)).setOnClickListener(this);
		
		RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
		for(int i = 0; i < mRadioButtonLabels.length; i++){
			int id = getResources().getIdentifier("radio" + mRadioButtonLabels[i], "id", getPackageName());
			findViewById(id).setOnClickListener(this);
		}
		int id = getResources().getIdentifier("radio" + nButtons, "id", getPackageName());
		rg.check(id);
	}

	@Override
	public void onClick(View v) {
		Intent returnIntent = new Intent();
		int numButtons = 3;
		switch (v.getId()) {
		case R.id.radio3:
			Log.d(LOM, "Clicked radio 3");
			break;
		case R.id.radio5:
			Log.d(LOM, "Clicked radio 5");
			numButtons = 5;
			break;
		case R.id.radio7:
			Log.d(LOM, "Clicked radio 7");
			numButtons = 7;
			break;
		case R.id.radio9:
			Log.d(LOM, "Clicked radio 9");
			numButtons = 9;
			break;
		}
		returnIntent.putExtra(MainActivity.KEY_NUM_BUTTONS, numButtons);
		setResult(RESULT_OK, returnIntent);
		finish();
	}
}
