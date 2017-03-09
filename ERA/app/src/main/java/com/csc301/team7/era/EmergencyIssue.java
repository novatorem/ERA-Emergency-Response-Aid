package com.csc301.team7.era;

/**
 * Created by MuhammadAun on 2017-03-08.
 * User submitted issues for emergency
 */

public class EmergencyIssues {

    private String user_email;
    private String issue;
    private String response;
    private boolean validated;

    public EmergencyIssues(User user, String issue, String response){
        this.user_email = user.getEmail();
        this.issue = issue;
        this.response = response;
        this.validated = false;
    }

    /**
     * Create getter for user issue
     * @return issue
     */
    public String getIssue(){
        return this.issue;
    }

    /**
     * Created getter for user response
     * @return response
     */
    public String getResponse(){
        return this.response;
    }

    public String getUser_email(){
        return this.user_email;
    }
    /**
     * Created setter to check the issue to validated by admin
     */
    public void setValidated(){
        this.validated = true;
    }

    /**
     * Created getter for validate variable of the issue
     * @return validated
     */
    public boolean getValidated(){
        return this.validated;
    }
}
