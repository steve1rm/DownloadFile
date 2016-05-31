package com.sunsystem.downloadfilechatapp.downloader;

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
    private DownloadFileView.DownloadFileResultReceiver mDownloadResultReceiver;

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

            mServiceModelContract.startServiceDownload(url, mDownloadResultReceiver);
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

    @Override
    public void setResultReceiver(DownloadFileView.DownloadFileResultReceiver resultReceiver) {
        mDownloadResultReceiver = resultReceiver;
    }

    /*
         * Presenter ->> View */
    @Override
    public void onDownloadFileFailure() {
        mDownloadFileContract.onDownloadFailed("Failed to download file");
    }

    @Override
    public void onDownloadFileSuccess() {
        mDownloadFileContract.onDownloadSuccess();
    }


}
