package com.csc301.team7.era;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    // Handles all the search queries from the user input to the GET reqeust for the APIMEDIC

    TextView simpleTextView;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistanceState){
        super.onCreate(savedInstanceState, persistanceState);
        setContentView(R.layout.diseases_page_layout);

        //retrieving values
        Intent intent = getIntent();
        String strIntent = intent.toString();
        Log.i("MyIntent", strIntent);
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.i("MyMessage", message);

        //testing the text view by placing user input as output
        simpleTextView = (TextView) findViewById(R.id.mResult);
        simpleTextView.setText(message);
    }
}
