package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;
import com.sunsystem.downloadfilechatapp.downloader.DownloadFileView;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/24/16.
 */
@Module
public class AppModule {
    @Provides
    public DownloadFilePresenterImp provideDownloadfilePresenterImp(DownloadFileView downloadFileView) {
        return new DownloadFilePresenterImp(downloadFileView);
    }

/*
    @Provides
    public DownloadFilePresenterImp provideDownloadfilePresenterImp() {
        return new DownloadFilePresenterImp();
    }
*/

/*
    @Provides
    public ServiceModelImp provideServiceModelImp(DownloadFilePresenterImp downloadFilePresenterImp) {
        return new ServiceModelImp(downloadFilePresenterImp);
    }
*/


}

