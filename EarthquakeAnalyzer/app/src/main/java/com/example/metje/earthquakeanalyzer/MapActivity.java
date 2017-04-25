package com.example.metje.earthquakeanalyzer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends AppCompatActivity
{
	private SQLiteDatabase earthquakeDB;
	private Map<String,String> eqDataHash;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener()
	{

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item)
		{
			switch (item.getItemId())
			{
				case R.id.navigation_info:
					setInfoView();
					return true;
				case R.id.navigation_map:

					return true;
				case R.id.navigation_buddies:

					return true;
			}
			return false;
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		eqDataHash = new HashMap<>();

		eqDataHash.put("id",Integer.toString(getIntent().getExtras().getInt("id")));

		earthquakeDB = openOrCreateDatabase("earthquakeDB",MODE_PRIVATE,null);
		setEQData();
	}

	private void setEQData()
	{
		Cursor res = earthquakeDB.rawQuery("Select * from earthquake where id="+ eqDataHash.get("id") +";",null);
		if (res.getCount() <= 0)
			Log.wtf("WTF", "The eq got deleted somehow. WTF!!!");
		res.moveToFirst();

		eqDataHash.put("datetime",res.getString(res.getColumnIndex("datetime")));
		eqDataHash.put("lat",res.getString(res.getColumnIndex("lat")));
		eqDataHash.put("lng",res.getString(res.getColumnIndex("lng")));
		eqDataHash.put("depth",Integer.toString(res.getInt(res.getColumnIndex("depth"))));
		eqDataHash.put("src",res.getString(res.getColumnIndex("src")));
		eqDataHash.put("eqid",res.getString(res.getColumnIndex("eqid")));
		eqDataHash.put("magnitude",res.getString(res.getColumnIndex("magnitude")));
		eqDataHash.put("isfav",Integer.toString(res.getInt(res.getColumnIndex("isfav"))));
	}

	private void setMapView()
	{

	}

	private void setInfoView()
	{
		FrameLayout content  = (FrameLayout) findViewById(R.id.content);

		TextView magnitudeText = new TextView(this);
		TextView datetimeText = new TextView(this);
		TextView latlngText = new TextView(this);
		TextView depthText = new TextView(this);
		TextView srcEqidText = new TextView(this);

		magnitudeText.setText("Magnitude: " + eqDataHash.get("magnitude"));
		datetimeText.setText("Date and Time: " + eqDataHash.get("magnitude"));
		latlngText.setText("Lat and Long: " + eqDataHash.get("magnitude"));
		depthText.setText("Depth: " + eqDataHash.get("magnitude"));
		srcEqidText.setText("Src and Equid: " + eqDataHash.get("magnitude"));


		FrameLayout.LayoutParams llp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		llp.gravity = Gravity.CENTER;
//		llp.width =
		llp.setMargins(16, 16, 16, 16); // llp.setMargins(left, top, right, bottom);

		magnitudeText.setLayoutParams(llp);
		datetimeText.setLayoutParams(llp);
		latlngText.setLayoutParams(llp);
		depthText.setLayoutParams(llp);
		srcEqidText.setLayoutParams(llp);

		content.addView(magnitudeText);
		content.addView(datetimeText);
		content.addView(latlngText);
		content.addView(depthText);
		content.addView(srcEqidText);
	}
}
