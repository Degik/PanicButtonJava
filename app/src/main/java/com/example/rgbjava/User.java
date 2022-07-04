package com.example.rgbjava;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class User {
    private String name;
    private String lastName;
    // Lista dei contatti
    private ArrayList<Contact> contacts;
    private String phoneNumber;


    public User(String name, String lastName, String phoneNumber){
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        contacts = new ArrayList<>();
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

    public boolean alreadyContact(String numberTel){
        for(Contact c : contacts){
            if(c.getNumberTel().equals(numberTel)){
                return true;
            }
        }
        return false;
    }

    public boolean addContact(Contact c){ // Aggiungo contatto alla lista
        return contacts.add(c);
    }

    public Contact getContact(int numberTel) {
        for(Contact c : contacts){
            if(c.getNumberTel().equals(numberTel)){
                return c;
            }
        }
        return null;
    }// Ricavo l'oggetto contatto

    public ArrayList<Contact> getContacts(){
        return contacts;
    }

    public boolean deleteContact(Contact c){
        return contacts.remove(c);
    }
}
