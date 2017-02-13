package com.example.metje.hangmanticklist;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class TicTacToe extends AppCompatActivity {

	private Square[][] squares = new Square[3][3];
	private DecimalFormat twodigits = new DecimalFormat("00");
	private  boolean gameended;
	private char winner;
	private int numWins, numLosses, numTies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tic_tac_toe);
		numWins = numLosses = numTies =0;
		TextView scoreView = (TextView)findViewById(R.id.scoreView);
		scoreView.setText("Score Results");

		initGame();

		View.OnClickListener operatorButtons = new View.OnClickListener()
		{
			public void onClick(View v)
			{
				Button b = (Button) v;
				if (b.getId() == R.id.newGame)
				{
					initGame();
					return;
				}
				if (gameended)
					return;
				if (!isValidMove(b))
					return;
				processUserMove(b);
				if (checkWin() || isFull())
				{
					endgame();
					return;
				}
				else
					makeMove();
				if (checkWin())
					endgame();

			}
		};

		for (int x=0; x<3; x++)
		{
			for (int y=0; y<3; y++)
			{
				String tag = "c" + twodigits.format(10*x+y);
				int id = getResources().getIdentifier(tag, "id", getPackageName());
				View v = findViewById(id);
				v.setOnClickListener(operatorButtons);
			}
		}

		Button newGame = (Button) findViewById(R.id.newGame);
		newGame.setOnClickListener(operatorButtons);
	}

	private void initGame()
	{
		TextView infoView = (TextView)findViewById(R.id.infoView);
		infoView.setText(R.string.make_move);

		for (int x=0; x<3; x++)
		{
			for (int y=0; y<3; y++)
			{
				String tag = "c" + twodigits.format(10*x+y);
				int id = getResources().getIdentifier(tag, "id", getPackageName());
				View v = findViewById(id);
				squares[x][y] = new Square('e', v);
			}
		}

		gameended = false;
		winner = 'e';
	}

	private boolean isValidMove(Button b)
	{
		return b.getText().equals("");
	}

	private void processUserMove(Button b)
	{
		int loc = Integer.parseInt(b.getTag().toString());
		int x = loc / 10;
		int y = loc % 10;
		squares[x][y].markUser();
	}

	private void makeMove()
	{
		while (true)
		{
			Random r = new Random();
			int x = r.nextInt(3);
			int y = r.nextInt(3);

			Square square = squares[x][y];
			if (square.isEmpty())
			{
				squares[x][y].markComputer();
				break;
			}
		}
	}

	private boolean isFull()
	{
		for (int x=0; x<3; x++)
			for (int y = 0; y < 3; y++)
				if (squares[x][y].isEmpty())
					return false;
		return true;
	}

	private boolean checkWin()
	{
		if (!squares[0][1].isEmpty())
		{
			if (squares[0][1].getOwner() == squares[0][0].getOwner() && squares[0][1].getOwner() == squares[0][2].getOwner())
			{
				winner = squares[0][1].getOwner();
				return true;
			}
			if (squares[0][1].getOwner() == squares[1][1].getOwner() && squares[0][1].getOwner() == squares[2][1].getOwner())
			{
				winner = squares[0][1].getOwner();
				return true;
			}
		}
		if (!squares[1][1].isEmpty())
		{
			if (squares[1][1].getOwner() == squares[0][0].getOwner() && squares[1][1].getOwner() == squares[2][2].getOwner())
			{
				winner = squares[1][1].getOwner();
				return true;
			}
			if (squares[1][1].getOwner() == squares[0][2].getOwner() && squares[1][1].getOwner()== squares[2][0].getOwner())
			{
				winner = squares[1][1].getOwner();
				return true;
			}
			if (squares[1][1].getOwner() == squares[1][0].getOwner() && squares[1][1].getOwner() == squares[1][2].getOwner())
			{
				winner = squares[1][1].getOwner();
				return true;
			}
		}
		if (!squares[2][1].isEmpty())
		{
			if (squares[2][1].getOwner() == squares[2][0].getOwner() && squares[2][1].getOwner() == squares[2][2].getOwner())
			{
				winner = squares[2][1].getOwner();
				return true;
			}
		}
		if (!squares[0][0].isEmpty())
		{
			if (squares[0][0].getOwner() == squares[1][0].getOwner() && squares[0][0].getOwner() == squares[2][0].getOwner())
			{
				winner = squares[0][0].getOwner();
				return true;
			}
		}
		if (!squares[0][2].isEmpty())
		{
			if (squares[0][2].getOwner() == squares[1][2].getOwner() && squares[0][2].getOwner() == squares[2][2].getOwner())
			{
				winner = squares[0][2].getOwner();
				return true;
			}
		}
		return  false;
	}

	private void endgame()
	{
		TextView infoView = (TextView)findViewById(R.id.infoView);
		TextView scoreView = (TextView)findViewById(R.id.scoreView);

		int duration = Toast.LENGTH_SHORT;


		if (winner == 'u')
		{
			Toast toast = Toast.makeText(this, "You Won!\n Not so stupid after all. \n 2/10", Toast.LENGTH_LONG);
			TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
			toastV.setTextColor(Color.RED);
			toastV.setBackgroundColor(Color.CYAN);
			toastV.setGravity(Gravity.CENTER);
			toast.show();

			numWins++;
			infoView.setText(R.string.won_game);
			scoreView.setText("won: " + numWins + " Losses: " + numLosses + " Ties: " + numTies);
		}
		else if (winner == 'c')
		{
			Toast toast = Toast.makeText(this, "You're STUPID!\n Do better next time!", Toast.LENGTH_LONG);
			TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
			toastV.setTextColor(Color.RED);
			toastV.setBackgroundColor(Color.CYAN);
			toastV.setGravity(Gravity.CENTER);
			toast.show();

			numLosses++;
			infoView.setText(R.string.lost_game);
			scoreView.setText("won: " + numWins + " Losses: " + numLosses + " Ties: " + numTies);
		}
		else
		{
			Toast toast = Toast.makeText(this, "A TIE???\n IDIOT \n 1/10", Toast.LENGTH_LONG);
			TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
			toastV.setTextColor(Color.RED);
			toastV.setBackgroundColor(Color.CYAN);
			toastV.setGravity(Gravity.CENTER);
			toast.show();

			numTies++;
			infoView.setText(R.string.tie_game);
			scoreView.setText("won: " + numWins + " Losses: " + numLosses + " Ties: " + numTies);
		}

		gameended = true;
	}
}
