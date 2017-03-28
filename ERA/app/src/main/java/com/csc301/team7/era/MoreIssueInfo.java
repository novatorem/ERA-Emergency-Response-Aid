package com.csc301.team7.era;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by Spas on 2017-03-28.
 */

public class MoreIssueInfo extends AppCompatActivity {
    LinearLayout linearLayout;
    ScrollView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        TextView hey = new TextView(this);
        hey.setText("More Info");
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
            HealthIssueInfo resultsObject = objectMapper.readValue(message, new TypeReference<HealthIssueInfo>(){});
            TextView ad = new TextView(this);
            ad.setText(resultsObject.Name + " (Professional Name: " + resultsObject.ProfName + ")"
            + "\n--------------------------------------------");
            ad.setTextSize((float)20.00);
            ad.setTextColor(Color.BLUE);
            TextView ae = new TextView(this);
            ae.setText("Description: \n" + resultsObject.Description +
            "\n--------------------------------------------");
            ae.setTextSize((float)20.00);
            ae.setTextColor(Color.BLACK);
            TextView af = new TextView(this);
            af.setText("Medical Condition: \n" + resultsObject.MedicalCondition +
            "\n--------------------------------------------");
            af.setTextSize((float)20.00);
            af.setTextColor(Color.BLACK);
            TextView ag = new TextView(this);
            ag.setText("Treatment Description: \n" + resultsObject.TreatmentDescription +
            "\n--------------------------------------------");
            ag.setTextSize((float)20.00);
            ag.setTextColor(Color.BLACK);
            TextView ah = new TextView(this);
            ah.setText("Possible symptoms: \n" + resultsObject.PossibleSymptoms +
            "\n--------------------------------------------");
            ah.setTextSize((float)20.00);
            ah.setTextColor(Color.BLACK);
            linearLayout.addView(ad);
            linearLayout.addView(ae);
            linearLayout.addView(af);
            linearLayout.addView(ag);
            linearLayout.addView(ah);
            sv.addView(linearLayout);

        }catch(IOException e){

        }

    }
}
