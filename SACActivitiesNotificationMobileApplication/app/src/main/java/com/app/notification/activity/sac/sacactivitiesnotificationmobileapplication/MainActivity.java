package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText inputUsername,inputPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        inputUsername = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(inputUsername.getText().toString(),inputPassword.getText().toString());
            }
        });

    }

    private void signIn(String email, String password) {

        if (validateForm()){
           // progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(MainActivity.this, Announcements.class);
                                startActivity(i);
                                System.out.println("success");

                            } else {
                                // If sign in fails, display a message to the user.
                              //  progressBar.setVisibility(View.INVISIBLE);
                                System.out.println("failed");
                                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println(e);
                }
            });
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            Intent i = new Intent(MainActivity.this,Welcomeback.class);
            startActivity(i);
            MainActivity.this.finish();
        }
    }
    private boolean validateForm() {
        boolean valid = true;

        String emailString = inputUsername.getText().toString();
        if (TextUtils.isEmpty(emailString)) {
            inputUsername.setError("Required.");
            valid = false;
        } else {
            inputUsername.setError(null);
        }

        String passwordString = inputPassword.getText().toString();
        if (TextUtils.isEmpty(passwordString)) {
            inputPassword.setError("Required.");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }
}
