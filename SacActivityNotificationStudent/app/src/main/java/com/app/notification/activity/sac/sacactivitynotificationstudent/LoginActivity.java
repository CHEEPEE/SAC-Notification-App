package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import devliving.online.mvbarcodereader.MVBarcodeScanner;

public class LoginActivity extends AppCompatActivity {
    final int REQ_CODE = 12;
    Barcode mBarcode;
    FirebaseAuth mAuth;
    String email;
    TextView lbl_msg,lbl_tryAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        lbl_msg = (TextView) findViewById(R.id.lbl_msg);
        lbl_tryAgain = (TextView) findViewById(R.id.lbl_tryAgain);
        lbl_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                getCredemtials(result.getContents().toString());
            }
        }


    }

    private void getCredemtials(final String studentNumber) {

        FirebaseDatabase.getInstance().getReference().child("pub_users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UserProfileLogin userProfileLogin = dataSnapshot1.getValue(UserProfileLogin.class);
                    System.out.println(userProfileLogin.email + userProfileLogin.studentNumber);
                    if (userProfileLogin.studentNumber.equals(studentNumber)) {
                       firebaseLonIn(userProfileLogin.email,studentNumber);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void firebaseLonIn(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();


                            if (email.equals("renel@gmail.com ")){

                            }else {
                                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("studentNumber").setValue(password);
                                FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("userId").setValue(user.getUid());
                                Intent i = new Intent(LoginActivity.this, Welcomeback.class);
                                startActivity(i);
                                System.out.println("success");
                                finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            lbl_tryAgain.setVisibility(View.VISIBLE);
                            lbl_msg.setText("Authentication Failed");
                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Intent i = new Intent(LoginActivity.this,Welcomeback.class);
            startActivity(i);
           LoginActivity.this.finish();
        }else{
            IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    }
}
