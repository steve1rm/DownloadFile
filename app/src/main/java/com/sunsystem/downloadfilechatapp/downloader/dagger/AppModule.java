package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterContact;
import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;
import com.sunsystem.downloadfilechatapp.downloader.ServiceModelContract;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/24/16.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    public DownloadFilePresenterContact.DownloadFilePresenterOps provideDownloadfilePresenterImp(ServiceModelContract serviceModelContact) {
        return new DownloadFilePresenterImp(serviceModelContact);
    }
}
