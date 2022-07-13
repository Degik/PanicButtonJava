package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String numberTel;
    private String email;
    private boolean favorite;

    public Contact(String firstName, String lastName, String numberTel){
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberTel = numberTel;
    }

    public Contact(String firstName, String lastName, String numberTel, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberTel = numberTel;
        this.email = email;
        this.favorite = false;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getNumberTel(){
        return numberTel;
    }

    public String getEmail(){
        return email;
    }

    public boolean getFavorite(){
        return favorite;
    }

    @Override
    public String toString(){
        String s  = "Nome:  " + firstName + " Cognome: " + lastName + " Telefono: " + numberTel + "\n";
        return s;
    }
}
