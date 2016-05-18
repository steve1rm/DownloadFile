package com.sunsystem.downloadfilechatapp.downloader;

import android.text.TextUtils;
import android.webkit.URLUtil;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterImp implements DownloadFilePresenterContact.DownloadFilePresenterOps, DownloadFilePresenterContact.DownloadFilePresenterEvents {

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
            mDownloadFileView.onDownloadFailed(errMessage);
        }
        else {
            /* continue to process download */
            mDownloadFileView.onDownloadSuccess("Success");
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

        if(url.isEmpty()) {
            return "No url has been entered";
        }

        try {
            new URI(url);
        }
        catch(URISyntaxException e) {
            message = e.getMessage();
        }

        return message;
    }
}
