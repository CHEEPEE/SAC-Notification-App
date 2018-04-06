package com.app.notification.activity.sac.sacactivitiesnotificationmobileapplication;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Announcements extends AppCompatActivity {
    SlidingRootNav slidingRootNav;
    Toolbar toolbar;
    TextView signOut,menuAdminPost,menuFreedomWall,lblAddStudent,lblChangePass;
    FirebaseAuth  mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        mAuth = FirebaseAuth.getInstance();

        toolbar.setTitle("SAC-SR Announcements");
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withDragDistance(200)
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        signOut  = (TextView) findViewById(R.id.signOut);
        menuAdminPost = (TextView)findViewById(R.id.lblAdminPost);
        menuFreedomWall = (TextView) findViewById(R.id.lblOpenForum);
        lblChangePass = (TextView) findViewById(R.id.lblResetPassword);
        lblChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Announcements.this);
                dialog.setCancelable(true);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.change_password_dialog);
                TextView submit = (TextView) dialog.findViewById(R.id.lblSubmit);
                final TextInputEditText password,newPassword,confirmPassword;
                password = (TextInputEditText) dialog.findViewById(R.id.inputPassword);
                newPassword = (TextInputEditText) dialog.findViewById(R.id.inputNewPass);
                confirmPassword = (TextInputEditText) dialog.findViewById(R.id.inputconfirmpass);
                dialog.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validatePass(newPassword.getText().toString(),confirmPassword.getText().toString())){
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(), password.getText().toString());

                            FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseAuth.getInstance().getCurrentUser().updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d("Ken", "Password updated");
                                                        } else {
                                                            Toast.makeText(Announcements.this,"Update Password Failed",Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Log.d("password", "Error auth failed");
                                            }
                                        }
                                    });
                        }
                    }
                });

            }
        });
        lblAddStudent = (TextView) findViewById(R.id.lblAddStudent);
        lblAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Announcements.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.register_dialog);
                dialog.show();
                final TextInputEditText studentNumber = (TextInputEditText) dialog.findViewById(R.id.inputStudentNumber);
                final TextInputEditText email = (TextInputEditText) dialog.findViewById(R.id.inputEmail);
                TextView btnDone = (TextView) dialog.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (validateForm(studentNumber,email)){
                           createUser(email.getText().toString(),studentNumber.getText().toString());
                           dialog.dismiss();
                       }
                    }
                });
            }
        });
        menuAdminPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new AdminPostFragment());
                slidingRootNav.closeMenu(true);
                toolbar.setTitle("SAC-SR Announcements");
            }
        });
        menuFreedomWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new OpenForumPostFragment());
                slidingRootNav.closeMenu(true);
                toolbar.setTitle("Open Forum");
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i =  new Intent(Announcements.this,MainActivity.class);
                startActivity(i);
                Announcements.this.finish();
            }
        });
        loadFragment(new AdminPostFragment());
    }
    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayOut,fragment);
        fragmentTransaction.commit(); // save the changes
    }
    private void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          FirebaseDatabase.getInstance().getReference().child("pub_users");
                            UserManagerMap userManagerMap = new UserManagerMap(email,password,email);
                            Map<String,Object> userManagerVal = userManagerMap.toMap();
                            Map<String,Object> childUpdate = new HashMap<>();
                            childUpdate.put(password,userManagerVal);
                            FirebaseDatabase.getInstance().getReference().child("pub_users").updateChildren(childUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Announcements.this, "REgistering user Success",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Firebase User Write: "+e);
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Announcements.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    public static boolean validateForm(EditText transactionName, EditText transactionCost) {
        boolean valid = true;

        String name = transactionName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            transactionName.setError("Required.");
            valid = false;
        } else {
            transactionName.setError(null);
        }

        String address = transactionCost.getText().toString();
        if (TextUtils.isEmpty(address)) {
            transactionCost.setError("Required.");
            valid = false;
        } else {
            transactionCost.setError(null);
        }

        return valid;
    }

    private void changePassword(){

    }
    private boolean validatePass(String newpass,String confirmPass){
        boolean value = true;
        if (!newpass.equals(confirmPass)){
           value = false;
        }
        return value;
    }

}
