package com.sunsystem.downloadfilechatapp.downloader.dagger;

/**
 * Created by steve on 5/24/16.
 */
public class DaggerInjector {
    private static AppComponent mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
