
package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WriteForumPost extends AppCompatActivity {
    TextInputEditText title,message;
    TextView btnDone;
    DatabaseReference mDatabase;
    String userName;
    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_write_forum_post);
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        title = (TextInputEditText) findViewById(R.id.inputTitle);
        message = (TextInputEditText) findViewById(R.id.inputMessage);
        btnDone = (TextView) findViewById(R.id.btnDone);
        final ArrayList<TextInputEditText> textInputEditTexts = new ArrayList<>();
        textInputEditTexts.add(title);
        textInputEditTexts.add(message);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm(textInputEditTexts)){
                   publishPost(title.getText().toString(),message.getText().toString());
                }
            }
        });





    }

    private boolean validateForm(ArrayList<TextInputEditText> textInputEditTexts){
        boolean valid =true;

        for (int i = 0;i<textInputEditTexts.size();i++){
            if (TextUtils.isEmpty(textInputEditTexts.get(i).getText().toString())){
                textInputEditTexts.get(i).setError("Required");
                valid = false;
            }
        }

        return valid;

    }

    private void printThis(String tobePrint){
        System.out.println(tobePrint);
    }

    private void publishPost(final String title ,final String content){

        String key = mdatabase.push().getKey();
        CreatePostMapModel createPostMapModel = new CreatePostMapModel(mAuth.getCurrentUser().getDisplayName(),title,content,"Empty","null",key,mAuth.getCurrentUser().getUid(),UtilsTools.getDateToStrig());
        Map<String,Object> postValue = createPostMapModel.toMap();
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put(key,postValue);
        mdatabase.child("OpenForumPost").updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(WriteForumPost.this,"Error Posting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
