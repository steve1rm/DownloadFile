package com.sunsystem.downloadfilechatapp.downloader.dagger;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFileView;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by steve on 5/24/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(DownloadFileView target);
}
