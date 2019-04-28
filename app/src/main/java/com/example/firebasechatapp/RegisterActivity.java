package com.example.firebasechatapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText registerUserName;
    private EditText registerPassword;
    private Button buttonRegister;
    private RelativeLayout mainLayout;

    private FirebaseAuth mAuth;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialize();
    }

    private void initialize(){
        mainLayout = findViewById(R.id.register_main_layout);

        registerUserName = findViewById(R.id.registerUserName);
        registerPassword = findViewById(R.id.registerPassword);
        buttonRegister = findViewById(R.id.buttonRegister);

        mAuth = FirebaseAuth.getInstance();

        // register buton tiklaninca
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = registerUserName.getText().toString();
                userPassword = registerPassword.getText().toString();
                if (userName.isEmpty() || userPassword.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(mainLayout, "Lütfen gerekli alanları doldurunuz!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    register();
                }
            }
        });
    }

    private void register() {
        mAuth.createUserWithEmailAndPassword(userName, userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}