package com.example.lampservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LampService extends Service {
    private static final String TAG = "LampService";
    boolean state;
     ;
    int lampTime = 5 * 1000; // defult 30分鐘(1800秒)
    Date beginTime,endTime,currenTime;

    public LampService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //ISO 8601
//        String time = "2019-02-0313:24";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String beginString = intent.getStringExtra("begin");
        String endString = intent.getStringExtra("end");
        currenTime = new Date();
        state = intent.getBooleanExtra("state", false);

        try {
             beginTime = sdf.parse(beginString); //轉回Date
             endTime = sdf.parse(endString);
            Log.i(TAG, "接到轉型後的 endTime: " + endTime);
            Log.i(TAG, "接到轉型後的 beginTime: " + beginTime);
            Log.i(TAG, "接到轉型後的 currenTime " + currenTime);
            Log.i(TAG, "比較 beginTime.before(endTime): " + beginTime.before(endTime));
//            Log.i(TAG, "currenTime.before(endTime): " + currenTime.before(dataToCalendar(endTime)));
//            Log.i(TAG, "currenTime.after(endTime): " + currenTime.after(dataToCalendar(endTime)));

        }catch (ParseException e) {
            e.printStackTrace();
        }
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i(TAG, "state : " + state);
                while (state) {
                    currenTime = new Date();
                    Log.i(TAG, "inside state : " + state);
                    Log.i(TAG, "currenTime.getTime();" + currenTime);
                    Log.i(TAG, "in side endTime : " + endTime);
                    Log.i(TAG, "currenTime.after(endTime) : " + currenTime.after(endTime));
                    try {
                        sleep(lampTime);
                        if (currenTime.after(endTime)){
                            Log.i(TAG, "aaaaaaaaaaaaaaaaa");
                            state = false;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();



    }
    // 当服务销毁时，调用该方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");

        state = false;
    }
    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
