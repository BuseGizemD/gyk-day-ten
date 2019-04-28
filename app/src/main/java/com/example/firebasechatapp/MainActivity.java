package com.example.firebasechatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextUserPassword;
    private Button buttonLogin;
    private TextView txtRegister;
    private RelativeLayout mainLayout;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        mainLayout = findViewById(R.id.main_layout);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextUserPassword = findViewById(R.id.editTextUserPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        txtRegister = findViewById(R.id.txtRegister);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = editTextUserName.getText().toString();
                userPassword = editTextUserPassword.getText().toString();
                if (userName.isEmpty() || userPassword.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(mainLayout, "Lütfen gerekli alanları doldurunuz!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    login();
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        mAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(MainActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }
}