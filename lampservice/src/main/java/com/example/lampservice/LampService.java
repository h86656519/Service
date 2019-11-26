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
import java.util.Timer;
import java.util.TimerTask;

/*
 強制開啟光明燈，每30鐘檢查一次
 */
public class LampService extends Service {
    private static final String TAG = "LampService";
    private static final long period_check = 1800 * 1000; //每30分鐘check，defult 30分鐘(1800秒)
    private static final long delay_check = 0;
//    private LampController lampController = new LampController();
    private boolean lampState;
    private Timer checkTimer; //Check 計時器
    private Date beginDate, endDate;
    private Calendar closeLampServiceTime, currenTime; //LampService 的關閉時間

    public LampService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand ");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginString = intent.getStringExtra("begin");
        String endString = intent.getStringExtra("end");
        lampState = intent.getBooleanExtra("lampState", false);

        try {
            beginDate = sdf.parse(beginString);
            endDate = sdf.parse(endString);
            setLampServiceCloseTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        checkTimer = new Timer();
        checkTimer.schedule(startCheckTask(), delay_check, period_check);

        return super.onStartCommand(intent, flags, startId);
    }

    private TimerTask startCheckTask() {
        return new TimerTask() {
            @Override
            public void run() {
                currenTime = Calendar.getInstance();
                if (lampState) {
//                    開燈
//                    lampController.lampOn();
//                    lampController.lampMainOn();
                    Log.i(TAG, "開燈中");
                } else {
//                    關燈
//                    lampController.lampOff();  //不由這邊關燈，由Schedule 來關燈
//                    lampController.lampMainOff();
                    Log.i(TAG, "關燈了");
                }
                if (currenTime.after(closeLampServiceTime)) {
                    lampState = false;
                    stopSelf();
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "service 結束");
        checkTimer.cancel();
    }

    public void setLampServiceCloseTime(){
        closeLampServiceTime = Calendar.getInstance();
        closeLampServiceTime.setTime(endDate);
        closeLampServiceTime.add(Calendar.MINUTE, -3); //關閉 service 的時間為 endTime前的3分鐘
    }
}
