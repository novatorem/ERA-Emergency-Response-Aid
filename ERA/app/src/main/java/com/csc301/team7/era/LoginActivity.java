package com.csc301.team7.era;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

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

        btLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                final String username = etLoginEmail.getText().toString();
                final String password = etLoginPassword.getText().toString();

                //creating a response listener
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainActivityIntent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Registration Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }

        });


    }
}
