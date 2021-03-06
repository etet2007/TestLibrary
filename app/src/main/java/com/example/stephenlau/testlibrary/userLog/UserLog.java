package com.example.stephenlau.testlibrary.userLog;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;
import timber.log.Timber;

/**
 * 一个生产者消费者，多线程通信的例程。
 */
public class UserLog {
    public static ReportRepository reportRepository;

    private static StringBuilder logCache = new StringBuilder();
    private static int reportSeqNumber = 0;
    private static long lastTouchTime = System.currentTimeMillis();
    private static long userReportTimeOut = 10000;

    private static Thread senderThread = null;

    private static void popAndSendLog() {
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

    public static void start() {
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

    public static void stop() {
        if (senderThread != null) {
            senderThread.interrupt();
        }
        senderThread = null;
    }

    public static void addUserLog(View view) {
        addUserLog(getSimpleResourceName(view) + " clicked");
    }

    public static void addUserLog(String message) {
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

    public static String getSimpleResourceName(@NonNull View view) {
        String result = "";
        if (-1 == view.getId()) return result;

        try {
            result = view.getResources().getResourceName(view.getId());
            result = result.substring(result.indexOf("/") + 1);
        } catch (Resources.NotFoundException ignored) {
        }
        return result;
    }

    private static void sendLog(String log) {
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

    public static void setUserReportTimeOut(long userReportTimeOut) {
        UserLog.userReportTimeOut = userReportTimeOut;
    }

    static class SenderThread extends Thread {

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
