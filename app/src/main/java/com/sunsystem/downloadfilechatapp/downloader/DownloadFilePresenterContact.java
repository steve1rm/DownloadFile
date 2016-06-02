package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 5/18/16.
 */
public interface DownloadFilePresenterContact {
    /*
     * Presenter operations
     */

    /* Presenter <<- View */
    void downloadFile();
    void setView(DownloadFileView view);

    /*
     * Presenter events
     */

    /* Model ->> Presenter */
    void onDownloadFileFailure(DownloadFile downloadFile, String errMessage);
    void onDownloadFileSuccess(DownloadFile downloadFile);
}
