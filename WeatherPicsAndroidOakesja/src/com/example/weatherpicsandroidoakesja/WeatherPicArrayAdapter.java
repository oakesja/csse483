package com.example.weatherpicsandroidoakesja;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.appspot.oakesja_weatherpics.weatherpics.model.Weatherpic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherPicArrayAdapter extends ArrayAdapter<Weatherpic> {

	public WeatherPicArrayAdapter(Context context, List<Weatherpic> objects) {
		super(context, R.layout.list_item_weather_pic, R.id.weatherPicText,
				objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, convertView, parent);
		Weatherpic pic = getItem(position);
		((TextView) v.findViewById(R.id.weatherPicText)).setText(pic
				.getCaption());
		ImageView iv = (ImageView)v.findViewById(R.id.weatherPicImage);
		new DownloadImageTask(iv).execute(pic.getImageUrl());
		return v;
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
				bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(in), 99, 69, true);
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
