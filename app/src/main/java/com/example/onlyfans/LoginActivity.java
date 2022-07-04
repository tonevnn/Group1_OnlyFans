package com.example.onlyfans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{
    private EditText edtEmailLogin,edtPasswordLogin;
    private Button btnLogin, btnRegister;
    FirebaseAuth auth;
    ProgressDialog pd;
    private void BindingView(){
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegisterLogin);
        auth = FirebaseAuth.getInstance();
    }
    private void BindingAction(){
        btnLogin.setOnClickListener(this::btnLoginOnClick);
        btnRegister.setOnClickListener(this::btnRegisterOnLick);
    }

    private void btnRegisterOnLick(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    private void btnLoginOnClick(View view) {
        String email = edtEmailLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this, "Email/ Password fields are do not empty!", Toast.LENGTH_SHORT).show();
        }else{
            pd = new ProgressDialog(LoginActivity.this);
            pd.setMessage("Please wait...");
            pd.show();
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getCurrentUser().getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                pd.dismiss();
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {

                            }
                        });
                    }else{
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,"Wrong email or password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BindingView();
        BindingAction();
    }
}
