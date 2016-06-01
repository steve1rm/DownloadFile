package com.sunsystem.downloadfilechatapp.downloader;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.sunsystem.downloadfilechatapp.R;
import com.sunsystem.downloadfilechatapp.downloader.adapters.DownloadFileAdapter;
import com.sunsystem.downloadfilechatapp.downloader.dagger.DaggerInjector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFileView extends Fragment implements DownloadFileContact {
    private static final String TAG = DownloadFileView.class.getSimpleName();
    private List<String> mFileNameList = Collections.emptyList();

    @BindView(R.id.etDownloadFile) EditText mEtDownloadFile;
    @BindView(R.id.rvDownloadFiles) RecyclerView mRvDownloadFiles;

    @Inject DownloadFilePresenterImp mDownloadFilePresenterImp;

    public DownloadFileView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.download_file_view, container, false);

        ButterKnife.bind(DownloadFileView.this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Setup recyclerview */
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvDownloadFiles.setLayoutManager(linearLayoutManager);
        mFileNameList = new ArrayList<>();
        mRvDownloadFiles.setAdapter(new DownloadFileAdapter(getActivity(), mFileNameList));

        /* Initialize presenter */
        DaggerInjector.getAppComponent().inject(DownloadFileView.this);

        if(mDownloadFilePresenterImp != null) {
            Log.d(TAG, "presenter is good - we did it");
            /* Use a setter property to inject the view */
            mDownloadFilePresenterImp.setView(DownloadFileView.this);
        }
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
    public void onDownloadSuccess(String filename) {
        Toast.makeText(getActivity(), "Download Success: " + filename, Toast.LENGTH_LONG).show();
        openDownloadedFile(filename);
    }

    private void openDownloadedFile(final String filename) {
        Log.d(TAG, "" + getActivity().getApplication().getPackageName());
        String packageName = getActivity().getApplication().getPackageName();
        if(packageName.contains(".debug")) {
            /* Remove the .debug part */
            packageName = packageName.replace(".debug", "");
        }

        Uri uri = Uri.parse("content://" + packageName + "/" + filename);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);

    }
}
