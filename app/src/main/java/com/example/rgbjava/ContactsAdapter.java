package com.example.rgbjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    public ContactsAdapter(Context context, ArrayList<Contact> contacts){
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Contact contact = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_contacts_list, parent, false);
        }
        TextView tvFirstName = (TextView) convertView.findViewById(R.id.tvFirstName);
        TextView tvLastName = (TextView) convertView.findViewById(R.id.tvLastName);
        TextView tvNubmerPhone = (TextView) convertView.findViewById(R.id.tvNumberPhone);

        tvFirstName.setText(contact.getFirstName());
        tvLastName.setText(contact.getLastName());
        tvNubmerPhone.setText(contact.getNumberTel());

        return convertView;
    }
}
