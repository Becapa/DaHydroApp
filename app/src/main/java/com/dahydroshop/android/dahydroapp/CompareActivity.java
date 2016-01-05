package com.dahydroshop.android.dahydroapp;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 10/28/2015.
 */
public class CompareActivity extends AppCompatActivity {

    private static final String ITEMTABLE = "item";
    private static final String ALLITEMS = "All Items";
    private static final String ONHANDITEMS = "Only items that are currently on hand";

    private Spinner mOptionSpinner;
    private Spinner mTypeSpinner;
    private Spinner mItem1Spinner;
    private Spinner mItem2Spinner;
    private String mTypeSelected;
    private String mItem1Selected;
    private String mItem2Selected;
    private String itemSql;
    private String mSqlArgs;
    private TextView mItem1Text;
    private TextView mItem2Text;
    private ArrayAdapter<String> mOptionAdapter;
    private ArrayAdapter<String> mTypeAdapter;
    private ArrayAdapter<String> mItem1Adapter;
    private ArrayAdapter<String> mItem2Adapter;
    private SQLiteDatabase mDatabase;

    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        mDatabase = new DatabaseHelper(this).getWritableDatabase();

        mItem1Text = (TextView)findViewById(R.id.item1_text);
        mItem2Text = (TextView)findViewById(R.id.item2_text);

        mOptionSpinner = (Spinner)findViewById(R.id.onhand_spinner);
        mOptionAdapter = loadOptionSpinner();
        mOptionSpinner.setAdapter(mOptionAdapter);
        if(savedInstanceState != null) {
            mOptionSpinner.setSelection(savedInstanceState.getInt("optionSpinner", 0));
        }
        mOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String optionSelected = parent.getItemAtPosition(position).toString();
                final String deptSql = "SELECT department from " + ITEMTABLE + " GROUP BY department";

                mTypeAdapter = loadSpinnerData(deptSql);
                /*new AsyncTask<String, Long, ArrayAdapter<String>>(){
                    @Override
                    protected ArrayAdapter<String> doInBackground(String... params) {
                        return loadSpinnerData(params[0]);
                    }

                    @Override
                    protected void onPostExecute(ArrayAdapter<String> stringArrayAdapter) {
                        mTypeAdapter = stringArrayAdapter;
                    }
                }.execute(deptSql);
                */

                mTypeSpinner.setAdapter(mTypeAdapter);
                if(savedInstanceState != null) {
                    mTypeSpinner.setSelection(savedInstanceState.getInt("typeSpinner", 0));
                }

                if(optionSelected.equals(ALLITEMS)){
                    mSqlArgs = "' GROUP BY itemName";
                }
                else{
                    mSqlArgs = "' AND quantity > 0 GROUP BY itemName";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CompareActivity.this, itemSql, Toast.LENGTH_LONG).show();
            }
        });

        mTypeSpinner = (Spinner)findViewById(R.id.type_spinner);

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTypeSelected = parent.getItemAtPosition(position).toString();
                itemSql = "SELECT itemName from " + ITEMTABLE + " WHERE department='" + mTypeSelected + mSqlArgs;
                //Toast.makeText(CompareActivity.this, itemSql, Toast.LENGTH_LONG).show();

                mItem1Adapter = loadSpinnerData(itemSql);
                mItem2Adapter = loadSpinnerData(itemSql);
                /*new AsyncTask<String, Long, ArrayAdapter<String>>(){
                    @Override
                    protected ArrayAdapter<String> doInBackground(String... params) {
                        return loadSpinnerData(params[0]);
                    }

                    @Override
                    protected void onPostExecute(ArrayAdapter<String> stringArrayAdapter) {
                        mItem1Adapter = stringArrayAdapter;
                        mItem2Adapter = stringArrayAdapter;
                    }
                }.execute(itemSql);
                */

                mItem1Spinner.setAdapter(mItem1Adapter);
                mItem2Spinner.setAdapter(mItem2Adapter);
                if(savedInstanceState != null) {
                    mItem1Spinner.setSelection(savedInstanceState.getInt("item1Spinner", 0));
                    mItem2Spinner.setSelection(savedInstanceState.getInt("item2Spinner", 0));
                }
                mItem1Spinner.setVisibility(View.VISIBLE);
                mItem2Spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mItem1Spinner = (Spinner)findViewById(R.id.item1_spinner);

        mItem1Spinner.setVisibility(View.GONE);
        mItem1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem1Selected = parent.getItemAtPosition(position).toString();

                mItem1Text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mItem1Text.setText(loadDescription(mItem1Selected));

               /* new AsyncTask<String, Long, String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        return loadDescription(params[0]);
                    }

                    @Override
                    protected void onPostExecute(String aString) {
                        mItem1Text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        mItem1Text.setText(aString);
                    }
                }.execute(mItem1Selected);
                */

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mItem2Spinner = (Spinner)findViewById(R.id.item2_spinner);

        mItem2Spinner.setVisibility(View.GONE);
        mItem2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mItem2Selected = parent.getItemAtPosition(position).toString();

                mItem2Text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                mItem2Text.setText(loadDescription(mItem2Selected));

                /*new AsyncTask<String, Long, String>(){
                    @Override
                    protected String doInBackground(String... params) {
                        return loadDescription(params[0]);
                    }

                    @Override
                    protected void onPostExecute(String aString) {
                        mItem2Text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        mItem2Text.setText(aString);
                    }
                }.execute(mItem2Selected);
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("optionSpinner", mOptionSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("typeSpinner", mTypeSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("item1Spinner", mItem1Spinner.getSelectedItemPosition());
        savedInstanceState.putInt("item2Spinner", mItem2Spinner.getSelectedItemPosition());
    }

    private String loadDescription(String itemSelected){

        String sql = "SELECT itemName, price FROM " + ITEMTABLE + " WHERE itemName = ?";
        List<String> rowData = getRow(sql, itemSelected);
        String mData = "";
        for(int i = 0; i < rowData.size(); i ++) {
            mData = mData + rowData.get(i) + "\n";
        }
        return mData;
    }

    private ArrayAdapter<String> loadOptionSpinner(){
        List<String> optionList = new ArrayList<>();
        optionList.add(ALLITEMS);
        optionList.add(ONHANDITEMS);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(CompareActivity.this,
                android.R.layout.simple_spinner_item, optionList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return spinnerAdapter;
    }

    private ArrayAdapter<String> loadSpinnerData(String sql) {
        List<String> itemTypes = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(sql, null);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                itemTypes.add(cursor.getString(0));
            }
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(CompareActivity.this,
                    android.R.layout.simple_spinner_item, itemTypes);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return spinnerAdapter;
        } finally {
            cursor.close();
        }
    }

    private List<String> getRow(String sql, String arg){
        List<String> rowData = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{arg});
        try {
            String[] names = cursor.getColumnNames();
            for(int i = 0; i < names.length; i++) {
                if (cursor.moveToFirst()) {
                    String databaseColumnName = cursor.getColumnName(i);
                    String columnName;
                    switch(databaseColumnName){
                        case("itemName"):
                            columnName = "Name: ";
                            break;
                        case("price"):
                            columnName = "Price: $";
                            break;
                        default:
                            columnName = "";
                            break;
                    }
                    rowData.add(columnName + cursor.getString(i) + "\n");
                }
            }
            return rowData;
        }
        finally {
            cursor.close();
        }
    }
}
