package com.example.homesafe3;

//import 부분은 자동추가함
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.net.URLEncoder;
import org.json.*;
import android.util.Log;

public class OutsideActivity extends AppCompatActivity {
    String shared = "file";

    String Key = "LY1N2Z6Ex8kH94swRYQw5fnmPsIyZY0/irhrsItAkZmHSzTE2IbeB1UBb1AsFmSix7z8GPl6LAILWLJBWhFJ9w==";

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd0600");
    String today = sdf.format(date);

    String cityname = "종로구";
    {
        try {
            cityname = URLEncoder.encode("종로구", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    String lotation = "11B10101";   //서울
    String lotation2 = "11B00000";  //서울인천경기도

    String dustURL = String.format("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=%s&dataTerm=daily&pageNo=1&numOfRows=1&returnType=json&serviceKey=%s",cityname, Key);
    String tempURL = String.format("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=%s&numOfRows=1&pageNo=1&regId=%s&tmFc=%s&dataType=JSON", Key, lotation, today);
    String rainURL = String.format("http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=%s&numOfRows=1&pageNo=1&regId=%s&tmFc=%s&dataType=JSON", Key, lotation2, today);

    String dustVal = "none";
    String timeVal = "none";
    String rainVal = "none";
    String tempVal = "none";

    public class RestAPITask extends AsyncTask<String, Void, String> {
        String response;
        String apiurl;
        RestAPITask(String urlString) {
            apiurl = urlString;
        }

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(apiurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream is = conn.getInputStream();

                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                response = builder.toString();
            }
            catch (UnsupportedEncodingException e) {
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("REST_API", "GET method failed: " + e.getMessage());
                e.printStackTrace();
            }
            return response;
        }
    }

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_outside);
        Intent intent = getIntent();

        TextView dustText = (TextView) findViewById(R.id.dustText);
        TextView rainText = (TextView) findViewById(R.id.rainText);
        TextView tempText = (TextView) findViewById(R.id.tempText);
        TextView timeText = (TextView) findViewById(R.id.timeText);

        //response
        RestAPITask task = new RestAPITask(dustURL);
        String responseJSON = "";
        try {
            responseJSON = task.execute().get();
        } catch (ExecutionException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }

        RestAPITask taskTemp = new RestAPITask(tempURL);
        String responseTemp = "";
        try {
            responseTemp = taskTemp.execute().get();
        } catch (ExecutionException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }

        RestAPITask taskRain = new RestAPITask(rainURL);
        String responseRain = "";
        try {
            responseRain = taskRain.execute().get();
        } catch (ExecutionException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }

        //parse
        try {
            JSONObject obj = new JSONObject(responseJSON);
            JSONArray items = obj.getJSONObject("response").getJSONObject("body").getJSONArray("items");

            for (int i = 0; i < items.length(); i++)
            {
                dustVal = items.getJSONObject(i).getString("pm10Value");
                timeVal = items.getJSONObject(i).getString("dataTime");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(responseTemp);
            JSONArray item = obj.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

            for (int i = 0; i < item.length(); i++)
            {
                int min = item.getJSONObject(i).getInt("taMin3");
                int max = item.getJSONObject(i).getInt("taMax3");
                float aver = (min+max)/2;
                tempVal = String.valueOf(aver);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(responseRain);
            JSONArray item = obj.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

            for (int i = 0; i < item.length(); i++)
            {
                int am = item.getJSONObject(i).getInt("rnSt3Am");
                int pm = item.getJSONObject(i).getInt("rnSt3Pm");
                rainVal = String.valueOf((am+pm)/2+"%");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dustText.setText(dustVal);
        timeText.setText(timeVal);
        tempText.setText(tempVal);
        rainText.setText(rainVal);

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dustVal", dustVal);
        editor.putString("timeVal", timeVal);
        editor.putString("rainVal", rainVal);
        editor.putString("tempVal", tempVal);
        editor.commit();
    }
}
