package com.csc301.team7.era;

import android.app.Application;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpStatus;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import android.content.Intent;
import android.util.Log;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONObject;

import Decoder.BASE64Encoder;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DiagnosisClient extends Application{

    private AccessToken token;
    private AccessToken accessToken;
    private String language;
    private String healthServiceUrl;

    private CloseableHttpClient httpclient;
    private RequestQueue queue;
    private String aa;

    /// <summary>
    /// DiagnosisClient constructor
    /// </summary>
    /// <param name="username">api user username</param>
    /// <param name="password">api user password</param>
    /// <param name="authServiceUrl">priaid login url (https://authservice.priaid.ch/login)</param>
    /// <param name="language">language</param>
    /// <param name="healthServiceUrl">priaid healthservice url(https://healthservice.priaid.ch)</param>
    public DiagnosisClient(String userName, String password, String authServiceUrl, String language, String healthServiceUrl, RequestQueue queuee) throws Exception {

        HandleRequiredArguments(userName, password, authServiceUrl, language, healthServiceUrl);

        //httpclient = HttpClients.createDefault();
        Log.d("asa","asas");
        queue = queuee;

        this.healthServiceUrl = healthServiceUrl;
        this.language = language;
        Log.d("asa","asas");

        LoadToken(userName, password, authServiceUrl);

    }

    public interface VolleyCallback{
        void onSuccess(String result);
    }


    private void storeToken(AccessToken tok){
        token = tok;
    }

    private void LoadToken(String username, String password, String url) throws Exception {

        SecretKeySpec keySpec = new SecretKeySpec(
                password.getBytes(),
                "HmacMD5");

        String computedHashString = "";
        final String user = username;
        final String pass = password;
        final String urll = url;
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] result = mac.doFinal(url.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();
            computedHashString = encoder.encode(result);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (NoSuchAlgorithmException)");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (InvalidKeyException)");
        }
        final String computedHashStringg = computedHashString;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        //haha = response;
                        ObjectMapper objectMapper = new ObjectMapper();
                        try{
                            accessToken = objectMapper.readValue(response, AccessToken.class);
                            storeToken(accessToken);

                        }catch (IOException e){

                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "you fucked up");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + user + ":" + computedHashStringg);

                return params;
            }
        };
        queue.add(postRequest);

    }

    private void HandleRequiredArguments(String username, String password, String authServiceUrl, String language, String healthServiceUrl) {
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Argument missing: username");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Argument missing: password");

        if (authServiceUrl == null || authServiceUrl.isEmpty())
            throw new IllegalArgumentException("Argument missing: authServiceUrl");

        if (language == null || language.isEmpty())
            throw new IllegalArgumentException("Argument missing: language");

        if (healthServiceUrl == null || healthServiceUrl.isEmpty())
            throw new IllegalArgumentException("Argument missing: healthServiceUrl");
    }

    private void setResponse(String r){
        aa = r;
    }

    public void loadFromWebService(String action, final TypeReference<?> valueTypeRef, final VolleyCallback callback) throws Exception {
        String extraArgs = "token=" + token.Token + "&format=json&language=" + this.language;
        String url = new StringBuilder(this.healthServiceUrl).append("/").append(action).append(action.contains("?") ? "&" : "?").append(extraArgs).toString();
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            //T resultsObject = objectMapper.readValue(aa.toString(), valueTypeRef);
                            setResponse(response);
                            callback.onSuccess(response);
                        }catch(Exception e){
                            Log.d("asdasd","what the duck now");
                        }


                        // display response

                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "yoyo222");
                    }
                }
        );
        queue.add(getRequest);

    }

    /// <summary>
    /// Load all symptoms
    /// </summary>
    /// <returns>Returns list of all symptoms</returns>
    public List<HealthItem> loadSymptoms() throws Exception {
        loadFromWebService("symptoms", new TypeReference<List<HealthItem>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        Log.d("asasa", "never3");
        ObjectMapper objectMapper = new ObjectMapper();
        Log.d("asasa", "never2");
        List <HealthItem> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        Log.d("asasa + aaa ", aa);
        return resultsObject;
    }

    /// <summary>
    /// Load all issues
    /// </summary>
    /// <returns>Returns list of all issues</returns>
    public List<HealthItem> loadIssues() throws Exception {
        loadFromWebService("issues", new TypeReference<List<HealthItem>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        Log.d("asasa", "never3");
        ObjectMapper objectMapper = new ObjectMapper();
        Log.d("asasa", "never2");
        List <HealthItem> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        Log.d("asasa + aaa ", aa);
        return resultsObject;
    }

    /// <summary>
    /// Load detail informations about selected issue
    /// </summary>
    /// <param name="issueId"></param>
    /// <returns>Returns detail informations about selected issue</returns>
    public HealthIssueInfo loadIssueInfo(int issueId) throws Exception {
        String action = "issues/" + issueId + "/info";
        loadFromWebService(action, new TypeReference<HealthIssueInfo>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        Log.d("asasa", "never3");
        ObjectMapper objectMapper = new ObjectMapper();
        Log.d("asasa", "never2");
        HealthIssueInfo resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        Log.d("asasa + aaa ", aa);
        return resultsObject;

    }

    /// <summary>
    /// Load calculated list of potential issues for selected parameters
    /// </summary>
    /// <param name="selectedSymptoms">List of selected symptom ids</param>
    /// <param name="gender">Selected person gender (Male, Female)</param>
    /// <param name="yearOfBirth">Selected person year of born</param>
    /// <returns>Returns calculated list of potential issues for selected parameters</returns>
    public List<HealthDiagnosis> loadDiagnosis(List<Integer> selectedSymptoms, Gender gender, int yearOfBirth) throws Exception {
        if (selectedSymptoms == null || selectedSymptoms.size() == 0)
            throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

        String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
        String action = "diagnosis?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;
        loadFromWebService(action, new TypeReference<List<HealthDiagnosis>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <HealthDiagnosis> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;

    }


    /// <summary>
    /// Load calculated list of specialisations for selected parameters
    /// </summary>
    /// <param name="selectedSymptoms">List of selected symptom ids</param>
    /// <param name="gender">Selected person gender (Male, Female)</param>
    /// <param name="yearOfBirth">Selected person year of born</param>
    /// <returns>Returns calculated list of specialisations for selected parameters</returns>
    public List<DiagnosedIssue> loadSpecialisations(List<Integer> selectedSymptoms, Gender gender, int yearOfBirth) throws Exception {
        if (selectedSymptoms == null || selectedSymptoms.size() == 0)
            throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

        String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
        String action = "diagnosis/specialisations?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;
        loadFromWebService(action, new TypeReference<List<DiagnosedIssue>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <DiagnosedIssue> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;

    }


    /// <summary>
    /// Load human body locations
    /// </summary>
    /// <returns>Returns list of human body locations</returns>
    public List<HealthItem> loadBodyLocations() throws Exception {
        //String a;

        loadFromWebService("body/locations", new TypeReference<List<HealthItem>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <HealthItem> resultsObject = objectMapper.readValue("asas", new TypeReference<List<HealthItem>>(){});
        return resultsObject;

    }

    /// <summary>
    /// Load human body sublocations for selected human body location
    /// </summary>
    /// <param name="bodyLocationId">Human body location id</param>
    /// <returns>Returns list of human body sublocations for selected human body location</returns>
    public List<HealthItem> loadBodySubLocations(int bodyLocationId) throws Exception {
        String action = "body/locations/" + bodyLocationId;
        loadFromWebService(action, new TypeReference<List<HealthItem>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <HealthItem> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;
    }

    /// <summary>
    /// Load all symptoms for selected human body location
    /// </summary>
    /// <param name="locationId">Human body sublocation id</param>
    /// <param name="selectedSelectorStatus">Selector status (Man, Woman, Boy, Girl)</param>
    /// <returns>Returns list of all symptoms for selected human body location</returns>
    public List<HealthSymptomSelector> loadSublocationSymptoms(int locationId, SelectorStatus selectedSelectorStatus) throws Exception {
        String action = "symptoms/" + locationId + "/" + selectedSelectorStatus.toString();
        loadFromWebService(action, new TypeReference<List<HealthSymptomSelector>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <HealthSymptomSelector> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;
    }

    ///<summary>
    /// Load list of proposed symptoms for selected symptoms combination
    /// </summary>
    /// <param name="selectedSymptoms">List of selected symptom ids</param>
    /// <param name="gender">Selected person gender (Male, Female)</param>
    /// <param name="yearOfBirth">Selected person year of born</param>
    /// <returns>Returns list of proposed symptoms for selected symptoms combination</returns>
    public List<HealthItem> loadProposedSymptoms(List<Integer> selectedSymptoms, Gender gender, Integer yearOfBirth) throws Exception {
        if (selectedSymptoms == null || selectedSymptoms.size() == 0)
            throw new IllegalArgumentException("selectedSymptoms  Can not be null or empty");

        String serializedSymptoms = new ObjectMapper().writeValueAsString(selectedSymptoms);
        String action = "symptoms/proposed?symptoms=" + serializedSymptoms + "&gender=" + gender.toString() + "&year_of_birth=" + yearOfBirth;

        loadFromWebService(action, new TypeReference<List<HealthItem>>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        List <HealthItem> resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;
    }

    /// <summary>
    /// Load red flag text for selected symptom
    /// </summary>
    /// <param name="symptomId">Selected symptom id</param>
    /// <returns>Returns red flag text for selected symptom</returns>
    public String loadRedFlag(int symptomId) throws Exception {
        String action = "redflag?symptomId=" + symptomId;
        loadFromWebService(action, new TypeReference<String>() {
        },new VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                // do stuff here
                aa = response;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        String resultsObject = objectMapper.readValue(aa, new TypeReference<List<HealthItem>>(){});
        return resultsObject;
    }
}
