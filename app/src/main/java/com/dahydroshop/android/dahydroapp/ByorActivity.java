package com.dahydroshop.android.dahydroapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 11/25/2015.
 */
public class ByorActivity extends AppCompatActivity {

    private static final String ITEMTABLE = "item";
    private static final String ALLITEMS = "All Items";
    private static final String ONHANDITEMS = "Only items that are currently on hand";

    private Spinner onHandSpinner;
    private Spinner lampSpinner;
    private Spinner ballastSpinner;
    private Spinner containerSpinner;
    private Spinner soilSpinner;
    private Spinner nutrientsSpinner;
    private Spinner climateSpinner;
    private String mSqlArgs;
    private String mLamps;
    private String mBallast;
    private String mContainer;
    private String mSoil;
    private String mNutrient;
    private String mClimate;
    private int mLampQty;
    private int mBallastQty;
    private int mContainerQty;
    private int mSoilQty;
    private int mNutrientQty;
    private int mClimateQty;
    private EditText mLampQtyEdit;
    private EditText mBallastQtyEdit;
    private EditText mContainerQtyEdit;
    private EditText mSoilQtyEdit;
    private EditText mNutrientQtyEdit;
    private EditText mClimateQtyEdit;
    private Button mTotalButton;
    private ArrayAdapter<String> mOnHandSpinnerAdapter;
    private ArrayAdapter<String> mLampSpinnerAdapter;
    private ArrayAdapter<String> mBallastSpinnerAdapter;
    private ArrayAdapter<String> mContainerSpinnerAdapter;
    private ArrayAdapter<String> mSoilSpinnerAdapter;
    private ArrayAdapter<String> mNutrientSpinnerAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private ArrayAdapter<String> mClimateSpinnerAdapter;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byor);

        mDatabase = new DatabaseHelper(this).getWritableDatabase();

        initializeUI();

        mOnHandSpinnerAdapter = loadOptionSpinner();
        onHandSpinner.setAdapter(mOnHandSpinnerAdapter);
        if(savedInstanceState != null) {
            onHandSpinner.setSelection(savedInstanceState.getInt("onHandSpinner", 0));
        }
        onHandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String optionSelected = parent.getItemAtPosition(position).toString();

                if (optionSelected.equals(ALLITEMS)) {
                    mSqlArgs = "' GROUP BY itemName";
                } else {
                    mSqlArgs = "' AND quantity > 0 GROUP BY itemName";
                }
                String sql = "SELECT itemName from " + ITEMTABLE + " WHERE department = '";

                mLampSpinnerAdapter = loadSpinnerData(sql + "Lamps" + mSqlArgs);
                mBallastSpinnerAdapter = loadSpinnerData(sql + "Grow Light Ballasts" + mSqlArgs);
                mContainerSpinnerAdapter = loadSpinnerData(sql + "Pots & Containers" + mSqlArgs);
                mSoilSpinnerAdapter = loadSpinnerData(sql + "Growing Media" + mSqlArgs);
                mNutrientSpinnerAdapter = loadSpinnerData(sql + "Nutrients & Additives" + mSqlArgs);
                mClimateSpinnerAdapter = loadSpinnerData(sql + "Ventilation AC Heating" + mSqlArgs);

                lampSpinner.setAdapter(mLampSpinnerAdapter);
                ballastSpinner.setAdapter(mBallastSpinnerAdapter);
                containerSpinner.setAdapter(mContainerSpinnerAdapter);
                soilSpinner.setAdapter(mSoilSpinnerAdapter);
                nutrientsSpinner.setAdapter(mNutrientSpinnerAdapter);
                climateSpinner.setAdapter(mClimateSpinnerAdapter);
                if (savedInstanceState != null) {
                    lampSpinner.setSelection(savedInstanceState.getInt("lampSpinner", 0));
                    ballastSpinner.setSelection(savedInstanceState.getInt("ballastSpinner", 0));
                    containerSpinner.setSelection(savedInstanceState.getInt("containerSpinner", 0));
                    soilSpinner.setSelection(savedInstanceState.getInt("soilSpinner", 0));
                    nutrientsSpinner.setSelection(savedInstanceState.getInt("nutrientSpinner", 0));
                    climateSpinner.setSelection(savedInstanceState.getInt("climateSpinner", 0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lampSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLamps = lampSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ballastSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBallast = ballastSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        containerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mContainer = containerSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        soilSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSoil = soilSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nutrientsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNutrient = nutrientsSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        climateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClimate = climateSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mTotalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLampQtyEdit.getText().toString().isEmpty()){
                    mLampQty = 0;
                }else{
                    mLampQty = Integer.parseInt(mLampQtyEdit.getText().toString());
                }
                if(mBallastQtyEdit.getText().toString().isEmpty()){
                    mBallastQty = 0;
                }else{
                    mBallastQty = Integer.parseInt(mBallastQtyEdit.getText().toString());
                }
                if(mContainerQtyEdit.getText().toString().isEmpty()){
                    mContainerQty = 0;
                }else{
                    mContainerQty = Integer.parseInt(mContainerQtyEdit.getText().toString());
                }
                if(mSoilQtyEdit.getText().toString().isEmpty()){
                    mSoilQty = 0;
                }else{
                    mSoilQty = Integer.parseInt(mSoilQtyEdit.getText().toString());
                }
                if(mNutrientQtyEdit.getText().toString().isEmpty()){
                    mNutrientQty = 0;
                }else{
                    mNutrientQty = Integer.parseInt(mNutrientQtyEdit.getText().toString());
                }
                if(mClimateQtyEdit.getText().toString().isEmpty()){
                    mClimateQty = 0;
                }else{
                    mClimateQty = Integer.parseInt(mClimateQtyEdit.getText().toString());
                }

                double total = calcTotals(new String[]{mLamps, mBallast, mContainer, mSoil, mNutrient, mClimate},
                        new int[]{mLampQty, mBallastQty, mContainerQty, mSoilQty, mNutrientQty, mClimateQty});
                NotificationDialog mNotifyDialog = NotificationDialog.newInstance(String.format("$%1$,.2f",total));
                mNotifyDialog.show(getSupportFragmentManager(), "total_price");
                //Toast.makeText(ByorActivity.this, String.format("$%1$,.2f",total), Toast.LENGTH_LONG).show();

                //TODO: Insert items to a database to be used for popular.
            }
        });
    }
    private void initializeUI(){

        onHandSpinner = (Spinner)findViewById(R.id.item_select_spinner);
        lampSpinner = (Spinner)findViewById(R.id.byor_lamp_spinner);
        ballastSpinner = (Spinner)findViewById(R.id.byor_ballast_spinner);
        containerSpinner = (Spinner)findViewById(R.id.byor_container_spinner);
        soilSpinner = (Spinner)findViewById(R.id.byor_soil_spinner);
        nutrientsSpinner = (Spinner)findViewById(R.id.byor_nutrients_spinner);
        climateSpinner = (Spinner)findViewById(R.id.byor_climate_spinner);
        mLampQtyEdit = (EditText)findViewById(R.id.byor_lamps_edit);
        mLampQtyEdit.setText(String.format("%d", 1));
        mBallastQtyEdit = (EditText)findViewById(R.id.byor_ballast_edit);
        mBallastQtyEdit.setText(String.format("%d", 1));
        mContainerQtyEdit = (EditText)findViewById(R.id.byor_container_edit);
        mContainerQtyEdit.setText(String.format("%d", 1));
        mSoilQtyEdit = (EditText)findViewById(R.id.byor_soil_edit);
        mSoilQtyEdit.setText(String.format("%d", 1));
        mNutrientQtyEdit = (EditText)findViewById(R.id.byor_nutrients_edit);
        mNutrientQtyEdit.setText(String.format("%d", 1));
        mClimateQtyEdit = (EditText)findViewById(R.id.byor_climate_edit);
        mClimateQtyEdit.setText(String.format("%d", 1));
        mTotalButton = (Button)findViewById(R.id.byor_submit);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("onHandSpinner", onHandSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("lampSpinner", lampSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("ballastSpinner", ballastSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("containerSpinner", containerSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("soilSpinner", soilSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("nutrientSpinner", nutrientsSpinner.getSelectedItemPosition());
        savedInstanceState.putInt("climateSpinner", climateSpinner.getSelectedItemPosition());
    }

    private double calcTotals(String[] items, int[] qtys){
        double total = 0.00;
        int i = 0;
        for(String item: items) {
            String sql = "SELECT price FROM " + ITEMTABLE + " WHERE itemName='" + item + "'";
            Cursor c = mDatabase.rawQuery(sql, null);
            try{
                c.moveToFirst();
                String price = c.getString(0);
                total+= Double.parseDouble(price)*qtys[i];
                i++;
            }
            finally {
                c.close();
            }
        }
        return total;
    }
    private ArrayAdapter<String> loadSpinnerData(String sql) {
        List<String> itemTypes = new ArrayList<>();
        ArrayAdapter<String> spinnerAdapter;
        Cursor cursor = mDatabase.rawQuery(sql, null);
        try {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                itemTypes.add(cursor.getString(0));
            }
            spinnerAdapter = new ArrayAdapter<>(ByorActivity.this,
                    android.R.layout.simple_spinner_item, itemTypes);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            return spinnerAdapter;
        } finally {
            cursor.close();
        }
    }

    private ArrayAdapter<String> loadOptionSpinner(){
        List<String> optionList = new ArrayList<>();
        optionList.add(ALLITEMS);
        optionList.add(ONHANDITEMS);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(ByorActivity.this,
                android.R.layout.simple_spinner_item, optionList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return spinnerAdapter;
    }
}
