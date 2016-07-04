package com.sunsystem.downloadfilechatapp.downloader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.downloadServices.IntentServiceDownload;
import com.sunsystem.downloadfilechatapp.downloader.utils.ApplicationClass;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadResultReceiver;

import static com.sunsystem.downloadfilechatapp.downloader.utils.DownloadResultReceiver.RESULT_RECEIVER;

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

    @Override
    public void onStartServiceDownloadFailed(DownloadFile downloadFile, String errMessage) {
        mDownloadFilePresenterContract.onDownloadFileFailure(downloadFile, errMessage);
    }

    @Override
    public void onStartServiceDownloadSuccess(DownloadFile downloadFile) {
        mDownloadFilePresenterContract.onDownloadFileSuccess(downloadFile);
    }

    /* Model <<- Presenter */
    @Override
    public void startServiceDownload(DownloadFile downloadFile) {
        Log.d(TAG, "startServiceDownload: " + downloadFile.getUrl());

        final DownloadResultReceiver downloadResultReceiver = new DownloadResultReceiver(new Handler(), ServiceModelImp.this);

        Intent intent = new Intent(ApplicationClass.mContext, IntentServiceDownload.class);
        intent.putExtra(RESULT_RECEIVER, downloadResultReceiver);

        Bundle bundle = new Bundle();
        bundle.putParcelable(URL_DATA_KEY, downloadFile);
        intent.putExtra(URL_DATA_KEY, bundle);

        ApplicationClass.mContext.startService(intent);
    }
}
