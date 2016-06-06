package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 5/18/16.
 */
public interface DownloadFilePresenterContact<V> {
    /*
     * Presenter operations
     */

    /* Presenter <<- View */
    void downloadFile();
    void attachView(V view);
    void detachView();
    void onDestroyed();

    /*
     * Presenter events
     */

    /* Model ->> Presenter */
    void onDownloadFileFailure(DownloadFile downloadFile, String errMessage);
    void onDownloadFileSuccess(DownloadFile downloadFile);
}
