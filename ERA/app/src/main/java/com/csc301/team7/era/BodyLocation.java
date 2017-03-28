package com.csc301.team7.era;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by Spas on 2017-03-27.
 */

public class BodyLocation  extends AppCompatActivity {
    private int id;
    Diagnosis aa;
    RequestQueue queue;
    TextView aaaa;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location);
        queue = Volley.newRequestQueue(this);
        aa = new Diagnosis(queue);
        aaaa = (TextView) findViewById(R.id.res);

    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    id = 16;
                    break;
            case R.id.radioButton2:
                if (checked)
                    id = 7;
                    break;
            case R.id.radioButton3:
                if (checked)
                    id = 15;
                    break;
            case R.id.radioButton4:
                if (checked)
                    id = 6;
                    break;
            case R.id.radioButton5:
                if (checked)
                    id = 10;
                    break;
            case R.id.radioButton6:
                if (checked)
                    id = 17;
                    break;
        }
    }

    public void transition(View view) {

        try{ //this is how you do stuff with the api
            aa._diagnosisClient.loadFromWebService("body/locations/"+id, new TypeReference<List<HealthItem>>() {
            },new DiagnosisClient.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        List <HealthItem> resultsObject = objectMapper.readValue(response, new TypeReference<List<HealthItem>>(){});
                        Intent b = new Intent(BodyLocation.this,SubBodyLocation.class);
                        b.putExtra(EXTRA_MESSAGE,response);
                        startActivity(b);
                    }
                    catch(IOException e){
                        Log.d("how dare u","aas");
                    }
                }
            });
        }
        catch (Exception e){

        }

    }
}
