package com.example.stephenlau.testlibrary.userLog;

import android.util.Log;

/**
 * logCache 一个线程读
 *
 */
public class UserLog {

    private static final Object monitor = new Object();
    private static StringBuilder logCache = new StringBuilder();
    private static boolean isPause = false;

    private static Thread senderThread = new Thread() {
        @Override
        public void run() {

            while (!isInterrupted()) {
                Log.d("UserLog", "Check cache");
                if (logCache.length() != 0) {
                    synchronized (monitor) {
                        //send log
                        Log.d("UserLog", logCache.toString());
                        Log.d("UserLog", senderThread.getName());
                        logCache.setLength(0);
                    }
                }
                if (isPause) {
                    try {
                        synchronized (monitor) {
                            monitor.wait();
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    break;
                }

            }

        }
    };

    public static void startOrContinue() {
        Log.d("UserLog", String.valueOf(senderThread.getState()));

        if (senderThread.getState() == Thread.State.NEW)
            senderThread.start();

        if (senderThread.getState() == Thread.State.WAITING)
            continueTask();
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
    }

    public static void addUserLog(String message) {
        synchronized (monitor) {
            logCache.append(message);
            logCache.append("\n");
        }
    }
}
