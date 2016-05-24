package com.sunsystem.downloadfilechatapp.downloader;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sunsystem.downloadfilechatapp.R;
import com.sunsystem.downloadfilechatapp.downloader.dagger.DaggerInjector;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFileView extends Fragment implements DownloadFileContact {
    private static final String TAG = DownloadFileView.class.getSimpleName();

    @BindView(R.id.etDownloadFile) EditText mEtDownloadFile;
    @BindView(R.id.pbDownloadFile) ProgressBar mPbDownloadFile;

    @Inject DownloadFilePresenterImp mDownloadFilePresenterImp;

    public DownloadFileView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.download_file_view, container, false);

        ButterKnife.bind(DownloadFileView.this, view);

        /* Initialize presenter */
        DaggerInjector.getAppComponent().inject(DownloadFileView.this);

        if(mDownloadFilePresenterImp == null) {
            Log.e(TAG, "presenter is null - didn't work");
        }
        else {
            Log.d(TAG, "presenter is good - we did it");
        }

        return view;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnDownloadFile)
    public void startDownload() {
        Log.d(TAG, "startDownload");
        mDownloadFilePresenterImp.downloadFile();
    }

    @Override
    public String getUrl() {
       return mEtDownloadFile.getText().toString();
    }

    @Override
    public void onDownloadFailed(String errMessage) {
        Toast.makeText(getActivity(), "Download failed: " + errMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDownloadSuccess() {
        Toast.makeText(getActivity(), "Download Success: ", Toast.LENGTH_LONG).show();
    }
}
