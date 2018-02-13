package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcomeback extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView userEmail;
    Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_welcomeback);
        userEmail = (TextView) findViewById(R.id.emailAddress);
        userEmail.setText(mAuth.getCurrentUser().getEmail());
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i =  new Intent(Welcomeback.this,Announcements.class);
                startActivity(i);
                Welcomeback.this.finish();
            }
        });
    }
}
