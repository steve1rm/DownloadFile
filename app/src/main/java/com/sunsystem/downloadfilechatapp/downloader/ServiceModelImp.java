package com.sunsystem.downloadfilechatapp.downloader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.downloadServices.IntentServiceDownload;
import com.sunsystem.downloadfilechatapp.downloader.utils.ApplicationClass;

/**
 * Created by steve on 5/19/16.
 */
public class ServiceModelImp implements ServiceModelContract {
    public static final String URL_DATA_KEY = "url_data_key";
    private static final String TAG = ServiceModelImp.class.getSimpleName();

    private DownloadFilePresenterContact mDownloadFilePresenterContract;

    public ServiceModelImp(DownloadFilePresenterContact downloadFilePresenterContact) {
        mDownloadFilePresenterContract = downloadFilePresenterContact;
    }

    /* Model <<- Presenter */
    @Override
    public void startServiceDownload(DownloadFile downloadFile, DownloadFilePresenterImp.DownloadFileResultReceiver resultReceiver) {

        Log.d(TAG, "startServiceDownload: " + downloadFile.getUrl());

        Intent intent = new Intent(ApplicationClass.mContext, IntentServiceDownload.class);
        intent.putExtra(DownloadFilePresenterImp.DownloadFileResultReceiver.RESULT_RECEIVER, resultReceiver);

        Bundle bundle = new Bundle();
        bundle.putParcelable(URL_DATA_KEY, downloadFile);
        intent.putExtra(URL_DATA_KEY, bundle);

        ApplicationClass.mContext.startService(intent);
    }
}
