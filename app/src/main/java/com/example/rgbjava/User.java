package com.example.rgbjava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;
    private int startTime;
    private boolean gpsEnabled;

    public User(String name, String lastName, String phoneNumber, int startTime, boolean gpsEnabled){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.gpsEnabled = gpsEnabled;
    }

    public String getName(){
        return name;
    }

    public String getLastname(){
        return lastName;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public int getStartTime() {
        return startTime;
    }

    public boolean getGpsEnabled(){
        return gpsEnabled;
    }
}
