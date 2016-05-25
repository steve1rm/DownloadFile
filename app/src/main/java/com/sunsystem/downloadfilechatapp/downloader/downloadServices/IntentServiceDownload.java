package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

/**
 * Created by steve on 5/23/16.
 */
public class IntentServiceDownload extends IntentService {
    private static final String TAG = IntentServiceDownload.class.getSimpleName();

    public IntentServiceDownload() {
        super(IntentServiceDownload.class.getSimpleName());
        Log.d(TAG, "IntentServiceDownload");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String mUrl = intent.getStringExtra("URL_DATA_KEY");
        Log.d(TAG, "onHandleIntent: " + mUrl);

        if(mUrl.isEmpty()) {
            return;
        }

        final Bitmap mBitmap = DownloadUtils.downloadAndDecodeImage(mUrl);

        if(mBitmap != null) {
            Log.d(TAG, "Download Completed: " + mBitmap.getByteCount());
        }
        else {
            Log.w(TAG, "bitmap is null - failed to download");
        }
    }
}
