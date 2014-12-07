package edu.rosehulman.hellobuttonjao;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private int mCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mCount = 0;
		
		final TextView messageText = (TextView)findViewById(R.id.message_text);
		Button incButton = (Button)findViewById(R.id.increment_button);
		
		incButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCount++;	
				String s = getString(R.string.message_format, mCount);
				messageText.setText(s);
				
				if(mCount > 10){
					int color = getResources().getColor(R.color.background);
					messageText.setTextColor(color);
				}
			}
		});
	}
}
