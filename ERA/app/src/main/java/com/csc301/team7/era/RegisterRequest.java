package com.csc301.team7.era;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://prostomial-independ.000webhostapp.com/Register.php";

    private Map<String, String> params;

    public RegisterRequest(String name, String email, String password, int age, String gender, String phone, String address, String
            erContact, String erNumber, String mIssue, Response.Listener<String> listener ){

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        //creating the parameters for the post request

        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        params.put("age", age + "");
        params.put("gender", gender);
        params.put("phone", phone);
        params.put("address", address);
        params.put("erContact", erContact);
        params.put("erNumber", erNumber);
        params.put("mIssue", mIssue);
    }

    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
