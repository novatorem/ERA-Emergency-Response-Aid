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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spas on 2017-03-27.
 */

public class DiagnosisScreen extends AppCompatActivity {
    private int id;
    Diagnosis aa;
    RequestQueue queue;
    ScrollView sv;
    TextView aaaa;
    LinearLayout linearLayout;
    ArrayList<Integer> rlist;
    ArrayList<Integer> slist;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_screen);
        queue = Volley.newRequestQueue(this);
        aa = new Diagnosis(queue);
        TextView hey = new TextView(this);
        hey.setText("Diagnosis");
        hey.setTextSize(24);
        hey.setTextColor(Color.BLACK);
        hey.setGravity(Gravity.CENTER);
        hey.setTypeface(null, Typeface.BOLD);
        linearLayout = new LinearLayout(this);
        sv = new ScrollView(this);
        setContentView(sv);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(hey);

        Intent intent = getIntent();
        String message = intent.getStringExtra(BodyLocation.EXTRA_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            List<HealthDiagnosis> resultsObject = objectMapper.readValue(message, new TypeReference<List<HealthDiagnosis>>(){});
            for (int i = 0; i < resultsObject.size(); i++){
                TextView c = new TextView(this);
                c.setText(resultsObject.get(i).Issue.IcdName + " - " + resultsObject.get(i).Issue.Accuracy + " %");
                c.setTextColor(Color.BLACK);
                c.setTextSize((float)20.0);
                TextView d = new TextView(this);
                String haha = "Specialisations: ";
                //d.setText("Specialisations: ");
                for (int j=0; j< resultsObject.get(i).Specialisation.size(); j++){
                    haha += resultsObject.get(i).Specialisation.get(j).Name + ", ";
                }
                //haha += "\n --------------------------------------";
                d.setText(haha);
                d.setTextSize((float)20.0);
                d.setTextColor(Color.RED);
                final Button e = new Button(this);
                e.setText("More Info");
                e.setTextColor(Color.BLUE);
                e.setBackgroundColor(Color.TRANSPARENT);
                e.setId(resultsObject.get(i).Issue.ID);
                e.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getMoreInfo(v,e.getId());

                    }
                });
                linearLayout.addView(c);
                linearLayout.addView(d);
                linearLayout.addView(e);
            }
        }catch(IOException e){

        }
        sv.addView(linearLayout);
    }

    private void getMoreInfo(View v, int e){
        try{
            aa._diagnosisClient.loadFromWebService("issues/"+e+"/info", new TypeReference<List<HealthDiagnosis>>(){},new DiagnosisClient.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        HealthIssueInfo resultsObject = objectMapper.readValue(response, new TypeReference<HealthIssueInfo>(){});
                        Intent b = new Intent(DiagnosisScreen.this,MoreIssueInfo.class);
                        b.putExtra(EXTRA_MESSAGE,response);
                        startActivity(b);


                    }
                    catch(IOException e){
                        Log.d("how dare u","aas");
                    }
                }
            });
        }catch(Exception t){

        }



    }

}
