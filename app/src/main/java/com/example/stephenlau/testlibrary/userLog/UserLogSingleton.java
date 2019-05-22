package com.example.stephenlau.testlibrary.userLog;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class UserLogSingleton {
    private static final UserLogSingleton ourInstance = new UserLogSingleton();

    public static UserLogSingleton getInstance() {
        return ourInstance;
    }

    private UserLogSingleton() {
    }

    public  ReportRepository reportRepository;

    private StringBuilder logCache = new StringBuilder();
    private int reportSeqNumber = 0;
    private long lastTouchTime = System.currentTimeMillis();
    private long userReportTimeOut = 10000;

    private Thread senderThread = null;

    private void popAndSendLog() {
        String log = "";
        if (logCache.length() != 0) {
            //read cache
            synchronized (UserLog.class) {
                log = logCache.toString();
                //clear cache
                logCache.setLength(0);
            }
        }
        sendLog(log);
    }

    public void start() {
        if (senderThread == null) {
            //thread may be terminated
            reportSeqNumber = 0;

            senderThread = new SenderThread();
        }
        Timber.d(String.valueOf(senderThread.getState()));

        if (senderThread.getState() == Thread.State.NEW) {
            senderThread.start();
        }
    }

    public void stop() {
        if (senderThread != null) {
            senderThread.interrupt();
        }
        senderThread = null;
    }

    public void addUserLog(View view) {
        addUserLog(getSimpleResourceName(view) + " clicked");
    }

    public void addUserLog(String message) {
        synchronized (UserLog.class) {
            Timber.d("addUserLog: getLock");
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            logCache.append("\n@")
                    .append(time)
                    .append(": ")
                    .append(message);
            lastTouchTime = System.currentTimeMillis();
        }
    }

    public String getSimpleResourceName(@NonNull View view) {
        String result = "";
        if (-1 == view.getId()) return result;

        try {
            result = view.getResources().getResourceName(view.getId());
            result = result.substring(result.indexOf("/") + 1);
        } catch (Resources.NotFoundException ignored) {
        }
        return result;
    }

    private  void sendLog(String log) {
        Timber.d("sendLog: " + log);

        if (reportRepository != null) {
            reportRepository.sendUserReport(reportSeqNumber,
                    System.currentTimeMillis(),
                    lastTouchTime,
                    log
            );
            reportSeqNumber++;
        }
    }

    public void setUserReportTimeOut(long userReportTimeOut) {
        this.userReportTimeOut = userReportTimeOut;
    }

    class SenderThread extends Thread {

        @Override
        public void run() {
            while (!isInterrupted()) {
                Timber.d("Check cache");
                popAndSendLog();

                try {
                    Thread.sleep(userReportTimeOut);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
