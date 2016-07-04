package com.sunsystem.downloadfilechatapp.downloader.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelContract;
import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 7/4/16.
 */

@SuppressLint("ParcelCreator")
public class DownloadResultReceiver extends ResultReceiver {
    private static final String TAG = DownloadResultReceiver.class.getSimpleName();

    public static final int RESULT_CODE_OK = 0;
    public static final int RESULT_CODE_FAIL = -1;
    public static final String RESULT_DATA = "result_data_key";
    public static final String RESULT_RECEIVER = "result_receiver_key";

    private ServiceModelContract mServiceModelContract;

    public DownloadResultReceiver(Handler handler, ServiceModelContract serviceModelContract) {
        super(handler);
        mServiceModelContract = serviceModelContract;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);

        Log.d(TAG, "onReceiveResult resultCode: " + resultCode);
        final DownloadFile downloadFile = resultData.getParcelable(RESULT_DATA);

        if(resultCode == RESULT_CODE_OK) {
            if(downloadFile != null) {
                Log.d(TAG, "onReceiveResult: " + downloadFile.getmFilepath());
                mServiceModelContract.onStartServiceDownloadSuccess(downloadFile);
            }
            else {
                mServiceModelContract.onStartServiceDownloadFailed(null, "bundle contains no data");
            }
        }
        else if(resultCode == RESULT_CODE_FAIL){
            String errMessage = resultData.getString(RESULT_DATA);
            mServiceModelContract.onStartServiceDownloadFailed(downloadFile, errMessage);
        }
    }
}
