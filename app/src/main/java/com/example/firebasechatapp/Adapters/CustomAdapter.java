package com.example.firebasechatapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.firebasechatapp.Models.Message;
import com.example.firebasechatapp.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Message> {
    private FirebaseUser firebaseUser;

    public CustomAdapter(Context context, ArrayList<Message> chatList, FirebaseUser firebaseUser) {
        super(context, 0, chatList);
        this.firebaseUser = firebaseUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if (firebaseUser.getEmail().equalsIgnoreCase(message.getGonderici())) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_item_layout, parent, false);

            TextView txtUser = convertView.findViewById(R.id.txtUserRight);
            TextView txtMessage = convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = convertView.findViewById(R.id.txtTimeRight);

            txtUser.setText(message.getGonderici());
            txtMessage.setText(message.getMesajText());
            txtTime.setText(message.getZaman());

        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_item_layout, parent, false);

            TextView txtUser = convertView.findViewById(R.id.txtUserLeft);
            TextView txtMessage = convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = convertView.findViewById(R.id.txtTimeLeft);

            txtUser.setText(message.getGonderici());
            txtMessage.setText(message.getMesajText());
            txtTime.setText(message.getZaman());

        }
        return convertView;
    }
}