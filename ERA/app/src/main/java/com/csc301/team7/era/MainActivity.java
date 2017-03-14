package com.csc301.team7.era;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoMedicalPage(View view) {
        Intent medicalPage = new Intent(MainActivity.this, MedicalPage.class);
        startActivity(medicalPage);
    }


}
