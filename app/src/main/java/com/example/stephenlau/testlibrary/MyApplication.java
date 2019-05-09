package com.example.stephenlau.testlibrary;

import android.app.Application;

import com.example.stephenlau.testlibrary.EventTracking.Monitor.Monitor;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Monitor.init(this,true);
    }
}
