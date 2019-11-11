package com.example.countdown;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    EditText editText;
    Button button;
    TextView textView;
    CountdownService.MyBinder myBinder = null;
    Intent i;
    String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.input);
        textView = findViewById(R.id.tv);
        button = findViewById(R.id.button);
        i = new Intent();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.setClass(MainActivity.this, CountdownService.class);
//                i.setPackage(getPackageName());
                int inputtime = Integer.valueOf(editText.getText().toString());
                i.putExtra("time", inputtime);
                startService(i);
                bindService(i, MainActivity.this, BIND_AUTO_CREATE);
            }
        });

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        myBinder = (CountdownService.MyBinder) iBinder; //
        myBinder.getService().setCallBack(new CountdownService.CallBack() {
            @Override
            public void onShowChanged(String show) {
                Message message = new Message();
                Bundle b = new Bundle();
                Log.i("MainActivity", "show: " + show);
                b.putString("show", show);
                message.setData(b);
                handler.sendMessage(message);


            }
        });

//        if (show.equals("" + 0)){

//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(i);
        unbindService(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText(msg.getData().getString("show"));
            if (msg.getData().getString("show").equals("0")) {
                Log.i("MainActivity", "showwwwwwwww" );
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("倒數結束了喔")
                        .setCancelable(false)
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).show();
            }
        }
    };
}
