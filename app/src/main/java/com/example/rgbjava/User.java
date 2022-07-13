package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;
    private int startTime;
    private boolean gpsEnabled;
    private boolean cameraEnabled;
    private boolean recordingEnabled;

    public User(String name, String lastName, String phoneNumber, int startTime, boolean gpsEnabled, boolean cameraEnabled, boolean recordingEnabled){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.gpsEnabled = gpsEnabled;
        this.cameraEnabled = cameraEnabled;
        this.recordingEnabled = recordingEnabled;
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

    public boolean isGpsEnabled(){
        return gpsEnabled;
    }

    public boolean isCameraEnabled() {
        return cameraEnabled;
    }

    public boolean isRecordingEnabled() {
        return recordingEnabled;
    }
}
