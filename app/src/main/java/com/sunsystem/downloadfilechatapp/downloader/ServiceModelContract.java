package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 5/19/16.
 */
public interface ServiceModelContract {
    /* Model <<- Presenter */
    void startServiceDownload(DownloadFile downloadFile);

    /* Model ->> Presenter */
    void onStartServiceDownloadSuccess(DownloadFile downloadFile);
    void onStartServiceDownloadFailed(DownloadFile downloadFile, String errMessage);
}
