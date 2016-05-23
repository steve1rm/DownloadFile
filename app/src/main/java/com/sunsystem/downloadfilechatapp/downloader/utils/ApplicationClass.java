package com.sunsystem.downloadfilechatapp.downloader.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by steve on 5/23/16.
 */
public class ApplicationClass extends Application {
    private static final String TAG = ApplicationClass.class.getSimpleName();

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mContext = getApplicationContext();
    }
}
