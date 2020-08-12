package com.example.isitopen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class FilterActivity extends AppCompatActivity {

    Button backButton;
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TextView textView_Time;
    private TimePickerDialog.OnTimeSetListener callbackMethodT;
    private TextView storeType;
    int[] date1 =new int[3];
    int[] ts1 = new int[2];
    int[] te1 = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button backButton;
        Button Find;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // 가게종류 받아오기
        Intent intent = getIntent();
        String typeN = intent.getStringExtra("value");

        storeType = findViewById(R.id.storetype);
        storeType.setText(typeN); //가게종류 표기


        this.InitializeViewD();
        this.InitializeListenerD();

        this.InitializeViewT();
        this.InitializeListenerT();

        //public Info findinfo(typeN,null,date1,ts1,te1,null);

        //뒤로가기버튼
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //찾았니? 버튼
        Find = (Button)findViewById(R.id.Find);
        Find.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void InitializeViewD(){
        textView_Date = (TextView)findViewById(R.id.Date);
    }
    public void InitializeViewT(){
        textView_Time = (TextView)findViewById(R.id.Time);
    }

    public void InitializeListenerD(){
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                date1[0] = year;
                date1[1] = month;
                date1[2] = day;
                textView_Date.setText(year + "년" + month + "월" + day + "일");
            }
        };
    }
    public void InitializeListenerT(){
        callbackMethodT = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                ts1[0] = hourOfDay;
                ts1[1] = minute;
                textView_Time.setText(hourOfDay + "시" + minute + "분");
            }
        };
    }

    public void OnClickHandler(View view){
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod,2020,8,10);
        dialog.show();
    }
    public void OnClickHandlerT(View view){
        TimePickerDialog dialogT = new TimePickerDialog(this, callbackMethodT,4,25,true);
        dialogT.show();
    }
}