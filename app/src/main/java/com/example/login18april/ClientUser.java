package com.example.login18april;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ClientUser {
    private static final String TAG = "Tracking";

    private String Username;
    private String Password;
    private int age;
    private String bookingStatus;
    private int ClientUserID;
    private boolean hasBooking;
    private String gender;


    public ClientUser(String username, String password) {
        Username = username;
        Password = password;
    }

    public ClientUser(String username, String password, int age) {
        this(username,password,age,"Nothing",false,"Null");
    }

    public ClientUser(String username, String password, int age, String bookingStatus, boolean hasBooking, String gender) {
        Username = username;
        Password = password;
        this.age = age;
        this.bookingStatus = bookingStatus;
        this.hasBooking = hasBooking;
        this.gender = gender;
        Random random = new Random();
        this.ClientUserID = random.nextInt(1000000)+random.nextInt(1000000);

    }

    public static String getTAG() {
        return TAG;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public boolean isHasBooking() {
        return hasBooking;
    }

    public void setHasBooking(boolean hasBooking) {
        this.hasBooking = hasBooking;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getClientUserID() {
        return ClientUserID;
    }

    public void setClientUserID(int clientUserID) {
        ClientUserID = clientUserID;
    }
}
