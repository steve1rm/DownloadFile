package com.sunsystem.downloadfilechatapp.downloader;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterImp implements DownloadFilePresenterContact.DownloadFilePresenterOps, DownloadFilePresenterContact.DownloadFilePresenterEvents {
    private static final String TAG = DownloadFilePresenterImp.class.getSimpleName();

    private DownloadFileView mDownloadFileView;
    public DownloadFilePresenterImp(DownloadFileView downloadFileView) {
        mDownloadFileView = downloadFileView;
    }

    /*
     * Presenter <<- view */
    @Override
    public void downloadFile() {
        String errMessage;
        boolean isSuccess = true;

        /* Get the url */
        String url = mDownloadFileView.getUrl();
        errMessage = isValidUrl(url);
        if(errMessage.isEmpty()) {
            mDownloadFileView.onDownloadSuccess("Success");
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

    /* Helpers */
    private String isValidUrl(String url) {
        String message = "";

        if(!url.isEmpty()) {
            try {
                new URI(url);
            }
            catch(URISyntaxException e) {
                message = e.getReason();
            }
        }
        else {
            message = "No url has been entered";
        }

        return message;
    }
}
