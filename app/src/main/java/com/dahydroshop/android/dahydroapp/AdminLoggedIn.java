package com.dahydroshop.android.dahydroapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Mike on 11/21/2015.
 */
public class AdminLoggedIn extends AppCompatActivity {
    private Button mMessagesButton;
    //private Button mPopularButton;
    private Button mNotificationButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_logged_in);

        mMessagesButton = (Button)findViewById(R.id.admin_messages_button);
        //mPopularButton = (Button)findViewById(R.id.admin_popular_button);
        mNotificationButton = (Button)findViewById(R.id.admin_notification_button);

        mMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLoggedIn.this, ViewMessagesActivity.class);
                startActivity(i);
            }
        });
        /*mPopularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLoggedIn.this, ViewPopularActivity.class);
                startActivity(i);
            }
        });*/
        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminLoggedIn.this, SendNotificationActivity.class);
                startActivity(i);
            }
        });

    }
}
