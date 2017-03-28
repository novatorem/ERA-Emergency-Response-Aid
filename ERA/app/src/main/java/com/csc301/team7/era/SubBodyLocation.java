package com.csc301.team7.era;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spas on 2017-03-27.
 */

public class SubBodyLocation extends AppCompatActivity{
    private int id;
    Diagnosis aa;
    RequestQueue queue;
    TextView aaaa;
    LinearLayout linearLayout;
    ArrayList <Integer> rlist;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subbody_location);
        queue = Volley.newRequestQueue(this);
        aa = new Diagnosis(queue);
        aaaa = new TextView(this);
        TextView hey = new TextView(this);
        hey.setText("Select Sub-Body Part");
        hey.setTextSize(24);
        hey.setTextColor(Color.BLACK);
        hey.setGravity(Gravity.CENTER);
        hey.setTypeface(null, Typeface.BOLD);
        linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(hey);
        rlist = new ArrayList<Integer>();

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(BodyLocation.EXTRA_MESSAGE);


        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List <HealthItem> resultsObject = objectMapper.readValue(message, new TypeReference<List<HealthItem>>(){});
            RadioGroup ass = new RadioGroup(this);
            ass.setOrientation(RadioGroup.VERTICAL);
            for (int i = 0; i < resultsObject.size(); i++){
                RadioButton c = new RadioButton(this);
                c.setText(resultsObject.get(i).Name);
                c.setId(resultsObject.get(i).ID);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRadioButtonClicked(v);

                    }
                });
                rlist.add(resultsObject.get(i).ID);
                ass.addView(c);
            }
            linearLayout.addView(ass);
            Button bb = new Button(this);
            bb.setText("Submit");
            bb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transition(v);
                }
            });
            linearLayout.addView(bb);
        }
        catch (IOException e){

        }

    }

    public void onRadioButtonClicked(View view) {
        RadioButton rb;
        for (int i = 0; i < rlist.size(); i++){
            rb = (RadioButton) findViewById(rlist.get(i));
            if (rb.isChecked()){
                id = rlist.get(i);
            }
        }
    }

    public void transition(View v) {
        try{ //this is how you do stuff with the api
            aaaa = new TextView(this);
            aa._diagnosisClient.loadFromWebService("symptoms/"+id+"/"+SelectorStatus.Man.toString(), new TypeReference<List<HealthSymptomSelector>>() {
            },new DiagnosisClient.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        List <HealthSymptomSelector> resultsObject = objectMapper.readValue(response, new TypeReference<List<HealthSymptomSelector>>(){});
                        Intent b = new Intent(SubBodyLocation.this,SelectSymptoms.class);
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
