package com.example.lab5oakesjapickers;

import java.util.Calendar;
import java.util.Date;

import android.animation.TimeAnimator.TimeListener;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void showTimePicker(View v) {
		DialogFragment df = new DialogFragment() {

			public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
				Calendar c = Calendar.getInstance();
				int hour = c.get(Calendar.HOUR_OF_DAY);
				int minute = c.get(Calendar.MINUTE);
				return new TimePickerDialog(getActivity(),
						new MyTimePickerListener(), hour, minute,
						DateFormat.is24HourFormat(getActivity()));
			};

		};
		df.show(getFragmentManager(), "time picker");
	}

	class MyTimePickerListener implements TimePickerDialog.OnTimeSetListener {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String tensMinute = "";
			if (minute < 10) {
				tensMinute = "0";
			}
			String s = "You chose " + hourOfDay + ":" + tensMinute + minute
					+ " as your time.";
			Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
		}

	}

	public void showDatePicker(View v) {
		DialogFragment df = new DialogFragment() {
			public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
				final Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);
				return new DatePickerDialog(getActivity(),
						new MyDatePickerListener(), year, month, day);
			};
		};
		df.show(getFragmentManager(), "date picker");
	}

	class MyDatePickerListener implements DatePickerDialog.OnDateSetListener {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			monthOfYear++;
			String s = "You chose " + monthOfYear + "/" + dayOfMonth + "/"
					+ year + " as your date.";
			Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
		}

	}
}
