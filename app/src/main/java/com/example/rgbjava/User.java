package com.example.rgbjava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String name;
    private String lastName;
    private String phoneNumber;


    public User(String name, String lastName, String phoneNumber){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
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
}
