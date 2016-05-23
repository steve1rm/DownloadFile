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
    private Bitmap mBitmap;
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
                mBitmap = DownloadUtils.downloadAndDecodeImage(mUrl);

                if(mBitmap != null) {
                    Log.d(TAG, "bitmap not null: " + startId);
                }
                else {
                    Log.w(TAG, "bitmap is null: " + startId);
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
