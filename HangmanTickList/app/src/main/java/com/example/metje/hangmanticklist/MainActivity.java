package com.example.metje.hangmanticklist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] values = new String[]{"Hangman", "Tic Tac Toe"};

		listView = (ListView) findViewById(R.id.list);

//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, getString(R.array.item_names));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			                        int position, long id) {
				// ListView Clicked item index
				switch (position)
				{
					case 0:
						Intent hangmanIntent = new Intent(MainActivity.this, Hangman.class);
						startActivity(hangmanIntent);
						break;
					case 1:
						Intent tictactoeIntent = new Intent(MainActivity.this, TicTacToe.class);
						startActivity(tictactoeIntent);
						break;
				}
			}
		});
	}
}
