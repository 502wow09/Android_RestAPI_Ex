package com.example.homesafe3;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView dustText = (TextView) findViewById(R.id.dustText);
        TextView timeText = (TextView) findViewById(R.id.timeText);
        TextView rainText = (TextView) findViewById(R.id.rainText);
        TextView tempText = (TextView) findViewById(R.id.tempText);
        Button outsideButton = (Button) findViewById(R.id.outsideButton);


    }
}