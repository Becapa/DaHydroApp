package com.dahydroshop.android.dahydroapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

/**
 * Created by Mike on 10/28/2015.
 */
public class AdminActivity extends AppCompatActivity {

    private static final String OWNERTABLE = "owner";

    private EditText mUserEdit;
    private EditText mPassEdit;
    private Button mSubmit;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mDatabase = new DatabaseHelper(this).getWritableDatabase();

        mUserEdit = (EditText)findViewById(R.id.username_edit);
        mPassEdit = (EditText)findViewById(R.id.password_edit);
        mSubmit = (Button)findViewById(R.id.admin_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = mUserEdit.getText().toString();
                String password = md5(mPassEdit.getText().toString());

                new AsyncTask<String, Long, Boolean>(){
                    @Override
                    protected Boolean doInBackground(String... params) {
                        String param0 = params[0];
                        String param1 = params[1];
                        return checkAdmin(param0, param1);
                    }

                    @Override
                    protected void onPostExecute(Boolean aBoolean) {
                        if(aBoolean){
                            Intent intent = new Intent(AdminActivity.this, AdminLoggedIn.class);
                            startActivity(intent);
                        }
                        else{
                            mSubmit.setEnabled(false);
                            mSubmit.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mSubmit.setEnabled(true);
                                }
                            }, 5000);
                            Toast.makeText(AdminActivity.this, "Incorrect admin username or password. Try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute(username, password);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private boolean checkAdmin(String username, String password){
        String sql = "SELECT passwd from " + OWNERTABLE + " WHERE username = ?";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{username});
        try {
            if(cursor.moveToFirst()) {
                if (cursor.getString(0).equals(password)) {
                    return true;
                }
            }
            return false;

        } finally {
            cursor.close();
        }
    }
    private static String md5(final String passwd) {
        // http://stackoverflow.com/questions/3934331/android-how-to-encrypt-a-string
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            md.update(passwd.getBytes());

            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(String.format("%02X", aByte));
            }

            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }
}
