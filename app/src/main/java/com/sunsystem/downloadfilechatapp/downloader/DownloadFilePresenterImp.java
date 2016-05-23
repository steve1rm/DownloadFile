package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterImp implements DownloadFilePresenterContact.DownloadFilePresenterOps, DownloadFilePresenterContact.DownloadFilePresenterEvents {
    private static final String TAG = DownloadFilePresenterImp.class.getSimpleName();

    private DownloadFileView mDownloadFileView;
    private ServiceModelImp mServiceModelImp;

    public DownloadFilePresenterImp(DownloadFileView downloadFileView) {
        mDownloadFileView = downloadFileView;
        mServiceModelImp = new ServiceModelImp(DownloadFilePresenterImp.this);
    }

    /*
     * Presenter <<- view */
    @Override
    public void downloadFile() {
        String errMessage;

        /* Get the url */
        String url = mDownloadFileView.getUrl();
        errMessage = DownloadUtils.isValidUrl(url);
        if(errMessage.isEmpty()) {
            mServiceModelImp.startServiceDownload(url);

        }
        else {
            /* continue to process download */
            mDownloadFileView.onDownloadFailed(errMessage);
        }
    }

    /*
     * Presenter ->> View */
    @Override
    public void onDownloadFileFailure() {

    }

    @Override
    public void onDownloadFileSuccess() {

    }


}
