package com.example.isitopen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
    }

    public void onButtonToCafe(View v){
        Toast.makeText(getApplicationContext(), "카페 ㄱㄱ", Toast.LENGTH_LONG).show();
    }

    public void onButtonToConvenienceStore(View v){
        Toast.makeText(getApplicationContext(), "편의점 ㄱㄱ", Toast.LENGTH_LONG).show();
    }

    public void onButtonToPharmacy(View v){
        Toast.makeText(getApplicationContext(), "약국 ㄱㄱ", Toast.LENGTH_LONG).show();
    }

    public void onButtonToAnimalHospital(View v){
        Toast.makeText(getApplicationContext(), "동물병원 ㄱㄱ", Toast.LENGTH_LONG).show();
    }


}