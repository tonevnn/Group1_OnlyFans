package com.example.onlyfans;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TungAnh extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private Button btnRead;
    private Button btnSend, btnSignout;
    private EditText edtName;
    private TextView twRead;
    private void BindingView(){
        btnSignout = findViewById(R.id.btnSignout);
        btnSend = findViewById(R.id.btnSend);
        edtName = findViewById(R.id.edtName);
        btnRead = findViewById(R.id.btnRead);
        twRead = findViewById(R.id.twRead);
    }
    private void BindingAction(){
        btnSend.setOnClickListener(this::btnSendOnClick);
        btnRead.setOnClickListener(this::btnReadOnClick);
        btnSignout.setOnClickListener(this::btnSignoutClick);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null){
            twRead.setText(acct.getDisplayName() + ", " +acct.getEmail());
        }
    }

    private void btnSignoutClick(View view) {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(TungAnh.this, LoginGoogleActivity.class));
            }
        });
    }

    String s = "";
    private void btnReadOnClick(View view) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                s = "failed";
            }
        });
        twRead.setText(s);
    }

    private void btnSendOnClick(View view) {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference();
        ref.setValue(edtName.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tung_anh);
        BindingView();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        BindingAction();
    }
}