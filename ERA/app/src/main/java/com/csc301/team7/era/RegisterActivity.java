package com.csc301.team7.era;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.util.JSONPObject;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private RadioButton radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //creating all the variables for all the appropriate user inputs for registration

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etERContact = (EditText) findViewById(R.id.etERContact);
        final EditText etERNumber = (EditText) findViewById(R.id.etERNumber);
        final RadioGroup rbGender = (RadioGroup) findViewById(R.id.rgGender);
        final EditText etMedicalCondition = (EditText) findViewById(R.id.etMedicalCondition);
        final Button bRegister = (Button) findViewById(R.id.btRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());
                final String phone = etPhone.getText().toString();
                final String address = etAddress.getText().toString();
                final String erContact = etERContact.getText().toString();
                final String erNumber = etERNumber.getText().toString();
                final String medicalCondition = etMedicalCondition.getText().toString();

                //getting the gender of the user
                int selectedId = rbGender.getCheckedRadioButtonId();
                radioGender = (RadioButton) findViewById(selectedId);

                final String gender = radioGender.getText().toString();

                //creating response listener
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            //if registration is a success change the view to login page

                            if(success){
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                //registering request

                RegisterRequest registerRequest = new RegisterRequest(name, email, password, age, gender, phone,
                        address, erContact, erNumber, medicalCondition,responseListener);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
