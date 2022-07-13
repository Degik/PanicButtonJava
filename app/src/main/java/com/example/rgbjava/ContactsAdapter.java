package com.example.rgbjava;
/*
    Davide Bulotta
    Matricola: 596782
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resources;

    public ContactsAdapter(Context context, int resources, ArrayList<Contact> contacts){
        super(context, resources, contacts);
        this.context = context;
        this.resources = resources;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView tvFirstName;
        TextView tvLastName;
        TextView tvNumberPhone;

        String firstName = getItem(position).getFirstName();
        String lastName = getItem(position).getLastName();
        String numberPhone = getItem(position).getNumberTel();

        Contact contact = new Contact(firstName, lastName, numberPhone);
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resources, parent, false);

        tvFirstName = (TextView) convertView.findViewById(R.id.textFirstNameAdapter);
        tvLastName = (TextView) convertView.findViewById(R.id.textLastNameAdapter);
        tvNumberPhone = (TextView) convertView.findViewById(R.id.textNumberPhoneAdapter);

        tvFirstName.setText(contact.getFirstName());
        tvLastName.setText(contact.getLastName());
        tvNumberPhone.setText(contact.getNumberTel());

        return convertView;
    }
}
