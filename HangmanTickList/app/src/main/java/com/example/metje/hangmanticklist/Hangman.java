package com.example.metje.hangmanticklist;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Hangman extends AppCompatActivity
{
	private String word;
	private String guess;
	private int numGuess;
	private int numGuessUsed;
	private boolean gameended;
	Collection<View> invisibleButtonsList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hangman);
		initWordGuess();
	}

	private String generateWord()
	{
		String [] words = {"handler","against","horizon","chops","junkyard","amoeba","academy","roast",
				"countryside","children","strange","best","drumbeat","amnesiac","chant","amphibian","smuggler","fetish"};
		Random r = new Random();
		int index = r.nextInt(words.length);
		return words[index];
	}

	private void initWordGuess()
	{
		word = generateWord();
		gameended = false;
		numGuessUsed = 0;
		numGuess = word.length() + 5;
		char[] chars = new char[word.length()];
		Arrays.fill(chars, '*');
		guess = new String(chars);
		TextView resView = (TextView)findViewById(R.id.resView);
		resView.setText(guess + " consists of " + guess.length() + " letters.");
	}

	public void processGuess(View v)
	{
		Button b = (Button) v;
		String guessL = b.getText().toString();
		TextView resView = (TextView)findViewById(R.id.resView);

		if (guessL.compareTo("reset") == 0) {
			endgame();
			initWordGuess();
			return;
		}

		if (gameended == true)
			return;

		v.setVisibility(View.INVISIBLE);
		invisibleButtonsList.add(v);
		numGuessUsed++;

		if (word.indexOf(guessL) == -1)
		{
			resView.setText(guess + " used " + numGuessUsed + " of " + numGuess + " guesses.");

			Toast toast = Toast.makeText(this, "You're STUPID!\n Do better next time!", Toast.LENGTH_SHORT);
			TextView toastV = (TextView) toast.getView().findViewById(android.R.id.message);
			toastV.setTextColor(Color.RED);
			toastV.setBackgroundColor(Color.CYAN);
			toastV.setGravity(Gravity.CENTER);
			toast.show();
		}
		else
		{
			for (int x=0; x<word.length(); x++)
			{
				int index = word.indexOf(guessL, x);

				if (index == -1)
					break;
				char[] guessChars = guess.toCharArray();
				guessChars[index] = guessL.charAt(0);
				guess = String.valueOf(guessChars);
				resView.setText(guess + " used " + numGuessUsed + " of " + numGuess + " guesses.");
			}
		}

		if (word.compareTo(guess) == 0)
		{
			resView.setText("You WIN, the word was: " + guess + ". You used " + numGuessUsed + " guesses.");
			endgame();
		}

		if (numGuessUsed >= numGuess)
		{
			if (word.compareTo(guess) == 0)
				resView.setText("You WIN, the word was: " + guess + ". You used " + numGuessUsed + " guesses.");
			else
				resView.setText("You lose, the word was: " + word + ". You used " + numGuessUsed + " guesses.");
			endgame();
		}
	}

	private void endgame()
	{
		gameended = true;

		for (View v : invisibleButtonsList)
			v.setVisibility(View.VISIBLE);
	}
}

