package com.example.arid3011q1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText edittext;
    TextView AgeIn,CalculatedAge;
    final Calendar myCalendar = Calendar.getInstance();
    String AgeUnit,COLOR;
    Button  agecalulateButton;
    String myFormat = "MM/dd/yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        AgeUnit= sharedPreferences.getString("unit", "days");
        COLOR= sharedPreferences.getString("colors", "red");

        agecalulateButton = (Button) findViewById(R.id.CalculateAgeButton);
        edittext= (EditText) findViewById(R.id.Birthday);
        AgeIn = (TextView) findViewById(R.id.ageIn);
        CalculatedAge = (TextView) findViewById(R.id.calculatedAge);

        AgeIn.setText("Age In " + AgeUnit);


        switch (COLOR) {
            case "red":
                CalculatedAge.setTextColor(Color.RED);
                break;
            case "green":
                CalculatedAge.setTextColor(Color.GREEN);
                break;
            case "blue":
                CalculatedAge.setTextColor(Color.BLUE);
                break;
            default:
                CalculatedAge.setTextColor(Color.RED);
                break;
        }
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        agecalulateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

               String[] Arr= edittext.getText().toString().split("/");

                LocalDate DOB = LocalDate.of(Integer.parseInt(Arr[2]),Integer.parseInt(Arr[0]) , Integer.parseInt(Arr[1])); //specify year, month, date directly
                LocalDate now = LocalDate.now(); //gets localDate
                Period diff = Period.between(DOB, now); //difference between the dates is calculated
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setTitle("Alert");
//                alertDialog.setMessage(diff.getYears() * 365 +   diff.getMonths() * 30 + diff.getDays());
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
                switch (AgeUnit){
                    case "days":
                        CalculatedAge.setText(diff.getYears() * 365 +   diff.getMonths() * 30 + diff.getDays() + "");
                        break;
                    case "months":
                        CalculatedAge.setText(diff.getYears() * 12 +   diff.getMonths() + 30/diff.getDays() +"");
                        break;
                    case "years":
                        CalculatedAge.setText(diff.getYears()+ "");
                        break;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateLabel() {
         //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "Item 1 selected cloud", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.contact:
                Toast.makeText(this, "Item 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.setting:

                Intent intent = new Intent(MainActivity.this,Setting.class);

                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}