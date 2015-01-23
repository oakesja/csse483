package com.example.fragmentslaboakesja;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FragmentOne extends Fragment implements OnClickListener {

	private OnDataPassToTwoListener mListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragement_one, container, false);
		((Button) v.findViewById(R.id.buttonOne)).setOnClickListener(this);
		return v;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnDataPassToTwoListener) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ "does not implement OnDataPassToTwoListener");
		}
	}

	@Override
	public void onClick(View v) {
		mListener.passDataToTwo(getString(R.string.one));
	}

	public void setBackground(int c) {
		Log.d(MainActivity.DEBUG_TAG, c + "");
		getActivity().findViewById(R.id.fragmentOne)
				.setBackgroundColor(c);
	}
}
