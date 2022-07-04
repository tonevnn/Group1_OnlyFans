package com.example.onlyfans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button btnRegister, btnLogin;
    private void BindingView(){
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void BindingAction(){
        btnRegister.setOnClickListener(this::btnRegisterOnClick);
        btnLogin.setOnClickListener(this::btnLoginOnClick);
    }

    private void btnLoginOnClick(View view) {
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
    }

    private void btnRegisterOnClick(View view) {
        startActivity(new Intent(StartActivity.this, RegisterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        BindingView();
        BindingAction();
    }
}