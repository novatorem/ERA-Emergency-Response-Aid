package com.csc301.team7.era;

/**
 * Created by MuhammadAun on 2017-03-08.
 * User submitted issues for emergency
 */

public class EmergencyIssue {

    private String user_email;
    private String issue;
    private String response;
    private boolean validated;

    public EmergencyIssue(){

    }
    public EmergencyIssue(User user, String issue, String response){
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
     * Setting ER issue
     * @param issue
     */
    public void setIssue(String issue){this.issue = issue;}

    /**
     * Created getter for user response
     * @return response
     */
    public String getResponse(){
        return this.response;
    }

    /**
     * Updating Emergency response for the issue
     * @param response
     */
    public void setResponse(String response){this.response = response;}

    /**
     * Get associate user email with the emergency issue
     * @return user_email
     */
    public String getUser_email(){
        return this.user_email;
    }

    /**
     * Setting user email
     * @param email
     */
    public void setUser_email(String email){this.user_email = email;}

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
