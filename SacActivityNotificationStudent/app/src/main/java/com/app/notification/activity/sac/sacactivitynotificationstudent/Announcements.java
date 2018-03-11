package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class Announcements extends AppCompatActivity {
    SlidingRootNav slidingRootNav;
    Toolbar toolbar;
    TextView signOut,menuAdminPost,menuFreedomWall;
    FirebaseAuth  mAuth;
    String studentNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);
        mAuth = FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("studentNumber")!= null)
        {
           studentNumber = bundle.getString("studentNumber");
            Toast.makeText(Announcements.this,studentNumber,Toast.LENGTH_SHORT).show();
            Toast.makeText(Announcements.this,mAuth.getCurrentUser().getDisplayName(),Toast.LENGTH_SHORT).show();
        }



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
                Intent i =  new Intent(Announcements.this,LoginActivity.class);
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
}
