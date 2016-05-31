package com.sunsystem.downloadfilechatapp.downloader;

import android.content.Intent;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.downloadServices.IntentServiceDownload;
import com.sunsystem.downloadfilechatapp.downloader.utils.ApplicationClass;

/**
 * Created by steve on 5/19/16.
 */
public class ServiceModelImp implements ServiceModelContract {
    private static final String TAG = ServiceModelImp.class.getSimpleName();

    private DownloadFilePresenterContact mDownloadFilePresenterContract;

    public ServiceModelImp(DownloadFilePresenterContact downloadFilePresenterContact) {
        mDownloadFilePresenterContract = downloadFilePresenterContact;
    }

    /* Model <<- Presenter */
    @Override
    public void startServiceDownload(String url, DownloadFileView.DownloadFileResultReceiver resultReceiver) {
        Log.d(TAG, "startServiceDownload: " + url);

        Intent intent = new Intent(ApplicationClass.mContext, IntentServiceDownload.class);
        intent.putExtra("RESULT_RECEIVER", resultReceiver);
        intent.putExtra("URL_DATA_KEY", url);
        ApplicationClass.mContext.startService(intent);

        mDownloadFilePresenterContract.onDownloadFileSuccess();
    }
}
