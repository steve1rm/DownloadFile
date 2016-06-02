package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 5/18/16.
 */
public interface DownloadFileContact {
    void onDownloadFailed(DownloadFile downloadFile, String errMessage);
    void onDownloadSuccess(DownloadFile downloadFile);
    void onDownloadStarted(DownloadFile downloadFile);
    String getUrl();
}
