package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterContact;
import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelContract;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/24/16.
 */
@Module
public class AppModule {
/*
    @Provides
    public ServiceModelImp provideServiceModelImp(DownloadFilePresenterContact downloadFilePresenterContact) {
        return new ServiceModelImp(downloadFilePresenterContact);
    }
*/

    @Provides
    public DownloadFilePresenterImp provideDownloadfilePresenterImp(ServiceModelContract serviceModelContract) {
        return new DownloadFilePresenterImp(serviceModelContract);
    }
}
