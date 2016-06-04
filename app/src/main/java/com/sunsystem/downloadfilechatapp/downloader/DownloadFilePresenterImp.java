package com.sunsystem.downloadfilechatapp.downloader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.dagger.DaggerInjector;
import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import java.util.UUID;

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
                    /* Create object */
            DownloadFile downloadFile =  new DownloadFile(
                    DownloadUtils.getFilename(url),
                    UUID.randomUUID(),
                    url);

            /* Send back the DownloadFile object so it can be added to the adapter */
            mDownloadFileContract.onDownloadStarted(downloadFile);

            /* Start the service to begin downloading */
            DownloadFileResultReceiver downloadFileResultReceiver = new DownloadFileResultReceiver(new Handler(), DownloadFilePresenterImp.this);
            mServiceModelContract.startServiceDownload(downloadFile, downloadFileResultReceiver);
        }
        else {
            /* continue to process download */
            mDownloadFileContract.onDownloadFailed(null, errMessage);
        }
    }

    @Override
    public void setView(DownloadFileView downloadFileView) {
        mDownloadFileContract = downloadFileView;
    }

    /*
     * Presenter ->> View */
    @Override
    public void onDownloadFileFailure(DownloadFile downloadFile, String errMessage) {
        mDownloadFileContract.onDownloadFailed(downloadFile, "Failed to download file");
    }

    @Override
    public void onDownloadFileSuccess(DownloadFile downloadFile) {
        mDownloadFileContract.onDownloadSuccess(downloadFile);
    }

    @SuppressLint("ParcelCreator")
    public class DownloadFileResultReceiver extends ResultReceiver {
        public static final int RESULT_CODE_OK = 0;
        public static final int RESULT_CODE_FAIL = -1;

        public static final String RESULT_DATA = "result_data_key";
        public static final String RESULT_RECEIVER = "result_receiver_key";

        private DownloadFilePresenterImp mDownloadFilePresenterImp;
        public DownloadFileResultReceiver(Handler handler, DownloadFilePresenterImp downloadFilePresenterImp) {
            super(handler);
            mDownloadFilePresenterImp = downloadFilePresenterImp;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            Log.d(TAG, "onReceiveResult resultCode: " + resultCode);
            final DownloadFile downloadFile = resultData.getParcelable(RESULT_DATA);

            if(resultCode == RESULT_CODE_OK) {
//                if(downloadFile != null) {
                    Log.d(TAG, "onReceiveResult: " + downloadFile.getmFilepath());
                    mDownloadFilePresenterImp.onDownloadFileSuccess(downloadFile);
/*
                }
                else {
                    mDownloadFilePresenterImp.onDownloadFileFailure(downloadFile, "bundle contains no data");
                }
*/
            }
            else if(resultCode == RESULT_CODE_FAIL){
                String errMessage = resultData.getString(RESULT_DATA);
                mDownloadFilePresenterImp.onDownloadFileFailure(downloadFile, errMessage);
            }
        }
    }

}
