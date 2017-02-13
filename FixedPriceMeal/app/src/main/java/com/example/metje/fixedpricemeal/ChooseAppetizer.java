package com.example.metje.fixedpricemeal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ChooseAppetizer extends AppCompatActivity
{
	private ArrayList<ImageModel> imageModelList;
	private int[] imageList = new int[]{
		R.drawable.bacon, R.drawable.beetles,
		R.drawable.rattlesnake, R.drawable.chicken,
		R.drawable.blob
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_appetizer);

		imageModelList = populateList();
		ListView listView = (ListView) findViewById(R.id.appListView);
		CustomAdaptor customAdapter = new CustomAdaptor(this, imageModelList);
		listView.setAdapter(customAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
				Intent intent = getIntent();
				String text = imageModelList.get(index).getName();
//				Toast.makeText(getApplicationContext(), "you have chosen: " + text, Toast.LENGTH_SHORT).show();
				Bundle bundle = new Bundle();
				bundle.putString("choice", text);
				intent.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}

	private ArrayList<ImageModel> populateList()
	{
		ArrayList<ImageModel> imageModelList = new ArrayList<>();
		String[] appetizerOptions = getResources().getStringArray(R.array.app_choices);
		for (int x=0; x < appetizerOptions.length; x++)
			imageModelList.add(new ImageModel(appetizerOptions[x], imageList[x]));
		return imageModelList;
	}
}
