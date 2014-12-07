package edu.rosehulman.tictactoeoakesja;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button[][] mButtons;
	private TicTacToeGame mGame;
	private Button mNewGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGame = new TicTacToeGame(this);

		mButtons = new Button[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLUMNS];
		// mButtons[0][0] = (Button)findViewById(R.id.button00);
		// mButtons[0][1] = (Button)findViewById(R.id.button01);
		// mButtons[0][2] = (Button)findViewById(R.id.button02);
		// mButtons[1][0] = (Button)findViewById(R.id.button10);
		// mButtons[1][1] = (Button)findViewById(R.id.button11);
		// mButtons[1][2] = (Button)findViewById(R.id.button12);
		// mButtons[2][0] = (Button)findViewById(R.id.button20);
		// mButtons[2][1] = (Button)findViewById(R.id.button21);
		// mButtons[2][2] = (Button)findViewById(R.id.button22);

		// try getRosourcs().getIdentifier("button00", "id", getPackageName());

		for (int i = 0; i < TicTacToeGame.NUM_ROWS; i++) {
			for (int j = 0; j < TicTacToeGame.NUM_COLUMNS; j++) {
				int id = getResources().getIdentifier("button" + i + j, "id",
						getPackageName());
				mButtons[i][j] = (Button) findViewById(id);
				mButtons[i][j].setOnClickListener(this);
			}
		}

		mNewGameButton = (Button) findViewById(R.id.newGameButton);
		mNewGameButton.setOnClickListener(this);
		updateGame();
	}

	@Override
	public void onClick(View v) {
		Log.d("TTT", "button clicked");
		if (v.getId() == R.id.newGameButton) {
			Log.d("TTT", "new game");
			mGame.resetGame();
		} else {
			for(int i = 0; i < TicTacToeGame.NUM_ROWS; i++){
				for(int j = 0; j < TicTacToeGame.NUM_COLUMNS; j++){
					if(mButtons[i][j].getId() == v.getId()){
						Log.d("TTT", "button" + i + j + "pressed");
						mGame.pressedButtonAtLocation(i, j);
					}
				}
			}
		}
		updateGame();
	}
	
	private void updateGame(){
		for(int i = 0; i < TicTacToeGame.NUM_ROWS; i++){
			for(int j = 0; j < TicTacToeGame.NUM_COLUMNS; j++){
				mButtons[i][j].setText(mGame.stringForButtonAtLocation(i, j));
			}
		}
		
		TextView tv = (TextView)findViewById(R.id.gameStateTextView);
		tv.setText(mGame.stringForGameState());
	}
}
