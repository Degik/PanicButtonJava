package com.example.rgbjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackupFile {
    private final static String LIST_CONTACTS = "contactsList";
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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(LIST_CONTACTS, jsonString);
        editor.apply();
    }

    public void makeBackupSettings(String firstName, String lastName, String numberPhone){
        SharedPreferences settings = context.getSharedPreferences(SETTINGS, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("numberPhone", numberPhone);
        editor.commit();
    }

    public String getFirstName(){
        SharedPreferences
    }

    public ArrayList<Contact> getContactList(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString =sharedPreferences.getString(LIST_CONTACTS, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
        ArrayList<Contact> list = gson.fromJson(jsonString, type);
        return list;
    }


}
