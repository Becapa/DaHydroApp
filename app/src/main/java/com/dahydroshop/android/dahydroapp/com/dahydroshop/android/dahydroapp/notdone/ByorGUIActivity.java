package com.dahydroshop.android.dahydroapp.com.dahydroshop.android.dahydroapp.notdone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.dahydroshop.android.dahydroapp.R;

/**
 * Created by Mike on 11/24/2015.
 */
public class ByorGUIActivity extends FragmentActivity {

    private final static String GUI = "com.dahydroshop.android.dahydroapp.gui";
    private final static String HEIGHT = "com.dahydroshop.android.dahydroapp.height";
    private final static String WIDTH = "com.dahydroshop.android.dahydroapp.width";
    private final static String LENGTH = "com.dahydroshop.android.dahydroapp.lenght";
    private final static String ROWS = "com.dahydroshop.android.dahydroapp.rows";
    private final static String COLS = "com.dahydroshop.android.dahydroapp.cols";

    protected int mHeight;
    protected int mWidth;
    protected int mLength;
    protected int mRows;
    protected int mCols;

    public static Intent newIntent(Context packageContext, int height, int width, int length, int rows, int cols){
        Intent i = new Intent(packageContext, ByorGUIActivity.class);
        i.putExtra(HEIGHT, height);
        i.putExtra(WIDTH, width);
        i.putExtra(LENGTH, length);
        i.putExtra(ROWS, rows);
        i.putExtra(COLS, cols);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byor_gui);

        mHeight = getIntent().getIntExtra(HEIGHT, 0);
        mWidth = getIntent().getIntExtra(WIDTH, 0);
        mLength = getIntent().getIntExtra(LENGTH, 0);
        mRows = getIntent().getIntExtra(ROWS, 0);
        mCols = getIntent().getIntExtra(COLS, 0);

        Bundle args = new Bundle();
        args.putInt(HEIGHT, mHeight);
        args.putInt(WIDTH, mWidth);
        args.putInt(LENGTH, mLength);
        args.putInt(COLS, mCols);
        args.putInt(ROWS, mRows);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.byor_fragment_container);

        if (fragment == null){
            fragment = new GUIFragment();
            fragment.setArguments(args);
            fm.beginTransaction()
                    .add(R.id.byor_fragment_container, fragment, GUI)
                    .commit();
        }
    }
}
