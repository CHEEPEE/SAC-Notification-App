package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Welcomeback extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView userEmail;
    TextView btnContinue;
    DatabaseReference mDatabase;
    TextInputEditText inpt_username;
    String StudentNumber;
    TextInputLayout textInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        inpt_username = (TextInputEditText) findViewById(R.id.inpUsername);
        userEmail = (TextView) findViewById(R.id.emailAddress);
        userEmail.setText(mAuth.getCurrentUser().getEmail());
        btnContinue = (TextView) findViewById(R.id.lblContinue);
        textInputLayout = (TextInputLayout) findViewById(R.id.textusername);
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("studentNumber").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

             StudentNumber = dataSnapshot.getValue().toString();
             cont();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void cont(){
        if (mAuth.getCurrentUser().getDisplayName()==null){
            inpt_username.setVisibility(View.VISIBLE);
            textInputLayout.setVisibility(View.VISIBLE);
        }else {
            userEmail.setText(mAuth.getCurrentUser().getDisplayName());
        }

        mDatabase.child("pub_users").child(StudentNumber).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    inpt_username.setText(dataSnapshot.getValue().toString());

                }catch (NullPointerException e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mAuth.getCurrentUser().getDisplayName()==null){
                   if (!inpt_username.getText().toString().trim().equals("")){
                       FirebaseUser user = mAuth.getCurrentUser();
                       UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                               .setDisplayName(inpt_username.getText().toString()).build();
                       user.updateProfile(profileUpdates);
                       mDatabase.child("pub_users").child(StudentNumber).child("username").setValue(inpt_username.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Intent i = new Intent(Welcomeback.this,Announcements.class);
                               i.putExtra("studentNumber",StudentNumber);
                               startActivity(i);
                               Welcomeback.this.finish();
                           }
                       });
                   }else {
                       Toast.makeText(Welcomeback.this,"Please Set Username",Toast.LENGTH_SHORT).show();
                   }
               }else {
                   Intent i = new Intent(Welcomeback.this,Announcements.class);
                   i.putExtra("studentNumber",StudentNumber);
                   startActivity(i);
                   Welcomeback.this.finish();
               }

            }
        });
    }
}
