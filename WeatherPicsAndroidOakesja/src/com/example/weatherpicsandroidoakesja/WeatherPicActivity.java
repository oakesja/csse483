package com.example.weatherpicsandroidoakesja;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class WeatherPicActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_pic);
		
		Intent i = getIntent();
		String url = i.getStringExtra(MainActivity.WP_URL_KEY);
		String title = i.getStringExtra(MainActivity.WP_CAPTION_KEY);
		
		setTitle(title);
		
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		new DownloadImageTask(iv).execute(url);
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		private ImageView iv;

		public DownloadImageTask(ImageView iv) {
			this.iv = iv;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			String url = params[0];
			InputStream in = null;
			try {
				in = new java.net.URL(url).openStream();
				bitmap = BitmapFactory.decodeStream(in);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null && this.iv.getDrawable() == null) {
				this.iv.setImageBitmap(result);
			}
		}
	}
}
