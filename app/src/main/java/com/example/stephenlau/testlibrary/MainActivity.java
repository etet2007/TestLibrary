package com.example.stephenlau.testlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hundsun.base.HsSysInfoUtils;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HsSysInfoUtils.getInstance().init(this);
        Log.d(TAG, "HsSysInfoUtils.getInstance().getSysInfo(): " + HsSysInfoUtils.getInstance().getSysInfo());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getSysInfoCompletion(): "+ HsSysInfoUtils.getInstance().getSysInfoCompletion());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getAbnormalType(): "+ HsSysInfoUtils.getInstance().getAbnormalType());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getDetailError(): "+ HsSysInfoUtils.getInstance().getDetailError());

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onTextViewClick(View view) {
        Log.d(TAG, "HsSysInfoUtils.getInstance().getSysInfo(): " + HsSysInfoUtils.getInstance().getSysInfo());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getSysInfoCompletion(): "+ HsSysInfoUtils.getInstance().getSysInfoCompletion());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getAbnormalType(): "+ HsSysInfoUtils.getInstance().getAbnormalType());
        Log.d(TAG, "HsSysInfoUtils.getInstance().getDetailError(): "+ HsSysInfoUtils.getInstance().getDetailError());
    }
}
