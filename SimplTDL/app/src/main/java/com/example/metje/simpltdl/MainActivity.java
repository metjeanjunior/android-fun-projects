package com.example.metje.simpltdl;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
	ArrayList<String> taskList = new ArrayList<String>();
	private static String LOG_TAG = "TODOLISTTASK";
	ArrayAdapter<String> arrayAdapter;
	Button addButton;
	ListView tdListview;
	EditText newtaskView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addButton = (Button) findViewById(R.id.addButton);
		tdListview = (ListView) findViewById(R.id.tdListview);
		newtaskView = (EditText) findViewById(R.id.newtaskView);
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		Object obj = getObjectFromFile(this, "taskList");

		if (obj != null && obj instanceof ArrayList)
			taskList = (ArrayList<String>) obj;
		else
			taskList = new ArrayList<>();

		arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
		tdListview.setAdapter(arrayAdapter);

		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				String task = newtaskView.getText().toString();
				if (task.length() < 2)
				{
					Toast.makeText(MainActivity.this, "The task is too short", Toast.LENGTH_LONG).show();
					return;
				}
				taskList.add(task);
				newtaskView.setText("");
				arrayAdapter.notifyDataSetChanged();
			}
		});

		tdListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				taskList.remove(i);
				arrayAdapter.notifyDataSetChanged();
				return  true;
			}
		});
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		saveObjectToFile(this, "taskList", taskList);
	}

	public static Object getObjectFromFile(Context context, String filename) {
		try {
			FileInputStream fis = context.openFileInput(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			Object object = ois.readObject();
			ois.close();

			return object;

		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "getObjectFromFile FileNotFoundException: " + e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e(LOG_TAG, "getObjectFromFile IOException: " + e.getMessage());
			return null;
		} catch (ClassNotFoundException e) {
			Log.e(LOG_TAG, "getObjectFromFile ClassNotFoundException: " + e.getMessage());
			return null;
		} catch (Exception e) {// Catch exception if any
			Log.e(LOG_TAG, "getBookmarksFromFile Exception: " + e.getMessage());
			return null;
		}
	}

	public static void saveObjectToFile(Context context, String fileName, Object obj) {

		try {
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();

		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "saveObjectToFile FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			Log.e(LOG_TAG, "saveObjectToFile IOException: " + e.getMessage());
		} catch (Exception e) {
			Log.e(LOG_TAG, "saveObjectToFile Exception: " + e.getMessage());
		}
	}
}
