package io.github.dzulfikar68.k24memberapp.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import io.github.dzulfikar68.k24memberapp.model.User;

import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_ADDRESS;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_BIRTH;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_CODE;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_EMAIL;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_FULLNAME;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_GENDER;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_ID;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_PASSWORD;
import static io.github.dzulfikar68.k24memberapp.model.User.COLUMN_TIMESTAMP;
import static io.github.dzulfikar68.k24memberapp.model.User.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "user_db";
    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(User.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public ArrayList<User> getAllData() {
        ArrayList<User> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
//                " WHERE "+ COLUMN_TYPE + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User venue = new User();
                venue.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                venue.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
                venue.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
                venue.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                venue.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                venue.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                venue.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                venue.setBirth(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH)));
                venue.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                movies.add(venue);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return movies;
    }

    //TODO INSERT
    public long insertUser(String name,
                           String email,
                           String password,
                           String birth,
                           String gender,
                           String address) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_CODE, getSaltString());
        values.put(COLUMN_FULLNAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_BIRTH, birth);
        values.put(COLUMN_GENDER, gender);

        long id = -1;
        try {
            // insert row
            id = db.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public boolean updatePassword(int id, String password) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(COLUMN_PASSWORD, password);

        boolean result = false;
        try {
            // update row
            db.update(TABLE_NAME, values, COLUMN_ID + "=" + id, null);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        // close db connection
        db.close();

        return result;
    }

    public boolean existCheck(String email) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,
                        COLUMN_CODE,
                        COLUMN_EMAIL,
                        COLUMN_PASSWORD,
                        COLUMN_FULLNAME,
                        COLUMN_ADDRESS,
                        COLUMN_BIRTH,
                        COLUMN_GENDER,
                        COLUMN_TIMESTAMP},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        boolean result = false;

        try {
            User data = new User();
            data.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));

            if (data.getEmail().equals(email)) {
                result = true;
                Toast.makeText(context, "email sudah ada", Toast.LENGTH_LONG).show();
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        cursor.close();
        return result;
//        return false;
    }

    public User loginCheck(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,
                        COLUMN_CODE,
                        COLUMN_EMAIL,
                        COLUMN_PASSWORD,
                        COLUMN_FULLNAME,
                        COLUMN_ADDRESS,
                        COLUMN_BIRTH,
                        COLUMN_GENDER,
                        COLUMN_TIMESTAMP},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);

//        values.put(User.COLUMN_CODE, getSaltString());
//        values.put(User.COLUMN_FULLNAME, name);
//        values.put(User.COLUMN_EMAIL, email);
//        values.put(User.COLUMN_PASSWORD, password);
//        values.put(User.COLUMN_ADDRESS, address);
//        values.put(User.COLUMN_BIRTH, birth);
//        values.put(User.COLUMN_GENDER, gender);

        User result = null;

        if (cursor != null) {
            cursor.moveToFirst();
        }

        try {
            User data = new User();
            data.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));

            if (data.getPassword().equals(password)) {
                //TODO save shared preference
                result = new User();
                result.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                result.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
                result.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                result.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                result.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
                result.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                result.setBirth(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH)));
                result.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                result.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
            } else {
                Toast.makeText(context, "password salah", Toast.LENGTH_LONG).show();
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        cursor.close();
        return result;
    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID,
                        COLUMN_CODE,
                        COLUMN_EMAIL,
                        COLUMN_PASSWORD,
                        COLUMN_FULLNAME,
                        COLUMN_ADDRESS,
                        COLUMN_BIRTH,
                        COLUMN_GENDER,
                        COLUMN_TIMESTAMP},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);

//        values.put(User.COLUMN_CODE, getSaltString());
//        values.put(User.COLUMN_FULLNAME, name);
//        values.put(User.COLUMN_EMAIL, email);
//        values.put(User.COLUMN_PASSWORD, password);
//        values.put(User.COLUMN_ADDRESS, address);
//        values.put(User.COLUMN_BIRTH, birth);
//        values.put(User.COLUMN_GENDER, gender);

        User result = null;

        if (cursor != null) {
            cursor.moveToFirst();
        }

        try {
            result = new User();
            result.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            result.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
            result.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            result.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            result.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
            result.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            result.setBirth(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTH)));
            result.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
            result.setTimestamp(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }

        cursor.close();
        return result;
    }
}