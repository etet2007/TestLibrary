package com.atp.data;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserLog {
    private static final Object monitor = new Object();

//    public static ReportRepository reportRepository;

    private static boolean isPause = false;
    private static StringBuilder logCache = new StringBuilder();
    private static int reportSeqNumber = 0;
    private static long lastTouchTime;

    private static Thread senderThread = null;

    private static void popAndSendLog() {
        if (logCache.length() != 0) {
            //read cache
            String log;
            synchronized (monitor) {
                log = logCache.toString();
                //clear cache
                logCache.setLength(0);
            }
            //send log
            sendLog(log);
        } else {
            //pause when cache is empty
            pauseTask();
        }
    }

    public static void startOrContinue() {
        if (senderThread == null) {
            //thread may be terminated
            reportSeqNumber = 0;
            isPause = false;

            senderThread = new SenderThread();
        }

        if (senderThread.getState() == Thread.State.NEW) {
            senderThread.start();
        }

        //restart()
        if (senderThread.getState() == Thread.State.WAITING) {
            continueTask();
        }
    }

    private static void continueTask() {
        synchronized (monitor) {
            isPause = false;
            monitor.notifyAll();
        }
    }

    public static void pauseTask() {
        synchronized (monitor) {
            isPause = true;
        }
    }

    public static void stop() {
        senderThread.interrupt();
        senderThread = null;
    }

    public static void addUserLog(View view) {
        addUserLog(getSimpleResourceName(view) + " clicked");
    }

    public static void addUserLog(String message) {
        synchronized (monitor) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            logCache.append("\n@")
                    .append(time)
                    .append(": ")
                    .append(message);
            lastTouchTime = System.currentTimeMillis();

            continueTask();
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

//        if (reportRepository != null) {
//            reportRepository.sendUserReport(reportSeqNumber,
//                    System.currentTimeMillis(),
//                    lastTouchTime,
//                    log
//            );
//            reportSeqNumber++;
//        }
    }

    static class SenderThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                popAndSendLog();

                try {
                    //pause
                    if (isPause) {
                        synchronized (monitor) {
                            monitor.wait();
                        }
                    }
                    //sleep
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    popAndSendLog();
                    break;
                }
            }
        }
    }

}
