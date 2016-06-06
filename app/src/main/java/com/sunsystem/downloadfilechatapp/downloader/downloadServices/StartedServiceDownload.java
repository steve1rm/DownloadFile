package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;
/**
 * Created by steve on 5/23/16.
 */
public class StartedServiceDownload extends Service {
    private static final String TAG = StartedServiceDownload.class.getSimpleName();
    private String filepath;
    private String mUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {

        mUrl = intent.getStringExtra("URL_DATA_KEY");
        Log.d(TAG, "onStartCommand: " + mUrl + " startId: " + startId);

        if(mUrl.isEmpty()) {
            return START_NOT_STICKY;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                filepath = DownloadUtils.downloadRequestedFile(StartedServiceDownload.this, mUrl);

                if(filepath.isEmpty()) {
                    Log.w(TAG, "file path is empty: " + startId);
                }
                else {
                    Log.d(TAG, ": " + startId);
                }

                stopSelf();
            }
        }).start();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
