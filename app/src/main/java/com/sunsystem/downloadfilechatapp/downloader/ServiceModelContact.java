package com.sunsystem.downloadfilechatapp.downloader;

/**
 * Created by steve on 5/19/16.
 */
public class ServiceModelContact {
    interface ServiceModelOps {
        void startServiceDownload();
    }

    interface ServiceModelEvents {
        void onFileDownloadFailure();
        void onFileDownloadSuccess();
    }
}
