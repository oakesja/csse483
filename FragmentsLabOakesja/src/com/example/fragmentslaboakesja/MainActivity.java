package com.example.fragmentslaboakesja;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity implements OnDataPassToTwoListener, OnRandomColorChangeListener {

	public static final String DEBUG_TAG = "FRAG";
	private FragmentOne mFragOne;
	private FragmentTwo mFragTwo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fragMan = getFragmentManager();
		FragmentTransaction fragTran = fragMan.beginTransaction();
		
		mFragOne =  new FragmentOne();
		mFragTwo = new FragmentTwo(); 
		
		fragTran.add(R.id.fragmentOneContainer, mFragOne);
		fragTran.add(R.id.fragmentTwoContainer, mFragTwo);
		fragTran.commit();
	}

	@Override
	public void passDataToTwo(String data) {
		mFragTwo.setText(data);
	}

	@Override
	public void randomizeColor() {
		int max = 255;
		int r = (int)(Math.random()*max);
		int g = (int)(Math.random()*max);
		int b = (int)(Math.random()*max);
		mFragOne.setBackground(Color.rgb(r, g, b));;
	}
}
