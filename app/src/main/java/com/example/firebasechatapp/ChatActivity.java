package com.example.firebasechatapp;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.firebasechatapp.Adapters.CustomAdapter;
import com.example.firebasechatapp.Models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private ArrayList<Message> chatLists = new ArrayList<>();
    private CustomAdapter customAdapter;
    private String subject;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private EditText inputChat;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initialize();
    }

    private void initialize(){
        mainLayout = findViewById(R.id.chat_main_layout);
        listView = findViewById(R.id.chatListView);
        inputChat = findViewById(R.id.inputChat);
        floatingActionButton = findViewById(R.id.fab);

        db = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        customAdapter = new CustomAdapter(getApplicationContext(),chatLists,fUser);
        listView.setAdapter(customAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            subject = bundle.getString("subject");
            dbRef = db.getReference("ChatSubjects/"+subject+"/mesaj");
            setTitle(subject);
        }

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Message message = ds.getValue(Message.class);
                    chatLists.add(message);
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputChat.getText().length()>=6){
                    long msTime = System.currentTimeMillis();
                    Date curDateTime = new Date(msTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                    String dateTime = formatter.format(curDateTime);
                    Message message = new Message(inputChat.getText().toString(),fUser.getEmail(),dateTime);
                    dbRef.push().setValue(message);
                    inputChat.setText("");
                }else{
                    Snackbar snackbar = Snackbar.make(mainLayout, "Gönderilecek mesaj uzunluğu en az 6 karakter olmalıdır!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }
}