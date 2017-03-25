package com.csc301.team7.era;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;

import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static com.csc301.team7.era.SampleDiagnosis.CheckRequiredArgs;


public class SearchActivity extends Activity {
    // Handles all the search queries from the user input to the GET reqeust for the APIMEDIC

    private static DiagnosisClient _diagnosisClient;
    Properties prop = new Properties();
    InputStream input = null;
    private String userName = "spasimir95@gmail.com";
    private String password = "Gt45MzPb96ReCc27W";
    private String authUrl = "https://sandbox-authservice.priaid.ch/login";
    private String healthUrl = "https://sandbox-healthservice.priaid.ch";
    private String language = "en-gb";

    static TextView simpleTextView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_issues);

        //retrieving values
        Intent intent = getIntent();
        String strIntent = intent.toString();

        String message = intent.getStringExtra("TextBox");
        Log.i("MyMessage", "test");

        //testing the text view by placing user input as output

        simpleTextView = (TextView)findViewById(R.id.mResult);

        if(message.equals("headache")){
            String symptoms = "You could possibly have a simple migraine or a concussion . \n Immediately consult your doctor";
            simpleTextView.setText(symptoms);
        }
        else if(message.equals("stomache")){
            String symptoms = "You could possibly have a stomach flu. \n Consult your doctor and advise him of what you eat last.";
            simpleTextView.setText(symptoms);
        }
        else{
            simpleTextView.setText(message);
        }
        /*
        CheckRequiredArgs(userName, password, authUrl, healthUrl, language);
        try {
            _diagnosisClient = new DiagnosisClient(userName, password, authUrl, language, healthUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }


            simulate();
        */

    }

    public static void CheckRequiredArgs(String userName, String password, String authUrl, String healthUrl, String language) {
        if (userName == null || userName.isEmpty())
            throw new IllegalArgumentException("username in config.properties is required");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("password in config.properties is required");

        if (authUrl == null || authUrl.isEmpty())
            throw new IllegalArgumentException("priaid_authservice_url in config.properties is required");

        if (healthUrl == null || healthUrl.isEmpty())
            throw new IllegalArgumentException("priaid_healthservice_url in config.properties is required");

        if (language == null || language.isEmpty())
            throw new IllegalArgumentException("language in config.properties is required");
    }


    public static int GetRandom(int maxNumber) {
        return ThreadLocalRandom.current().nextInt(0, maxNumber);
    }

    public static void writeHeaderMessage(String message) {
        Log.i("","---------------------------------------------");
        Log.i("message:",message);
        Log.i("","---------------------------------------------");
    }


    public static int loadBodySublocations(int locId) throws Exception {
        List<HealthItem> bodySublocations = _diagnosisClient.loadBodySubLocations(locId);

        if (bodySublocations == null || bodySublocations.size() == 0)
            throw new Exception("Empty body sublocations results");

        for (HealthItem loc : bodySublocations)
            System.out.println(loc.Name + " " + loc.ID);

        int randomLocIndex = GetRandom(bodySublocations.size());
        HealthItem randomLocation = bodySublocations.get(randomLocIndex);

        writeHeaderMessage("Sublocations symptoms for selected location " + randomLocation.Name);

        return randomLocation.ID;
    }


    public static Integer loadBodyLocations() throws Exception {
        List<HealthItem> bodyLocations = _diagnosisClient.loadBodyLocations();

        if (bodyLocations == null || bodyLocations.size() == 0)
            throw new Exception("Empty body locations results");

        writeHeaderMessage("Body locations:");

        for (HealthItem loc : bodyLocations)
            System.out.println(loc.Name + " (" + loc.ID + ")");

        int randomLocIndex = GetRandom(bodyLocations.size());
        HealthItem randomLocation = bodyLocations.get(randomLocIndex);

        writeHeaderMessage("Sublocations for randomly selected location " + randomLocation.Name);

        return randomLocation.ID;
    }


    public static List<HealthSymptomSelector> LoadSublocationSymptoms(int subLocId) throws Exception {
        List<HealthSymptomSelector> symptoms = _diagnosisClient.loadSublocationSymptoms(subLocId, SelectorStatus.Man);

        if (symptoms == null || symptoms.size() == 0) {
            Log.i("EmptyBody","Empty body sublocations symptoms results");
            return null;
        }

        writeHeaderMessage("Body sublocations symptoms:");

        for (HealthSymptomSelector sym : symptoms)
            System.out.println(sym.Name);

        int randomSymptomIndex = GetRandom(symptoms.size());

        randomSymptomIndex = GetRandom(symptoms.size());

        HealthSymptomSelector randomSymptom = symptoms.get(randomSymptomIndex);

        writeHeaderMessage("Randomly selected symptom: " + randomSymptom.Name);

        List<HealthSymptomSelector> selectedSymptoms = new ArrayList<HealthSymptomSelector>();
        selectedSymptoms.add(randomSymptom);

        LoadRedFlag(randomSymptom);

        return selectedSymptoms;
    }

    public static List<Integer> LoadDiagnosis(List<HealthSymptomSelector> selectedSymptoms) throws Exception {
        writeHeaderMessage("Diagnosis");

        List<Integer> selectedSymptomsIds = new ArrayList<Integer>();
        for (HealthSymptomSelector symptom : selectedSymptoms) {
            selectedSymptomsIds.add(symptom.ID);
        }

        List<HealthDiagnosis> diagnosis = _diagnosisClient.loadDiagnosis(selectedSymptomsIds, Gender.Male, 1988);

        if (diagnosis == null || diagnosis.size() == 0) {
            writeHeaderMessage("No diagnosis results for symptom " + selectedSymptoms.get(0).Name);
            return null;
        }

        for (HealthDiagnosis d : diagnosis) {
            String specialistions = "";
            for (MatchedSpecialisation spec : d.Specialisation)
                specialistions = specialistions.concat(spec.Name + ", ");
            Log.i("Specialization",d.Issue.Name + " - " + d.Issue.Accuracy + "% \nSpecialisations : " + specialistions);
        }

        List<Integer> retValue = new ArrayList<Integer>();
        for (HealthDiagnosis diagnose : diagnosis)
            retValue.add(diagnose.Issue.ID);
        return retValue;
    }


    public static void LoadSpecialisations(List<HealthSymptomSelector> selectedSymptoms) throws Exception {
        writeHeaderMessage("Specialisations");

        List<Integer> selectedSymptomsIds = new ArrayList<Integer>();
        for (HealthSymptomSelector symptom : selectedSymptoms) {
            selectedSymptomsIds.add(symptom.ID);
        }

        List<DiagnosedIssue> specialisations = _diagnosisClient.loadSpecialisations(selectedSymptomsIds, Gender.Male, 1988);

        if (specialisations == null || specialisations.size() == 0) {
            writeHeaderMessage("No specialisations for symptom " + selectedSymptoms.get(0).Name);
            return;
        }

        for (DiagnosedIssue s : specialisations)
            Log.i("DiagnosedIssue",s.Name + " - " + s.Accuracy + "%");
    }


    public static void LoadRedFlag(HealthSymptomSelector selectedSymptom) throws Exception {
        String redFlag = "Symptom " + selectedSymptom.Name + " has no red flag";

        if (selectedSymptom.HasRedFlag)
            redFlag = _diagnosisClient.loadRedFlag(selectedSymptom.ID);

        writeHeaderMessage(redFlag);
    }

    public static void LoadIssueInfo(int issueId) throws Exception {
        HealthIssueInfo issueInfo = _diagnosisClient.loadIssueInfo(issueId);
        writeHeaderMessage("Issue info");
        Log.i("Name","Name: " + issueInfo.Name);

    }

    static void LoadProposedSymptoms(List<HealthSymptomSelector> selectedSymptoms) throws Exception {
        List<Integer> selectedSymptomsIds = new ArrayList<Integer>();
        for (HealthSymptomSelector symptom : selectedSymptoms) {
            selectedSymptomsIds.add(symptom.ID);
        }
        List<HealthItem> proposedSymptoms = _diagnosisClient.loadProposedSymptoms(selectedSymptomsIds, Gender.Male, 1988);

        if (proposedSymptoms == null || proposedSymptoms.size() == 0) {
            writeHeaderMessage("No proposed symptoms for selected symptom " + selectedSymptoms.get(0).Name);
            return;
        }

        String proposed = "";
        for (HealthItem diagnose : proposedSymptoms)
            proposed = proposed.concat(diagnose.Name) + ", ";

       // writeHeaderMessage("Proposed symptoms: " + proposed);
        simpleTextView.setText(proposed);
    }


    static void simulate() {

        try {
            // Load body locations
            int selectedLocationID = loadBodyLocations();


            // Load body sublocations
            int selectedSublocationID = loadBodySublocations(selectedLocationID);

            // Load body sublocations symptoms
            List<HealthSymptomSelector> selectedSymptoms = LoadSublocationSymptoms(selectedSublocationID);

            // Load diagnosis
            List<Integer> diagnosis = LoadDiagnosis(selectedSymptoms);

            // Load specialisations
            LoadSpecialisations(selectedSymptoms);

            // Load issue info
            for (Integer issueId : diagnosis)
                LoadIssueInfo(issueId);

            // Load proposed symptoms
            LoadProposedSymptoms(selectedSymptoms);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
