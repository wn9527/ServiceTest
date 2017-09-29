package com.example.msi.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1;
    private Button button2;
    private Button mbind;
    private Button munbind;
    private Button databtn;
    private ProgressBar mprogress;
    private MyService.MyBinder mbinder;
    private MyService mservice;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbinder = (MyService.MyBinder) service;
            mservice = mbinder.LogMyMethod();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("mbinddddd", "onServiceDisconnected: executed");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Thread", "onCreate: "+Thread.currentThread().getId());
        button1 = (Button) findViewById(R.id.start);
        button2 = (Button) findViewById(R.id.stop);
        mbind = (Button) findViewById(R.id.bind);
        munbind = (Button)findViewById(R.id.unbind);
        databtn= (Button) findViewById(R.id.getdata);
        databtn.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        mbind.setOnClickListener(this);
        munbind.setOnClickListener(this);

        mprogress = (ProgressBar) findViewById(R.id.progress);

    }
    private class Mytask extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Intent intent = new Intent(this,MyService.class);
                startService(intent);
                break;
            case R.id.stop:
                Intent intent1 = new Intent(this,MyService.class);
                stopService(intent1);
                break;
            case R.id.bind:
                Intent bindintent = new Intent(this,MyService.class);
                //绑定服务之后自动创建服务对象
                bindService(bindintent,connection,BIND_AUTO_CREATE);
                break;
            case  R.id.unbind:
                if(mservice!=null){
                    mservice=null;
                    unbindService(connection);
                }
                break;
            case R.id.getdata:
                if(mservice != null){

                    Log.d("num", "onClick: "+mservice.getNum());
                }else {
                    Log.d("num", "onClick: ceshi");
                }
                break;
            default:
                break;
        }
    }
}
