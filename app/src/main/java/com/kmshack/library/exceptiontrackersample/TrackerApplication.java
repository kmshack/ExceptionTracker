package com.kmshack.library.exceptiontrackersample;

import android.app.Application;

import com.kmshack.library.exceptiontracker.Tracker;

/**
 * Created by kmshack on 16. 4. 14..
 */
public class TrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Tracker.setup(getApplicationContext()).setAlarmType(Tracker.ALARM_TYPE_NOTIFICATION);
    }
}
