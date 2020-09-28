package io.github.dzulfikar68.k24memberapp.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARE_PREF_NAME = "USER";
    private static final String KEY_IS_ADMIN = "KEY_IS_ADMIN";
    private static final String KEY_ID_USER = "KEY_ID_USER";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefManager mInstance;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private SharedPrefManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean setLogin(Boolean isAdmin, int id, String username, String password) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isAdmin) {
            editor.putBoolean(KEY_IS_ADMIN, true);
            editor.apply();
            return true;
        } else if (id > 0) {
            editor.putInt(KEY_ID_USER, id);
            editor.putString(USERNAME, username);
            editor.putString(PASSWORD, password);
            editor.apply();
            return true;
        } else {
            return false;
        }

    }

    public boolean isAdmin() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_ADMIN, false);
    }

    public int isUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID_USER, -1);
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, null);
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }

    public boolean logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARE_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
