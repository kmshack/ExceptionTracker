package com.kmshack.library.exceptiontracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by kmshack on 16. 4. 14..
 */
public class Tracker {

    public static final int ALARM_TYPE_NOTIFICATION = 1;
    public static final int ALARM_TYPE_ACTIVITY = 2;

    private static volatile Tracker instance;

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Thread.UncaughtExceptionHandler mHandlerListener;
    private Context mContext;

    private int mAlarmType = ALARM_TYPE_NOTIFICATION;

    private Tracker(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerApplication());
    }

    public static Tracker setup(Context context) {

        synchronized (Tracker.class) {
            if (instance == null) {
                instance = new Tracker(context);
            }
        }

        return instance;
    }


    public Tracker setAlarmType(int type) {
        mAlarmType = type;

        return instance;
    }

    public Tracker setExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        mHandlerListener = handler;

        return instance;
    }

    class UncaughtExceptionHandlerApplication implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

            String msg = getStackTrace(ex);

            StandardExceptionParser sp = new StandardExceptionParser(mContext, null);
            String description = sp.getDescription(Thread.currentThread().getName(), ex);

            alarm(description, msg);

            if (mHandlerListener != null)
                mHandlerListener.uncaughtException(thread, ex);

            mDefaultHandler.uncaughtException(thread, ex);
        }

        private String getStackTrace(Throwable th) {

            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);

            Throwable cause = th;
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            final String stacktraceAsString = result.toString();
            printWriter.close();

            return stacktraceAsString;
        }

        private void alarm(String desc, String log) {
            Intent intent = LogActivity.launch(mContext, desc, log);

            switch (mAlarmType) {
                case ALARM_TYPE_ACTIVITY:
                    mContext.startActivity(intent);
                    break;

                case ALARM_TYPE_NOTIFICATION:

                    PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

                    Notification noti = new NotificationCompat.Builder(mContext)
                            .setContentTitle(desc)
                            .setContentText(log).setStyle(new NotificationCompat.BigTextStyle().bigText(log))
                            .setSmallIcon(R.drawable.ic_developer_mode_white_48dp)
                            .setContentIntent(pIntent).build();

                    noti.flags |= Notification.FLAG_AUTO_CANCEL;

                    NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(100, noti);

                    break;
            }


        }
    }


}
