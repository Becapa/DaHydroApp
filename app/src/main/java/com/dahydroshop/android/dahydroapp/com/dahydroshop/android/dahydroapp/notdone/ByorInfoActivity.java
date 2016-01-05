package com.dahydroshop.android.dahydroapp.com.dahydroshop.android.dahydroapp.notdone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dahydroshop.android.dahydroapp.DatabaseHelper;
import com.dahydroshop.android.dahydroapp.R;

/**
 * Created by Mike on 10/28/2015.
 */
public class ByorInfoActivity extends AppCompatActivity {
    private static final String ROOMTABLE = "room";

    private Button mSubmitButton;
    private EditText mHeight;
    private EditText mWidth;
    private EditText mLength;
    private EditText mEmail;
    private EditText mLightRows;
    private EditText mLightCols;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byor_info);
        mDatabase = new DatabaseHelper(getApplicationContext()).getWritableDatabase();


        mEmail = (EditText)findViewById(R.id.user_email);
        mHeight = (EditText)findViewById(R.id.height);
        mWidth = (EditText)findViewById(R.id.width);
        mLength = (EditText)findViewById(R.id.length);
        mLightRows = (EditText)findViewById(R.id.row_lights_edit);
        mLightCols = (EditText)findViewById(R.id.col_lights_edit);

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmpheight = mHeight.getText().toString();
                int height = Integer.parseInt(tmpheight);
                String tmpwidth = mWidth.getText().toString();
                int width = Integer.parseInt(tmpwidth);
                String tmplength = mLength.getText().toString();
                int length = Integer.parseInt(tmplength);
                String email = mEmail.getText().toString();
                String tmprows = mLightRows.getText().toString();
                int rows = Integer.parseInt(tmprows);
                String tmpcols = mLightCols.getText().toString();
                int cols = Integer.parseInt(tmpcols);
                insert(email, height, width, length, rows, cols);

                Intent i = ByorGUIActivity.newIntent(ByorInfoActivity.this, height, width, length, rows, cols);
                startActivity(i);

            }
        });
    }

    public void insert(String email, int height, int width, int length, int rows, int cols){

        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("height", height);
        values.put("width", width);
        values.put("length", length);
        values.put("lightRows", rows);
        values.put("lightCols", cols);
        mDatabase.insertWithOnConflict(ROOMTABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
}
