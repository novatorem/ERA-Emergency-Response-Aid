package com.csc301.team7.era;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //creating variables for the text fields and buttons
        final EditText etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        final Button btLogin = (Button) findViewById(R.id.btLogin);
        final TextView tvSignUpLink = (TextView) findViewById(R.id.tvSignUp);


        //create on click listener for the text view to connect to the register activity

        tvSignUpLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });


    }
}
