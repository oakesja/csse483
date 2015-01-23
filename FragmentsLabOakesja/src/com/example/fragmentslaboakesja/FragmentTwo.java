package com.example.fragmentslaboakesja;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTwo extends Fragment implements OnClickListener{

	private OnRandomColorChangeListener mListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_two, container, false);
		((Button)v.findViewById(R.id.buttonTwo)).setOnClickListener(this);
		return v;
	}

	public void setText(String text) {
		((TextView) getActivity().findViewById(R.id.textViewTwo)).setText(text);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnRandomColorChangeListener) activity;
		} catch (Exception e) {
			throw new ClassCastException(activity.toString()
					+ "does not implement OnRandomColorChangeListener");
		}
	}

	@Override
	public void onClick(View v) {
		mListener.randomizeColor();
	}
}
