package com.example.homesafe3;

import android.content.Intent;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class OutsideActivity extends AppCompatActivity {
    String shared = "file";


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_outside);
        Intent intent = getIntent();

        TextView dustText = (TextView) findViewById(R.id.dustText);
        TextView rainText = (TextView) findViewById(R.id.rainText);
        TextView tempText = (TextView) findViewById(R.id.tempText);
        TextView timeText = (TextView) findViewById(R.id.timeText);


    }
}
