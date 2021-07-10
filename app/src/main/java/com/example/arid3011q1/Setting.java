package com.example.arid3011q1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Setting extends AppCompatActivity {
    String[] AgeUnit={"Days","Months","Years"};
    String[] COLORS={"Red","Green","Blue"};
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goBack);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Spinner Agespin = (Spinner) findViewById(R.id.AgeUnit);
        Spinner Colorspin = (Spinner) findViewById(R.id.ColorSpiner);
        final SharedPreferences mySharedPreferencesFile = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = mySharedPreferencesFile.edit();
        ArrayAdapter ageAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,AgeUnit);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter colorAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,COLORS);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Colorspin.setAdapter(colorAdapter);
        Agespin.setAdapter(ageAdapter);


        Agespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mEditor.putString("unit", AgeUnit[position].toLowerCase());
                mEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Colorspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mEditor.putString("colors", COLORS[position].toLowerCase());
                mEditor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}