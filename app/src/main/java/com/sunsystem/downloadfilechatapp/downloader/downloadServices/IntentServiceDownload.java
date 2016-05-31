package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
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
            Log.d(TAG, "There is no url");
            return;
        }

        final String filepath = DownloadUtils.downloadRequestedFile(IntentServiceDownload.this, mUrl);

        if(!filepath.isEmpty()) {
            Log.d(TAG, "Download Completed: " + filepath);
            ResultReceiver resultReceiver = intent.getParcelableExtra("RESULT_RECEIVER");
            final Bundle bundle = new Bundle();
            bundle.putString("FILEPATH", filepath);
            resultReceiver.send(1, bundle);
        }
        else {
            Log.w(TAG, "filepath is empty - failed to download");
        }
    }
}
