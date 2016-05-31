package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelImp;
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
        final String mUrl = intent.getStringExtra(ServiceModelImp.URL_DATA_KEY);
        Log.d(TAG, "onHandleIntent: " + mUrl);

        if(mUrl.isEmpty()) {
            Log.d(TAG, "There is no url");
            return;
        }

        /* Start the downloading */
        final String filepath = DownloadUtils.downloadRequestedFile(IntentServiceDownload.this, mUrl);

        /* File path that is returned */
        if(!filepath.isEmpty()) {
            Log.d(TAG, "Download Completed: " + filepath + " fileName: " + DownloadUtils.getFilename(filepath));

            ResultReceiver resultReceiver = intent.getParcelableExtra(DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_RECEIVER);
            final Bundle bundle = new Bundle();
            bundle.putString(DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_DATA, DownloadUtils.getFilename(filepath));
            resultReceiver.send(DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_CODE, bundle);
        }
        else {
            Log.w(TAG, "filepath is empty - failed to download");
        }
    }
}
