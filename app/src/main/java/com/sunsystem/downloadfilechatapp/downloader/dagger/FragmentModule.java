package com.sunsystem.downloadfilechatapp.downloader.dagger;

import android.app.Fragment;

import com.sunsystem.downloadfilechatapp.downloader.DownloadFileView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by steve on 5/28/16.
 */
@Module
public class FragmentModule {
    Fragment mFragment;

    FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    Fragment DownloadFileView() {
        return (DownloadFileView)mFragment;
    }
}
