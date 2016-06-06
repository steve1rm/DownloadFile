package com.sunsystem.downloadfilechatapp.downloader;

/**
 * Created by steve on 6/6/16.
 */
public interface PresenterFactory<T extends DownloadFilePresenterImp> {
    T create();
}
