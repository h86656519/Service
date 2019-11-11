package com.example.countdown;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CountdownService extends Service {
    private String show = "倒數計時:";
    private CallBack callBack = null;
    int countdountime = 10; // defult 先設10s

    public CountdownService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "CountdownService 啟動了", Toast.LENGTH_SHORT).show();
        countdountime = intent.getIntExtra("time", 10);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("CountdownService", "countdountime : " + countdountime);
                while (countdountime > 0) {
                    System.out.println(show + countdountime);
                    countdountime--;

                    // 执行回调函数，刷新 TextView 显示
                    if (callBack != null) {
                        callBack.onShowChanged(String.valueOf(countdountime));
                    }

                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }


    // 当服务销毁时，调用该方法
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy");
    }

    public static interface CallBack {
        void onShowChanged(String time);
    }

    public class MyBinder extends android.os.Binder {
        public void setShow(String s) {
            show = s;
        }

        public CountdownService getService() {
            return CountdownService.this;
        }
    }

}
