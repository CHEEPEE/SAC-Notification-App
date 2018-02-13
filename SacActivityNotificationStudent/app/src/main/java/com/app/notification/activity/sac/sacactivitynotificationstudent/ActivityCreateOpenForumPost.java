package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityCreateOpenForumPost extends AppCompatActivity {
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
                if (validateForm(title,message)){
                   // publishPost(title.getText().toString(),message.getText().toString());
                }
            }
        });
      //  mAuth = FirebaseAuth.getInstance();
    }
    private boolean validateForm(EditText title,EditText content) {
        boolean valid = true;

        String Title = title.getText().toString();
        if (TextUtils.isEmpty(Title)) {
            title.setError("Required.");
            valid = false;
        } else {
            title.setError(null);
        }

        String Content = content.getText().toString();
        if (TextUtils.isEmpty(Content)) {
            content.setError("Required.");
            valid = false;
        } else {
            content.setError(null);
        }

        return valid;
    }

/*    private void publishPost(final String title ,final String content){

            String key = mDataBase.push().getKey();
            CreatePostMapModel createPostMapModel = new CreatePostMapModel("Admin",title,content,"Empty","null",key,mAuth.getCurrentUser().getUid(),UtilsTools.getDateToStrig());
            Map<String,Object> postValue = createPostMapModel.toMap();
            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put(key,postValue);
            mDataBase.child("OpenForumPost").updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(ActivityCreateOpenForumPost.this,"Error Posting", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

}
