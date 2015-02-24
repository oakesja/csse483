package com.example.weatherpicsandroidoakesja;

import java.io.IOException;
import java.util.ArrayList;

import com.appspot.oakesja_weatherpics.weatherpics.Weatherpics;
import com.appspot.oakesja_weatherpics.weatherpics.Weatherpics.Weatherpic.List;
import com.appspot.oakesja_weatherpics.weatherpics.model.Weatherpic;
import com.appspot.oakesja_weatherpics.weatherpics.model.WeatherpicCollection;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	private final String WP = "WP";
	private Weatherpics mService;
	private ListView mListView;
	private java.util.List<Weatherpic> mWeatherPics;
	public static final String WP_CAPTION_KEY = "WP_CAPTION_KEY";
	public static final String WP_URL_KEY = "WP_URL_KEY";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.listView);

		mService = new Weatherpics(AndroidHttp.newCompatibleTransport(),
				new GsonFactory(), null);

		mWeatherPics = new ArrayList<Weatherpic>();
		updatePics();
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					Weatherpic wp = mWeatherPics.get(position);
					Intent intent = new Intent(MainActivity.this, WeatherPicActivity.class);
					intent.putExtra(WP_CAPTION_KEY, wp.getCaption());
					intent.putExtra(WP_URL_KEY, wp.getImageUrl());
					startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			showAddWeatherPicDialog();
			break;

		case R.id.sync:
			updatePics();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private String randomImageUrl() {
		String[] urls = new String[] {
				"http://upload.wikimedia.org/wikipedia/commons/0/04/Hurricane_Isabel_from_ISS.jpg",
				"http://daraint.org/wp-content/uploads/2010/12/hurricane_dennis-700x465.jpg",
				"http://tornado-facts.com/wp-content/uploads/2009/07/tornadoes1-300x181.jpg",
				"http://severe-wx.pbworks.com/f/tornado.jpg",
				"http://t.wallpaperweb.org/wallpaper/nature/1920x1080/Lightning_Storm_Over_Fort_Collins_Colorado.jpg",
				"http://www.legoengineering.com/wp-content/uploads/2013/06/earthquake.jpg",
				"http://gfxspeak.com/wp-content/uploads/2012/06/Cypress_LCluff_sm.jpg",
				"http://i.telegraph.co.uk/multimedia/archive/02405/weather-flood-sign_2405295b.jpg",
				"http://upload.wikimedia.org/wikipedia/commons/0/00/Flood102405.JPG",
				"http://www.lfpc.org/admin245937/my_documents/my_pictures/33FZ7_fire-forest.jpg",
				"http://upload.wikimedia.org/wikipedia/commons/6/6b/Mount_Carmel_forest_fire14.jpg" };
		return urls[(int) (Math.random() * urls.length)];
	}

	private void updatePics() {
		new QueryPicsTask().execute();
	}

	private void showAddWeatherPicDialog() {
		DialogFragment df = new DialogFragment() {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle(R.string.add_dialog_title);
				final View v = getLayoutInflater().inflate(
						R.layout.dialog_add_weatherpic, null);
				builder.setView(v);
				builder.setPositiveButton(android.R.string.ok,
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String caption = ((EditText) v
										.findViewById(R.id.captionEditText))
										.getText().toString();
								String url = ((EditText) v
										.findViewById(R.id.urlEditText))
										.getText().toString();
								if (url.length() == 0) {
									url = randomImageUrl();
								}
								Weatherpic wp = new Weatherpic();
								wp.setCaption(caption);
								wp.setImageUrl(url);
								new AddWeatherPicTask().execute(wp);
							}
						});
				builder.setNegativeButton(android.R.string.cancel, null);
				return builder.create();
			}
		};
		df.show(getFragmentManager(), "add wp");
	}

	private class QueryPicsTask extends
			AsyncTask<Void, Void, WeatherpicCollection> {

		@Override
		protected WeatherpicCollection doInBackground(Void... params) {
			WeatherpicCollection pics = null;
			try {
				List query = mService.weatherpic().list();
				query.setLimit(50L);
				query.setOrder("-last_touch_date_time");
				pics = query.execute();
			} catch (IOException e) {
				Log.e(WP, "Could not get weather pic list: " + e);
			}
			return pics;
		}

		@Override
		protected void onPostExecute(WeatherpicCollection result) {
			super.onPostExecute(result);
			if (result == null) {
				Log.e(WP, "Weather pic list is null");
				return;
			}
			mWeatherPics = result.getItems();
			WeatherPicArrayAdapter adapter = new WeatherPicArrayAdapter(
					MainActivity.this, mWeatherPics);
			mListView.setAdapter(adapter);
		}

	}

	private class AddWeatherPicTask extends
			AsyncTask<Weatherpic, Void, Weatherpic> {

		@Override
		protected Weatherpic doInBackground(Weatherpic... params) {
			Weatherpic wp = null;
			try {
				wp = mService.weatherpic().insert(params[0]).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return wp;
		}

		@Override
		protected void onPostExecute(Weatherpic result) {
			super.onPostExecute(result);
			updatePics();
		}

	}

}
