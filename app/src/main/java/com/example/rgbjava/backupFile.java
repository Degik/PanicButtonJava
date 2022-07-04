package com.example.rgbjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class backupFile {
    private final static String nameStorageFile = "list_contacts";
    private final static String LIST_KEY = "list_key";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static List<Contact> contactList;
    private static Context context;

    public backupFile(Context context, List<Contact> contactList){
        this.context = context;
        this.contactList = contactList;
    }

    public static void makeBackup(){
        Gson gson = new Gson();
        String jsonString = gson.toJson(contactList);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(LIST_KEY, jsonString);
        editor.apply();
    }

    public static List<Contact> getContactList(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString =sharedPreferences.getString(LIST_KEY, "");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Contact>>(){}.getType();
        List<Contact> list = gson.fromJson(jsonString, type);
        return list;
    }


}
