package com.sunsystem.downloadfilechatapp.downloader;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterContact {
    /*
     * Presenter operations
     */

    /* Presenter <<- View */
    public interface DownloadFilePresenterOps {
        void downloadFile();
    }

    /*
     * Presenter events
     */

    /* Model ->> Presenter */
    public interface DownloadFilePresenterEvents {
        void onDownloadFileFailure();
        void onDownloadFileSuccess();
    }
}
