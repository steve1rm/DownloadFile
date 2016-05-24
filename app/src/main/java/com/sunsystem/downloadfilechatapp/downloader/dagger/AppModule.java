package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFilePresenterImp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/24/16.
 */
@Module
public class AppModule {
    @Provides
    public DownloadFilePresenterImp provideDownloadfilePresenterImp() {
        return new DownloadFilePresenterImp();
    }
}
