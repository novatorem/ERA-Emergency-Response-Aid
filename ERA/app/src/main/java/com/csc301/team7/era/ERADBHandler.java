package com.csc301.team7.era;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;


/**
 * Created by MuhammadAun on 2017-03-08.
 * Setting up database and quering functionality (insert, update, find and delete)
 */

public class ERADBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "eradb.db";

    //Tables with in the database eradb.db
    private static final String TABLE_USERS = "appusers";
    private static final String TABLE_ERISSUES = "erissues";
    private static final String TABLE_BOOKMARK = "bookmarks";

    //Columns for user table
    private static final String COLUMN_FIRST_NAME = "fName";
    private static final String COLUMN_LAST_NAME = "lName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONTACT_NUMBER = "cNumber";
    private static final String COLUMN_EMERGENCY_CONTACT = "eNumber";
    private static final String COLUMN_HEALTH_ISSUES = "hIssues";

    //Columns for emergency issue table (erissues)
    private static final String COLUMN_USER_EMAIL = "uemail";
    private static final String COLUMN_EMERGENCY_ISSUE = "eIssue";
    private static final String COLUMN_EMERGENCY_RESPONSE = "eResponse";

    //column for bookmark table
    private static final String COLUMN_BOOKMARK_NAME = "bname";
    private static final String COLUMN_BOOKMARK_ISSUE = "bissue";
    private static final String COLUMN_BOOKMARK_SOLUTION = "bsolution";


    public ERADBHandler(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " +
                TABLE_USERS + "("
                + COLUMN_FIRST_NAME + "TEXT," + COLUMN_LAST_NAME
                + "TEXT," + COLUMN_EMAIL + "VARCHAR(50) PRIMARY KEY,"+ COLUMN_PASSWORD +
                "TEXT" + COLUMN_CONTACT_NUMBER + "INTEGER" + COLUMN_EMERGENCY_CONTACT + "INTEGER" +
                COLUMN_HEALTH_ISSUES + "TEXT"+ ")";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_EMERGENCY_ISSUE_TABLE = "CREATE TABLE " +
                TABLE_ERISSUES + "("
                + COLUMN_USER_EMAIL + " VARCHAR(50)," + COLUMN_EMERGENCY_ISSUE
                + " TEXT PRIMARY KEY," + COLUMN_EMERGENCY_RESPONSE + " TEXT" + ")";
        db.execSQL(CREATE_EMERGENCY_ISSUE_TABLE);

        String CREATE_BOOKMARK_TABLE = "CREATE TABLE" + TABLE_BOOKMARK + "(" +
                COLUMN_BOOKMARK_NAME + "VARCHAR(50) PRIMARY KEY," + COLUMN_BOOKMARK_ISSUE + " TEXT,"
                + COLUMN_BOOKMARK_SOLUTION + " TEXT" + ")";
        db.execSQL(CREATE_BOOKMARK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //for migration change it to migrate data to the new version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ERISSUES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_BOOKMARK);
        onCreate(db);
    }

    /**
     * Insert function for user table
     * @param user
     */
    public void addUser(User user){
        ContentValues values = new ContentValues();

        values.put(COLUMN_FIRST_NAME, user.getFirst_name());
        values.put(COLUMN_LAST_NAME, user.getLast_name());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_CONTACT_NUMBER, user.getContact());
        values.put(COLUMN_EMERGENCY_CONTACT, user.getEmergency_contact());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    /**
     * Update user password
     * @param user
     */
    public void updateUserPassword(User user){
        String email = user.getEmail();
        ContentValues args = new ContentValues();
        args.put(COLUMN_PASSWORD, user.getPassword());
        String strFilter = "email" + email;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, args, strFilter, null);
    }

    /**
     * Update user contact number
     * @param user
     */
    public void updateUserContact(User user){
        String email = user.getEmail();
        ContentValues args = new ContentValues();
        args.put(COLUMN_CONTACT_NUMBER, user.getContact());
        String strFilter = "email=" + email;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, args, strFilter, null);
    }

    /**
     * Update user emergency contact
     * @param user
     */
    public void updateUserEmergencyContact(User user){
        String email = user.getEmail();
        ContentValues args = new ContentValues();
        args.put(COLUMN_EMERGENCY_CONTACT, user.getEmergency_contact());
        String strFilter = "email=" + email;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, args, strFilter, null);
    }

    /**
     * Update user emergency issue
     * @param user
     */
    public void updateUserEmergencyIssue(User user){
        String email = user.getEmail();
        ContentValues args = new ContentValues();
        args.put(COLUMN_HEALTH_ISSUES, user.getMedical_condition());
        String strFilter = "email=" + email;
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, args, strFilter, null);
    }

    /**
     * Delete a user
     * @param user
     * @return result
     */
    public boolean deleteProduct(User user) {

        boolean result;
        String email = user.getEmail();
        String query =  COLUMN_EMAIL+ "=" + email;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_USERS, query, null) > 0;
        return result;
    }

    // Insert, update, find and delete functionality for TABLE_ERISSUES

    /**
     * Adding user email, associated er issue and er response
     * @param ei
     * @param user
     */
    public void addERIssue(EmergencyIssues ei, User user){
        if(ei.getValidated()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_EMERGENCY_ISSUE, ei.getIssue());
            values.put(COLUMN_EMERGENCY_RESPONSE, ei.getResponse());
            SQLiteDatabase db = this.getWritableDatabase();

            db.insert(TABLE_ERISSUES, null, values);
            db.close();
        }
    }



    /**
     * Updating Er response for an existing issue
     * @param ei
     */
    public void updateErresponse(EmergencyIssues ei){
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMERGENCY_RESPONSE, ei.getResponse());
        String query = "eIssue=" + ei.getIssue();
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, values, query, null);
    }

    /**
     * Finding the first issue searched by the user
     * @param issue
     * @return emergency issue
     */
    public EmergencyIssues findEissue(String issue){
        String query = "Select * FROM " + TABLE_ERISSUES + " WHERE " + COLUMN_EMERGENCY_ISSUE + " =  \"" + issue + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        EmergencyIssues erissue = new EmergencyIssues();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            erissue.setUser_email(cursor.getString(0));
            erissue.setIssue(cursor.getString(1));
            erissue.setResponse(cursor.getString(2));
            cursor.close();
        } else {
            erissue = null;
        }
        db.close();
        return erissue;

    }

    /**
     * Deleting a user inputed issue
     * @param ei
     * @return
     */
    public boolean deleteErissue(EmergencyIssues ei) {

        boolean result;
        String erissue = ei.getIssue();
        String query =  COLUMN_EMERGENCY_ISSUE+ "=" + erissue;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_USERS, query, null) > 0;
        return result;
    }

    /**
     * Insert new issue into bookmark table
     * @param bmark
     */
    public void addBookmark(Bookmark bmark){
        ContentValues values = new ContentValues();

        values.put(COLUMN_BOOKMARK_NAME, bmark.getName());
        values.put(COLUMN_BOOKMARK_ISSUE, bmark.getIssue());
        values.put(COLUMN_BOOKMARK_ISSUE, bmark.getSolution());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_BOOKMARK, null, values);
        db.close();
    }

    /**
     * Deleting an existing bookmark
     * @param bmark
     * @return
     */
    public boolean deleteBookmark(Bookmark bmark){
        boolean result;
        String bname = bmark.getName();
        String query = COLUMN_BOOKMARK_NAME + "=" + bname;
        SQLiteDatabase db = this.getWritableDatabase();
        result = db.delete(TABLE_BOOKMARK, query, null) > 0;
        return result;
    }
}
