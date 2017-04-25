package com.example.metje.earthquakeanalyzer;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
	private Spinner choice_spinner;
	private SQLiteDatabase earthquakeDB;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> magnitudeArrayList;
	private ArrayList<String> latlongArrayList;
	private ArrayList<Integer> idArrayList;
	private ListView quake_view;
	private boolean debug = false;

	private JSONObject jsonobject;
	private JSONArray jsonarray;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		choice_spinner = (Spinner) findViewById(R.id.choice_spinner);
		quake_view = (ListView) findViewById(R.id.quake_view);

		earthquakeDB = openOrCreateDatabase("earthquakeDB",MODE_PRIVATE,null);
		earthquakeDB.execSQL("CREATE TABLE IF NOT EXISTS earthquake(id INTEGER PRIMARY KEY AUTOINCREMENT, datetime VARCHAR, lat VARCHAR, lng VARCHAR, depth integer, src VARCHAR, eqid VARCHAR, magnitude VARCHAR, isfav integer);");

		Log.i("info", "before create if");
		if (isDataIntianted())
			populateQuakeData();
		else
			new DownloadJSON().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		Log.i("info", "end create");
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		choice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
			{
				switch (i)
				{
					case 0: Snackbar.make(view, "Feature coming soon. :)", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
						break;
					case 1: Snackbar.make(view, "Feature coming soon. :)", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
						break;
					case 2: Snackbar.make(view, "Feature coming soon. :)", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
						break;
					case 3: Snackbar.make(view, "Feature coming soon. :)", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView)
			{
				// Pass
			}
		});

		quake_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
				Bundle bundle  = new Bundle();

				bundle.putInt("id",idArrayList.get((i)));

				mapIntent .putExtras(bundle);
				startActivity(mapIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		switch (id)
		{
			case R.id.action_show_map:
				// TODO: finsih action show map
				break;
			case R.id.action_num_quakes:
				Snackbar.make(this.getCurrentFocus(), "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
				break;
			case R.id.action_fav_eqs:
				// TODO: finsih action show eqs
				break;
			case R.id.action_help:
				// TODO: finsih action show help
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	private boolean isDataIntianted()
	{
		long cnt  = DatabaseUtils.queryNumEntries(earthquakeDB, "earthquake");
		Log.i("info", "checked initiated with" + Long.toString(cnt));
		return cnt > 0;
	}

	private void searchUserRequest()
	{
		String latRequest = ((EditText) findViewById(R.id.user_lat_request)).getText().toString();
		String lngRequest = ((EditText) findViewById(R.id.user_lng_request)).getText().toString();
		String magRequest = ((EditText) findViewById(R.id.user_mag_request)).getText().toString();
	}

	private void populateQuakeData()
	{
		Log.i("info", "populating data");
		if (debug)
		{
			ArrayList<String> debuglist = new ArrayList<>();
			for (int x=0; x<10; x++)
				debuglist.add(Integer.toString(x) + " to the bank hahaha");
			ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, debuglist);
			quake_view.setAdapter(arrayAdapter);
			return;
		}

		magnitudeArrayList = new ArrayList<>();
		latlongArrayList = new ArrayList<>();
		idArrayList = new ArrayList<>();

		Cursor res = earthquakeDB.rawQuery("Select * from earthquake",null);
		res.moveToFirst();

		while(!res.isAfterLast()){
			magnitudeArrayList.add(res.getString(res.getColumnIndex("magnitude")));
			latlongArrayList.add("Lat: " + res.getString(res.getColumnIndex("lat")) + ", Long: " + res.getString(res.getColumnIndex("lng")));
			idArrayList.add(res.getInt(res.getColumnIndex("id")));
			res.moveToNext();
		}

		res.close();

//		adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, quakeArrayList);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, magnitudeArrayList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView text1 = (TextView) view.findViewById(android.R.id.text1);
				TextView text2 = (TextView) view.findViewById(android.R.id.text2);

				text1.setText(magnitudeArrayList.get(position));
				text2.setText(latlongArrayList.get(position));
				return view;
			}
		};

		quake_view.setAdapter(adapter);
		Log.i("info", "finish populating with length " +Integer.toString(magnitudeArrayList.size()));
	}

	private class DownloadJSON extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			Log.i("info", "Started background");
			jsonobject = JSONfunctions.getJSONfromURL("http://api.geonames.org/earthquakesJSON?north=90.0&south=-90.0&east=175.4&west=-180.0&lang=de&maxRows=100&username=bobandroid");

			try {
				// Locate the NodeList name
				jsonarray = jsonobject.getJSONArray("earthquakes");
				for (int i = 0; i < jsonarray.length(); i++)
				{
					jsonobject = jsonarray.getJSONObject(i);

					String datetime = jsonobject.optString("datetime");
					String lng = jsonobject.optString("lng");
					String lat = jsonobject.optString("lat");
					String depth = jsonobject.optString("depth");
					String src = jsonobject.optString("src");
					String eqid = jsonobject.optString("eqid");
					String magnitude = jsonobject.optString("magnitude");
					String isfav = Integer.toString(0);

					earthquakeDB.execSQL("INSERT INTO earthquake (datetime, lat, lng, depth, src, eqid, magnitude, isfav) VALUES('" + datetime + "','" + lat + "','" + lng  + "'," + depth  + ",'" + src  + "','" + eqid + "','" + magnitude + "'," + isfav + ");");
				}
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void args)
		{
			populateQuakeData();
		}
	}
}
