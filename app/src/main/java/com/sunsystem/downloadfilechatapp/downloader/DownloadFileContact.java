package com.sunsystem.downloadfilechatapp.downloader;

/**
 * Created by steve on 5/18/16.
 */
public interface DownloadFileContact {
    void onDownloadFailed(String errMessage);
    void onDownloadSuccess(String filePath);

    String getUrl();
}
