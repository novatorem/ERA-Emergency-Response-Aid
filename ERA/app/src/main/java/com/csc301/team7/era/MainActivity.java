package com.csc301.team7.era;

import android.app.Dialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    EditText simpleEditText;
    Button btnSearch;
    RequestQueue queue;
    Diagnosis aa;
    HashMap <Integer, String> bp = new HashMap<Integer, String>();
    HashMap <Integer, String> sbp = new HashMap<Integer, String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        queue = Volley.newRequestQueue(this);
        aa = new Diagnosis(queue);
        bp.put(16, "Abdomen, pelvis and buttocks");
        bp.put(7, "Arms and shoulder");
        bp.put(15, "Chest and back");
        bp.put(6, "Head, throat and neck");
        bp.put(10, "Legs");
        bp.put(17, "Skin, joints and general");
        sbp.put(36,"Abdomen");
        sbp.put(40,"Buttocks and Rectum");
        sbp.put(38,"Genitals and Groin");
        sbp.put(39,"Hips and hip joint");
        sbp.put(37,"Pelvis");
    }
    public void gotoMedicalPage(View view) {
        Intent medicalPage = new Intent(MainActivity.this, MedicalPage.class);
        startActivity(medicalPage);
    }

    public void searchMedical(View view){
        Intent abc = new Intent(MainActivity.this,BodyLocation.class);
        startActivity(abc);
    }
    public void gotoLocationPage(View view) {
        Intent locationPage = new Intent(MainActivity.this, LocationPage.class);
        startActivity(locationPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

}
