package com.sunsystem.downloadfilechatapp.downloader;

import android.content.Context;
import android.content.Loader;

/**
 * Created by steve on 6/6/16.
 */
public class PresenterLoader<T extends DownloadFilePresenterImp> extends Loader<T> {
    private final PresenterFactory<T> factory;
    private T presenter;

    public PresenterLoader(Context context) {
        super(context);
        factory = null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(presenter != null) {
            deliverResult(presenter);
        }
        else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        presenter = factory.create();

        deliverResult(presenter);
    }

    @Override
    protected void onReset() {
        super.onReset();
        presenter.onDestroyed();
        presenter = null;
    }
}
