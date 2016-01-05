package com.dahydroshop.android.dahydroapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Mike on 12/8/2015.
 */
public class NotificationDialog extends DialogFragment {

    private String mNotification;
    private TextView mTextView;

    public static NotificationDialog newInstance(String dialogText) {

        NotificationDialog nd = new NotificationDialog();

        Bundle b = new Bundle();
        b.putSerializable("dialogText", dialogText);
        nd.setArguments(b);

        return nd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);

        mNotification = (String)getArguments().getSerializable("dialogText");

        mTextView = (TextView)v.findViewById(R.id.dialog_text_view);
        mTextView.setText(mNotification);

        AlertDialog a = new AlertDialog.Builder(getActivity())
                .setTitle("Da Hydro App")
                .setView(v)
                .setPositiveButton("Ok", null)
                .create();
        return a;
    }
}
