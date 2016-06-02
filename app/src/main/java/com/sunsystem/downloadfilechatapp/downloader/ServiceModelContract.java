package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

/**
 * Created by steve on 5/19/16.
 */
public interface ServiceModelContract {
    void startServiceDownload(DownloadFile downloadFile, DownloadFilePresenterImp.DownloadFileResultReceiver resultReceiver);
}
