package com.csc301.team7.era;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
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

public class SelectSymptoms  extends AppCompatActivity {
    private int id;
    Diagnosis aa;
    RequestQueue queue;
    TextView aaaa;
    ScrollView sv;
    LinearLayout linearLayout;
    ArrayList<Integer> rlist;
    ArrayList<Integer> slist;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_symptoms);
        rlist = new ArrayList<Integer>();
        slist = new ArrayList<Integer>();
        queue = Volley.newRequestQueue(this);
        aa = new Diagnosis(queue);
        TextView hey = new TextView(this);
        hey.setText("Select Symptoms");
        hey.setTextSize(24);
        hey.setTextColor(Color.BLACK);
        hey.setGravity(Gravity.CENTER);
        hey.setTypeface(null, Typeface.BOLD);
        linearLayout = new LinearLayout(this);
        sv = new ScrollView(this);
        setContentView(sv);
        //setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(hey);
        Intent intent = getIntent();
        String message = intent.getStringExtra(BodyLocation.EXTRA_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<HealthSymptomSelector> resultsObject = objectMapper.readValue(message, new TypeReference<List<HealthSymptomSelector>>(){});
            for (int i = 0; i < resultsObject.size(); i++){
                CheckBox c = new CheckBox(this);
                c.setText(resultsObject.get(i).Name);
                c.setId(resultsObject.get(i).ID);
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCheckBoxClicked(v);
                    }
                });
                rlist.add(resultsObject.get(i).ID);
                linearLayout.addView(c);

            }
            Button bb = new Button(this);
            aaaa = new TextView(this);
            bb.setText("Submit");
            bb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //transition(v);
                    String action = "diagnosis?symptoms=" + slist + "&gender=" + Gender.Male.toString() + "&year_of_birth=" + 1988;
                    try {
                        aa._diagnosisClient.loadFromWebService(action, new TypeReference<List<HealthDiagnosis>>(){},new DiagnosisClient.VolleyCallback() {
                            @Override
                            public void onSuccess(String response) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                try {
                                    List <HealthDiagnosis> resultsObject = objectMapper.readValue(response, new TypeReference<List<HealthDiagnosis>>(){});
                                    Intent b = new Intent(SelectSymptoms.this,DiagnosisScreen.class);
                                    b.putExtra(EXTRA_MESSAGE,response);
                                    startActivity(b);
                                }
                                catch(IOException e){
                                    Log.d("how dare u","aas");
                                }
                            }
                        });
                    }catch(Exception e){

                    }
                }
            });
            linearLayout.addView(bb);
            sv.addView(linearLayout);
        }
        catch(IOException e){

        }

    }


    public void onCheckBoxClicked(View view) {
        CheckBox rb;
        slist = new ArrayList<Integer>();
        for (int i = 0; i < rlist.size(); i++){
            rb = (CheckBox) findViewById(rlist.get(i));
            if (rb.isChecked()){
                slist.add(rlist.get(i));
            }
        }


    }
}
