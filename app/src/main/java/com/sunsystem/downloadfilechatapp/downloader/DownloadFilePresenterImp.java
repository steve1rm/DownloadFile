package com.sunsystem.downloadfilechatapp.downloader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.dagger.DaggerInjector;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import javax.inject.Inject;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterImp implements DownloadFilePresenterContact {
    private static final String TAG = DownloadFilePresenterImp.class.getSimpleName();

    private DownloadFileContact mDownloadFileContract;
    // private ServiceModelContract mServiceModelContract;

    @Inject ServiceModelContract mServiceModelContract;

    public DownloadFilePresenterImp() {
        mServiceModelContract = new ServiceModelImp(DownloadFilePresenterImp.this);
        // DaggerInjector.getAppComponent().inject(DownloadFilePresenterImp.this);
    }

    /**
     * Presenter <<- view */
    @Override
    public void downloadFile() {
        String errMessage;

        /* Get the url */
        String url = mDownloadFileContract.getUrl();
        errMessage = DownloadUtils.isValidUrl(url);

        if(errMessage.isEmpty()) {
            DownloadUtils.getFileExtension(url);

            mServiceModelContract.startServiceDownload(url, new DownloadFileResultReceiver(new Handler()));
        }
        else {
            /* continue to process download */
            mDownloadFileContract.onDownloadFailed(errMessage);
        }
    }

    @Override
    public void setView(DownloadFileView downloadFileView) {
        mDownloadFileContract = downloadFileView;
    }

    /*
         * Presenter ->> View */
    @Override
    public void onDownloadFileFailure() {
        mDownloadFileContract.onDownloadFailed("Failed to download file");
    }

    @Override
    public void onDownloadFileSuccess() {
        mDownloadFileContract.onDownloadSuccess("");
    }

    @SuppressLint("ParcelCreator")
    public class DownloadFileResultReceiver extends ResultReceiver {
        public static final int RESULT_CODE_OK = 0;
        public static final int RESULT_CODE_FAIL = -1;

        public static final String RESULT_DATA = "result_data_key";
        public static final String RESULT_RECEIVER = "result_receiver_key";

        public DownloadFileResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.d(TAG, "onReceiveResult resultCode: " + resultCode);

            if(resultCode == RESULT_CODE_OK) {
                final String filePath = resultData.getString(RESULT_DATA);
                if(filePath != null) {
                    Log.d(TAG, "onReceiveResult: " + filePath);
                    mDownloadFileContract.onDownloadSuccess(filePath);
                }
                else {
                    mDownloadFileContract.onDownloadFailed("bundle contains no data");
                }
            }
            else if(resultCode == RESULT_CODE_FAIL){
                mDownloadFileContract.onDownloadFailed("Failed to return correct resultcode");
            }
        }
    }

}
