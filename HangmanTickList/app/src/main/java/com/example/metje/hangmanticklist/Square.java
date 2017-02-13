package com.example.metje.hangmanticklist;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

/**
 * Created by metje on 2/5/2017.
 */

public class Square
{
	private char owner;
	private Button b;

	public Square(char owner, View v)
	{
		this.owner = owner;
//        this.loc = loc;
		b = (Button) v;
		b.setText("");
	}

	public boolean isEmpty()
	{
		return owner == 'e';
	}

	public char getOwner() {
		return owner;
	}

	public void markUser()
	{
		b.setText("O");
		b.setTextColor(Color.rgb(0, 255, 0));
		owner = 'u';
	}

	public void markComputer()
	{
		b.setText("X");
		b.setTextColor(Color.rgb(255, 0, 0));
		owner = 'c';
	}
}
