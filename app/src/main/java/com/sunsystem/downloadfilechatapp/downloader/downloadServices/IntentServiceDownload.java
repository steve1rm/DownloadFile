package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.ServiceModelImp;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import static com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_CODE_FAIL;
import static com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_CODE_OK;
import static com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_DATA;
import static com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_RECEIVER;

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

        /* ResultReciver that will send back message of success of failure */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(RESULT_RECEIVER);
        final Bundle fileBundle = new Bundle();

        if(mUrl.isEmpty()) {
            Log.d(TAG, "There is no url");
            fileBundle.putString(RESULT_DATA, "There is no url from intent - cannot start the download");
            resultReceiver.send(RESULT_CODE_FAIL, fileBundle);
        }
        else {
            Log.d(TAG, "onHandleIntent: " + mUrl);

            /* Start the downloading the file */
            final String filepath = DownloadUtils.downloadRequestedFile(IntentServiceDownload.this, mUrl);

            /* File path that is returned */
            if (!filepath.isEmpty()) {
                Log.d(TAG, "Download Completed: " + filepath + " fileName: " + DownloadUtils.getFilename(filepath));

                fileBundle.putString(RESULT_DATA, DownloadUtils.getFilename(filepath));
                resultReceiver.send(RESULT_CODE_OK, fileBundle);
            } else {
                Log.w(TAG, "filepath is empty - failed to download");
                fileBundle.putString(RESULT_DATA, "filepath is empty - failed to download");
                resultReceiver.send(RESULT_CODE_FAIL, fileBundle);
            }
        }
    }
}
