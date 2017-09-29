package com.example.msi.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {

    private int num = 0;
    private boolean condition =  false;
    public static String mess = "Service";

    private MyBinder mbinder = new MyBinder();
    @Override
    public void onCreate() {
        Log.d(mess, "onCreate:executed "+"wang");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!condition)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    num++;
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        Log.d(mess, "onDestroy: executed");
        this.condition = true;
        super.onDestroy();
    }
    //start指令（command）
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(mess, "onStartCommand: executed");
        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mbinder;
    }

    //创建一个binder类，用于服务的绑定
    class MyBinder extends Binder{
        public MyService LogMyMethod() {
            return MyService.this;
        }
    }
    public int getNum(){
        return num;
    }
}
