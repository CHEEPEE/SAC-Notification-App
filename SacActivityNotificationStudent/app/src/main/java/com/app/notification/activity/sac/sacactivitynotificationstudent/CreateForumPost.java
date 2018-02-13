package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateForumPost extends AppCompatActivity {
    EditText title,message;
    DatabaseReference mDataBase;
    FloatingActionButton createPostDone;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_forum_post);
        title = (EditText) findViewById(R.id.inputTitle);
        message = (EditText)findViewById(R.id.inputMessage);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        createPostDone = (FloatingActionButton) findViewById(R.id.createPostDone);
        createPostDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

}
