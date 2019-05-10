package com.example.stephenlau.testlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.stephenlau.testlibrary.userLog.UserLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        UserLog.startOrContinue();
    }

    @Override
    protected void onStop() {
        super.onStop();
        UserLog.pauseTask();
    }

    public void onTextViewClick(View view) {
        UserLog.addUserLog("onTextViewClick!");
    }
}
