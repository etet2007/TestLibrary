package com.example.stephenlau.testlibrary.userLog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.stephenlau.testlibrary.R;
import com.example.stephenlau.testlibrary.userLog.UserLog;
import com.example.stephenlau.testlibrary.userLog.UserLogSingleton;


public class UserLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        UserLog.start();
        UserLog.addUserLog("UserLog message1");

        UserLogSingleton userLogSingleton = UserLogSingleton.getInstance();
        userLogSingleton.start();
        userLogSingleton.addUserLog("UserLogSingleton message1");
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onTextViewClick(View view) {
       }
}
