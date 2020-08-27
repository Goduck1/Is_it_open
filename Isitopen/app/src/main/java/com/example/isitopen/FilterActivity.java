package com.example.isitopen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    SeekBar sb;
    TextView tv;
    int init = 500;

    Button backButton;
    private TextView textView_Date;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private TextView textView_Time;
    private TimePickerDialog.OnTimeSetListener callbackMethodT;
    private TextView storeType;
    int[] date1 =new int[3];
    int[] ts1 = new int[2];
    int[] te1 = new int[2];

    int passrange = 500; //map으로 반경넘기는변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button backButton;
        Button Find;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // 가게종류 받아오기
        Intent intent = getIntent();
        final String typeN = intent.getStringExtra("value");
        final double[] array1 = intent.getDoubleArrayExtra("loc");

        // 현위치 저장하는 배열 변환
        TextView Nlocation = findViewById(R.id.nlocation);
        Geocoder g = new Geocoder(this);
        List<Address> address = null;
        try {
            address = g.getFromLocation(array1[0],array1[1],10);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("test","입출력오류");
        }
        if(address!=null){
            if(address.size()==0){
                Nlocation.setText("주소찾기 오류");
            }else{
                Log.d("찾은 주소",address.get(0).toString());
                Nlocation.setText(address.get(0).getAddressLine(0));
            }
        }

        // 서치버튼을 통해 위치 재설정
        Button Sbutton = findViewById(R.id.searchBtn);
        Sbutton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), SearchMap.class);
                intent3.putExtra("newloc",array1);
                startActivity(intent3);
            }
        });

        Intent intent4 = getIntent();
//        final double[] array1 = intent4.getDoubleArrayExtra("loc");




        storeType = findViewById(R.id.storetype);
        storeType.setText(typeN); //가게종류 표기

        this.InitializeViewD();
        this.InitializeListenerD();

        this.InitializeViewT();
        this.InitializeListenerT();

        //seekbar 초기설정
        tv=(TextView)findViewById(R.id.showRange);
        sb=(SeekBar)findViewById(R.id.seekBar);
        sb.setMax(2000);
        sb.setProgress(init);
        tv.setText(init+"m");


        //뒤로가기버튼
        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });



        //열었니? 버튼
        Find = (Button)findViewById(R.id.Find);
        Find.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);

                Info findInfo = new Info(typeN,"name",date1,ts1,te1,"locate");
                //현재위치 intent
                intent.putExtra("class",findInfo);
                intent.putExtra("loc",array1);
                intent.putExtra("range",passrange);
                startActivity(intent);
                finish();
            }
        });

        //seekbar 움직임 받아서 거리 출력
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int range, boolean b) {
                int temp = (range / 50) * 50;

                passrange = temp;

                tv.setText(temp+"m");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void InitializeViewD(){
        textView_Date = (TextView)findViewById(R.id.Date);
    }
    public void InitializeViewT(){
        textView_Time = (TextView)findViewById(R.id.Time);
    }

    //날짜입력함수
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
    //시간입력함수
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

    //달력위젯불러오기
    public void OnClickHandler(View view){
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod,2020,8,10);
        dialog.show();
    }
    //시계위젯불러오기
    public void OnClickHandlerT(View view){
        TimePickerDialog dialogT = new TimePickerDialog(this, callbackMethodT,4,25,true);
        dialogT.show();
    }
}