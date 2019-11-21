package com.example.lampservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Intent lampIntent;   // 設定光明燈每30分鐘開一次燈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar begin = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        Calendar end = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
//        end.add(Calendar.DATE, 1);
        end.add(Calendar.MINUTE, 1);
        startLampService(begin.getTime(),end.getTime(),true);
    }

    private void startLampService(Date begin, Date end, boolean state) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//進行轉換
        String beginString = sdf.format(begin);
        String endString = sdf.format(end);
        Log.i(TAG, "beginString: " + beginString);
        Log.i(TAG, "endString: " + endString);

        lampIntent = new Intent();
        lampIntent.setClass(MainActivity.this, LampService.class);
        lampIntent.putExtra("begin", beginString);
        lampIntent.putExtra("end", endString);
        lampIntent.putExtra("state", state); //開燈為true，關燈為false

        startService(lampIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(lampIntent);
    }
}
