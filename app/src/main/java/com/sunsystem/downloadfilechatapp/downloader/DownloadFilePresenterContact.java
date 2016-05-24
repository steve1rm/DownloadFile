package com.sunsystem.downloadfilechatapp.downloader;

/**
 * Created by steve on 5/18/16.
 */
public interface DownloadFilePresenterContact {
    /*
     * Presenter operations
     */

    /* Presenter <<- View */
        void downloadFile();
        void setView(DownloadFileContact view);

    /*
     * Presenter events
     */

    /* Model ->> Presenter */
        void onDownloadFileFailure();
        void onDownloadFileSuccess();
}
