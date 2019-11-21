package com.example.lampservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LampService extends Service {
    private static final String TAG = "LampService";
    boolean state;
    Calendar currenTime;
    int lampTime = 5 * 1000; // defult 30分鐘(1800秒)

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

        state = intent.getBooleanExtra("state", false);
        currenTime = Calendar.getInstance();
        currenTime.getTime();
        try {
            Date beginTime = sdf.parse(beginString); //轉回Date
            Date endTime = sdf.parse(endString);
            Log.i(TAG, "endTime: " + endTime);
            Log.i(TAG, "beginTime: " + beginTime);
            Log.i(TAG, "currenTime.getTime() : " + currenTime.getTime());
            Log.i(TAG, "beginTime.before(endTime): " + beginTime.before(endTime));
            Log.i(TAG, "currenTime.before(endTime): " + currenTime.before(dataToCalendar(endTime)));

        }catch (ParseException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            @Override
            public void run() {
                super.run();
                while (state) {
                    try {
                        sleep(lampTime);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

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
