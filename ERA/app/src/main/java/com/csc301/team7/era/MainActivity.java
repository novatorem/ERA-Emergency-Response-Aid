package com.csc301.team7.era;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText simpleEditText;
    Button btnSearch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        

    }



    public void gotoMedicalPage(View view) {
        Intent medicalPage = new Intent(MainActivity.this, MedicalPage.class);
        startActivity(medicalPage);
    }

    public void searchMedical(View view){

        Intent searchPage = new Intent(MainActivity.this, SearchActivity.class);
        EditText simpleEditText = (EditText) findViewById(R.id.mSearch);
        String strValue = simpleEditText.getText().toString();
        Log.i("strValue", strValue);
        searchPage.putExtra("TextBox", strValue);
        startActivity(searchPage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
}
