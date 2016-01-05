package com.dahydroshop.android.dahydroapp.com.dahydroshop.android.dahydroapp.notdone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dahydroshop.android.dahydroapp.R;

/**
 * Created by Mike on 11/24/2015.
 */
public class GUIFragment extends Fragment {

    private final static String HEIGHT = "com.dahydroshop.android.dahydroapp.height";
    private final static String WIDTH = "com.dahydroshop.android.dahydroapp.width";
    private final static String LENGTH = "com.dahydroshop.android.dahydroapp.lenght";
    private final static String ROWS = "com.dahydroshop.android.dahydroapp.rows";
    private final static String COLS = "com.dahydroshop.android.dahydroapp.cols";

    private int mHeight;
    private int mWidth;
    private int mLength;
    private int mRows;
    private int mCols;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gui, container, false);

        mHeight = this.getArguments().getInt(HEIGHT);
        mWidth = this.getArguments().getInt(WIDTH);
        mLength = this.getArguments().getInt(LENGTH);
        mRows = this.getArguments().getInt(ROWS);
        mCols = this.getArguments().getInt(COLS);

        RoomView topRoomView = (RoomView)v.findViewById(R.id.top_view_image);
        topRoomView.setValues(mRows, mCols, mLength, mWidth, "top");
        LinearLayout.LayoutParams topParams = (LinearLayout.LayoutParams)topRoomView.getLayoutParams();
        topParams.height = mLength*60;
        topParams.width = mWidth*60;
        topRoomView.setLayoutParams(topParams);

        RoomView frontRoomView = (RoomView)v.findViewById(R.id.front_view_image);
        frontRoomView.setValues(1, mCols, mHeight, mWidth, "front");
        LinearLayout.LayoutParams frontParams = (LinearLayout.LayoutParams)frontRoomView.getLayoutParams();
        frontParams.height = mHeight*60;
        frontParams.width = mWidth*60;
        frontRoomView.setLayoutParams(frontParams);

        RoomView sideRoomView = (RoomView)v.findViewById(R.id.side_view_image);
        sideRoomView.setValues(1, mRows, mHeight, mLength, "side");
        LinearLayout.LayoutParams sideParams = (LinearLayout.LayoutParams)sideRoomView.getLayoutParams();
        sideParams.height = mHeight*60;
        sideParams.width = mLength*60;
        sideRoomView.setLayoutParams(sideParams);


        return v;
    }
}
