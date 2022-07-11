package com.example.rgbjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BackupFile {
    private final static String CONTACTS = "contacts";
    private final static String SETTINGS  = "settings";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Context context;

    public BackupFile(Context context){
        this.context = context;
    }

    public void makeBackupContactsList(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(MainActivity.contacts);

        sharedPreferences = context.getSharedPreferences(CONTACTS, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(CONTACTS, jsonString);
        editor.apply();
    }

    public void makeBackupSettings(String firstName, String lastName, String numberPhone, int startTime, boolean gpsEnabled){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("numberPhone", numberPhone);
        editor.putBoolean("firstStart", false);
        editor.putInt("startTime", startTime);
        editor.putBoolean("gpsEnabled", gpsEnabled);
        editor.commit();
    }

    public String getFirstName(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getString("firstName", "DEFAULT");
    }

    public String getLastName(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getString("lastName", "DEFAULT");
    }

    public String getNumberPhone(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getString("numberPhone", "DEFAULT");
    }

    public boolean getFirstStart(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getBoolean("firstStart", true);
    }

    public int getStartTime(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getInt("startTime", 2000);
    }

    public boolean getGpsEnabled(){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        return settings.getBoolean("gpsEnabled", false);
    }

    public ArrayList<Contact> getContactList(){
        sharedPreferences = context.getSharedPreferences(CONTACTS, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(CONTACTS, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
        ArrayList<Contact> list = gson.fromJson(jsonString, type);
        return list;
    }


}
