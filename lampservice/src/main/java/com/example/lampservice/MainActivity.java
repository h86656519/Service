package com.example.lampservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
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
        startLampService(begin.getTime(), end.getTime(), true);
    }

    //region 開/關燈Service
    private void startLampService(Date begin, Date end, boolean state) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginString = sdf.format(begin);
        String endString = sdf.format(end);
        lampIntent = new Intent();
        lampIntent.setClass(this, LampService.class);
        lampIntent.putExtra("begin", beginString);
        lampIntent.putExtra("end", endString);
        lampIntent.putExtra("lampState", state); //開燈為true，關燈為false

        startService(lampIntent);
    }

    public void stopLampService() {
        if (isServiceRunning(LampService.class) && lampIntent != null) {
            stopService(lampIntent);//確保service關閉
        }
    }
    //endregion

    //確認Service 是否存在
    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    //region 銷毀
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (lampIntent != null)
            stopService(lampIntent);

    }
    //endregion
}
