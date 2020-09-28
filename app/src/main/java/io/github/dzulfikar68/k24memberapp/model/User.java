package io.github.dzulfikar68.k24memberapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public static final String TABLE_NAME = "user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULLNAME = "fullname";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BIRTH = "birth";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CODE + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_FULLNAME + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_BIRTH + " TEXT,"
                    + COLUMN_GENDER + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private int id;
    private String email, code, password, fullname, address, birth, gender, timestamp;

    public User() {
    }

    public User(int id,
                String code,
                String email,
                String password,
                String fullname,
                String address,
                String birth,
                String gender,
                String timestamp) {
        this.id = id;
        this.code = code;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.birth = birth;
        this.gender = gender;
        this.timestamp = timestamp;
    }

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        code = in.readString();
        password = in.readString();
        fullname = in.readString();
        address = in.readString();
        birth = in.readString();
        gender = in.readString();
        timestamp = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(code);
        dest.writeString(password);
        dest.writeString(fullname);
        dest.writeString(address);
        dest.writeString(birth);
        dest.writeString(gender);
        dest.writeString(timestamp);
    }
}
