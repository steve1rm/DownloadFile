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

            mServiceModelContract.startServiceDownload(url);
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
        mDownloadFileContract.onDownloadSuccess();
    }


}
