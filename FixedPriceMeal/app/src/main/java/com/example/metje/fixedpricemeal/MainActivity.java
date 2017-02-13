package com.example.metje.fixedpricemeal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private String customerName;
	private int customerTable;
	private int numfilled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    numfilled = 0;
	    customerName = "";
	    customerTable = -1;

	    View.OnClickListener operatorButtons = new View.OnClickListener()
	    {
		    public void onClick(View v)
		    {
				switch (v.getId())
				{
					case R.id.appetizerButton:
						Intent appetizerIntent = new Intent(MainActivity.this, ChooseAppetizer.class);
						startActivityForResult(appetizerIntent, 101);
						break;
					case R.id.pastaButton:
						Intent pastaIntent = new Intent(MainActivity.this, ChoosePasta.class);
						startActivityForResult(pastaIntent, 102);
						break;
					case R.id.meatButton:
						Intent meatIntent = new Intent(MainActivity.this, ChooseMeat.class);
						startActivityForResult(meatIntent, 103);
						break;
					case R.id.dessertButton:
						Intent dessertIntent = new Intent(MainActivity.this, ChooseDessert.class);
						startActivityForResult(dessertIntent, 104);
						break;
					case R.id.clearButton:
						AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(MainActivity.this);
						cancelBuilder.setMessage(R.string.ckear_dialog_title)
								.setTitle(R.string.clear_dialog_title);
						cancelBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								initResViews();
								numfilled = 0;
								Toast clearToast = Toast.makeText(MainActivity.this, "You cleared!", Toast.LENGTH_SHORT);
								TextView clearToastV = (TextView) clearToast.getView().findViewById(android.R.id.message);
								clearToastV.setTextColor(Color.CYAN);
								clearToastV.setGravity(Gravity.CENTER);
								clearToast.show();
							}
						});
						cancelBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(MainActivity.this, "Good Save :)", Toast.LENGTH_SHORT).show();
							}
						});
						cancelBuilder.show();
//						AlertDialog dialog = builder.create();
						break;
					case R.id.confirmButton:
						if (numfilled < 4)
						{
							Toast.makeText(MainActivity.this, "Finish order and submit again!", Toast.LENGTH_SHORT).show();
							break;
						}

						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setTitle(R.string.confirm_dialog_title);
						final EditText nameInput = new EditText(MainActivity.this);
						nameInput.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
						nameInput.setHint("Name");
						final EditText tableInput = new EditText(MainActivity.this);
						tableInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
						tableInput.setHint("Table");


						LinearLayout layout = new LinearLayout(MainActivity.this);
						layout.setOrientation(LinearLayout.VERTICAL);
						layout.addView(nameInput);
						layout.addView(tableInput);

						builder.setView(layout);
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								try
								{
									customerName = nameInput.getText().toString();
									customerTable = Integer.parseInt(tableInput.getText().toString());
									if (customerName.length() ==0 || customerTable == -1)
									{
										dialog.dismiss();
										Toast.makeText(MainActivity.this, "Please review Customer info and submit again!", Toast.LENGTH_SHORT).show();
										return;
									}
									numfilled = 0;
									initResViews();
									Toast confirmToast = Toast.makeText(MainActivity.this, customerName + "'s Order at at table " + customerTable + " has been received!", Toast.LENGTH_LONG);
									TextView confirmToastV = (TextView) confirmToast.getView().findViewById(android.R.id.message);
									confirmToastV.setTextColor(Color.GREEN);
									confirmToastV.setGravity(Gravity.CENTER);
									confirmToast.show();
									customerName ="";
									customerTable=-1;
								}
								catch (Exception e)
								{
									Toast confirmToast = Toast.makeText(MainActivity.this, "Please review Customer info and submit again!", Toast.LENGTH_LONG);
									TextView badInputToastV = (TextView) confirmToast.getView().findViewById(android.R.id.message);
									badInputToastV.setGravity(Gravity.CENTER);
									confirmToast.show();
									dialog.dismiss();
								}
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
								Toast.makeText(MainActivity.this, "Please review and submit again!", Toast.LENGTH_SHORT).show();
							}
						});

						builder.show();

						break;
				}
		    }
	    };

	    LinearLayout mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
		bindButtons(mainLayout, operatorButtons);
	    initResViews();
    }

	private void bindButtons(ViewGroup view, View.OnClickListener operatorButtons)
	{
		for (int i = 0; i < view.getChildCount(); i++)
		{
			View v = view.getChildAt(i);

			if (v instanceof Button)
				v.setOnClickListener(operatorButtons);
			else if (v instanceof ViewGroup)
				bindButtons((ViewGroup) v, operatorButtons);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		try	{
			if (resultCode == Activity.RESULT_OK)
			{
				Bundle myResults = data.getExtras();
				String choice = myResults.getString("choice");
				TextView resView;
				String newText;
				numfilled++;
				switch (requestCode)
				{
					case 101:
						resView = (TextView) findViewById(R.id.appTextView);
						newText = String.format(getResources().getString(R.string.app_choice_text), choice);
						resView.setText(newText);
						break;
					case 102:
						resView = (TextView) findViewById(R.id.pastaTextView);
						newText = String.format(getResources().getString(R.string.pasta_choice_text), choice);
						resView.setText(newText);
						break;
					case 103:
						resView = (TextView) findViewById(R.id.meatTextView);
						newText = String.format(getResources().getString(R.string.meat_choice_text), choice);
						resView.setText(newText);
						break;
					case 104:
						resView = (TextView) findViewById(R.id.desTextView);
						newText = String.format(getResources().getString(R.string.des_choice_text), choice);
						resView.setText(newText);
						break;
				}
			}
		}
		catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Something went wrong. Whoops!!!", Toast.LENGTH_SHORT).show();
		}
	}

	private void initResViews()
	{
		ViewGroup view = (ViewGroup) findViewById(R.id.resViews);
		for (int i = 0; i < view.getChildCount(); i++)
		{
			TextView v = (TextView) view.getChildAt(i);
			String text="";

			switch (v.getId())
			{
				case R.id.appTextView:
					text = String.format(getResources().getString(R.string.app_choice_text), "");
					break;
				case R.id.pastaTextView:
					text = String.format(getResources().getString(R.string.pasta_choice_text), "");
					break;
				case R.id.meatTextView:
					text = String.format(getResources().getString(R.string.meat_choice_text), "");
					break;
				case R.id.desTextView:
					text = String.format(getResources().getString(R.string.des_choice_text), "");
					break;
			}

			v.setText(text);
		}
	}
}
