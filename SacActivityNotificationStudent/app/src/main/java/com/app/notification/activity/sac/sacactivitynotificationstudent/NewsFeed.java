package com.app.notification.activity.sac.sacactivitynotificationstudent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsFeed extends AppCompatActivity {
    TextView cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        cont = (TextView) findViewById(R.id.lbl_continue);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsFeed.this,Announcements.class);
                startActivity(i);
                finish();
            }
        });
    }
}
