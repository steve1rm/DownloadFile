package com.sunsystem.downloadfilechatapp.downloader;

import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by steve on 5/18/16.
 */
public class DownloadFilePresenterImp implements DownloadFilePresenterContact {
    private static final String TAG = DownloadFilePresenterImp.class.getSimpleName();

    private DownloadFileContact mDownloadFileContract;
    private ServiceModelContract mServiceModelContract;

  //  @Inject ServiceModelContract mServiceModelContract;

    public DownloadFilePresenterImp() {
        mServiceModelContract = new ServiceModelImp(DownloadFilePresenterImp.this);

        // DaggerInjector.getAppComponent().inject(DownloadFilePresenterImp.this);
    }

    public DownloadFilePresenterImp(ServiceModelContract serviceModelImp) {
        mServiceModelContract = serviceModelImp;
    }

    /**
     * Presenter <<- view */
    @Override
    public void downloadFile() {
        String errMessage;

        /* Get the url */
        String url = mDownloadFileContract.getUrl();
        errMessage = DownloadUtils.isValidUrl(url);

        if(errMessage.isEmpty()) {
            /* Create download file object */
            final DownloadFile downloadFile =
                    DownloadFile.getNewInstance(DownloadUtils.getFilename(url), UUID.randomUUID(), url);

            /* Send back the DownloadFile object so it can be added to the adapter so the user can see the progress */
            mDownloadFileContract.onDownloadStarted(downloadFile);

            /* Start the service to begin downloading */
            mServiceModelContract.startServiceDownload(downloadFile);
        }
        else {
            /* Cannot download file invalid url */
            mDownloadFileContract.onDownloadFailed(null, errMessage);
        }
    }

    @Override
    public void setView(DownloadFileView downloadFileView) {
        mDownloadFileContract = downloadFileView;
    }

    /*
     * Presenter ->> View */
    @Override
    public void onDownloadFileFailure(DownloadFile downloadFile, String errMessage) {
        mDownloadFileContract.onDownloadFailed(downloadFile, errMessage);
    }

    @Override
    public void onDownloadFileSuccess(DownloadFile downloadFile) {
        mDownloadFileContract.onDownloadSuccess(downloadFile);
    }


}
