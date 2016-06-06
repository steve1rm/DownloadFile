package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/24/16.
 */
@Module
public class AppModule {
    @Provides @Singleton
    public DownloadFilePresenterImp provideDownloadfilePresenterImp() {
        return new DownloadFilePresenterImp();
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

