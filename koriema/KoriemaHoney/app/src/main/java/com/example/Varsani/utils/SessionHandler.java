package com.example.Varsani.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.Varsani.Clients.Models.UserModel;

import java.util.Date;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_CLIENT_ID = "clientID";
    private static final String KEY_ID = "ID";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_DATE_CREATED = "dateCreated";
    private static final String KEY_EMPTY = "";
    private static final String KEY_PHONE_NO = "phone_no";
    // check user type
    private static final String KEY_USER_TYPE = "user_type";


    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param firstname
     * @param username
     */
    public void loginUser(String clientID,String firstname,String lastname,String username,String phoneno,
                          String email,String dateCreated,String user_type) {

        mEditor.putString(KEY_CLIENT_ID, clientID);
        mEditor.putString(KEY_FIRST_NAME, firstname);
        mEditor.putString(KEY_LAST_NAME, lastname);
        mEditor.putString(KEY_USER_NAME, username);
        mEditor.putString(KEY_PHONE_NO, phoneno);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_DATE_CREATED, dateCreated);
        mEditor.putString(KEY_USER_TYPE,user_type);
//        mEditor.putString(KEY_PHOTO_URL, photoUrl);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    public void loginUser_2(String ID,String firstname,String lastname,String username,String phoneno,
                          String email,String dateCreated,String user_type) {

        mEditor.putString(KEY_ID, ID);
        mEditor.putString(KEY_FIRST_NAME, firstname);
        mEditor.putString(KEY_LAST_NAME, lastname);
        mEditor.putString(KEY_USER_NAME, username);
        mEditor.putString(KEY_PHONE_NO, phoneno);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_DATE_CREATED, dateCreated);
        mEditor.putString(KEY_USER_TYPE,user_type);
//        mEditor.putString(KEY_PHOTO_URL, photoUrl);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }


    /**
     * Checks whether user is logged in
     *
     * @return
     */

    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public UserModel getUserDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        UserModel user = new UserModel();
        user.setUser_type(mPreferences.getString(KEY_USER_TYPE, KEY_EMPTY));
        user.setClientID(mPreferences.getString(KEY_CLIENT_ID, KEY_EMPTY));
        user.setFirstname(mPreferences.getString(KEY_FIRST_NAME, KEY_EMPTY));
        user.setLastname(mPreferences.getString(KEY_LAST_NAME, KEY_EMPTY));
        user.setUsername(mPreferences.getString(KEY_USER_NAME, KEY_EMPTY));
        user.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
        user.setPhoneNo(mPreferences.getString(KEY_PHONE_NO, KEY_EMPTY));
        user.setDateCreated(mPreferences.getString(KEY_DATE_CREATED, KEY_EMPTY));

        return user;
    }
//    public BnfryModel getUserDetails_2() {
//        //Check if user is logged in first
//        if (!isLoggedIn()) {
//            return null;
//        }
//        BnfryModel user = new BnfryModel();
//        user.setUser_type(mPreferences.getString(KEY_USER_TYPE, KEY_EMPTY));
//        user.setID(mPreferences.getString(KEY_ID, KEY_EMPTY));
//        user.setFirstname(mPreferences.getString(KEY_FIRST_NAME, KEY_EMPTY));
//        user.setLastname(mPreferences.getString(KEY_LAST_NAME, KEY_EMPTY));
//        user.setUsername(mPreferences.getString(KEY_USER_NAME, KEY_EMPTY));
//        user.setEmail(mPreferences.getString(KEY_EMAIL, KEY_EMPTY));
//        user.setPhoneNo(mPreferences.getString(KEY_PHONE_NO, KEY_EMPTY));
//        user.setDateCreated(mPreferences.getString(KEY_DATE_CREATED, KEY_EMPTY));
//
//        return user;
//    }


    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();

    }
}
