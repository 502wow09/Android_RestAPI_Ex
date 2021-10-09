package com.example.homesafe3;

//import 부분은 자동추가함
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

        SharedPreferences sharedPreferences = getSharedPreferences("file", 0);
        String dustVal = sharedPreferences.getString("dustVal","0");
        String timeVal = sharedPreferences.getString("timeVal","0");
        String rainVal = sharedPreferences.getString("rainVal","0");
        String tempVal = sharedPreferences.getString("tempVal","0");

        dustText.setText(dustVal);
        timeText.setText(timeVal);
        rainText.setText(rainVal);
        tempText.setText(tempVal);

        outsideButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OutsideActivity.class);
                startActivity(intent);
            }
        });
    }
}