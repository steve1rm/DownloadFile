package com.sunsystem.downloadfilechatapp.downloader.downloadServices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.ServiceModelImp;
import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
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
        final Bundle downloadFileBundle = intent.getBundleExtra(ServiceModelImp.URL_DATA_KEY);
        DownloadFile downloadFile = null;
        final String mUrl;
        /* ResultReciver that will send back message of success of failure */
        final ResultReceiver resultReceiver = intent.getParcelableExtra(RESULT_RECEIVER);
        final Bundle fileBundle = new Bundle();
        boolean hasDownloadSuccess = false;
        String downloadErrMessage = "";
        String filepath = "";

        /* Check that bundle contains the url */
        if(downloadFileBundle != null) {
            downloadFile = downloadFileBundle.getParcelable(ServiceModelImp.URL_DATA_KEY);
            if(downloadFile != null) {
                mUrl = downloadFile.getUrl();

                if(mUrl.isEmpty()) {
                    Log.d(TAG, "There is no url");
                    downloadErrMessage = "There is no url from intent - cannot start the download";
                    hasDownloadSuccess = false;
                }
                else {
                    Log.d(TAG, "onHandleIntent: " + mUrl);

                    /* Start the downloading the file */
                    filepath = DownloadUtils.downloadRequestedFile(IntentServiceDownload.this, mUrl);

                    /* File path that is returned successfully */
                    if (!filepath.isEmpty()) {
                        hasDownloadSuccess = true;
                    }
                    else {
                        Log.w(TAG, "filepath is empty - failed to download");
                        downloadErrMessage = "filepath is empty - failed to download";
                    }
                }
            }
        }
        else {
            hasDownloadSuccess = false;
            downloadErrMessage = "DownloadFile bundle is empty";
        }

        if(hasDownloadSuccess) {
            Log.d(TAG, "Download Completed: " + filepath + " fileName: " + DownloadUtils.getFilename(filepath));

            /* Update the DownloadFile object with the filepath of where the file is stored on the device */
            downloadFile.setmFilepath(DownloadUtils.getFilename(filepath));

            fileBundle.putParcelable(RESULT_DATA, downloadFile);
            resultReceiver.send(RESULT_CODE_OK, fileBundle);
        }
        else {
            Log.w(TAG, "Download Failed: " + downloadErrMessage);
            fileBundle.putParcelable(RESULT_DATA, downloadFile);
            /* Add an extra string for the error message */
   //         fileBundle.putString(RESULT_DATA, downloadErrMessage);
            resultReceiver.send(RESULT_CODE_FAIL, fileBundle);
        }
    }
}
