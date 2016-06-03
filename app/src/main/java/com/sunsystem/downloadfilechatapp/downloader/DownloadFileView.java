package com.sunsystem.downloadfilechatapp.downloader;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sunsystem.downloadfilechatapp.R;
import com.sunsystem.downloadfilechatapp.downloader.adapters.DownloadFileAdapter;
import com.sunsystem.downloadfilechatapp.downloader.dagger.DaggerInjector;
import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;
import com.sunsystem.downloadfilechatapp.downloader.utils.DownloadUtils;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFileView extends Fragment implements DownloadFileContact {
    private static final String TAG = DownloadFileView.class.getSimpleName();
    private DownloadFileAdapter mDownloadFileAdapter;

    @BindView(R.id.etDownloadFile) EditText mEtDownloadFile;
    @BindView(R.id.rvDownloadFiles) RecyclerView mRvDownloadFiles;
    @BindView(R.id.cbOpenfile) CheckBox mCbOpenfile;

    @Inject DownloadFilePresenterImp mDownloadFilePresenterImp;

    public DownloadFileView() {
        // Required empty public constructor
    }

    public static DownloadFileView getNewInstance(final DownloadFileAdapter downloadFileAdapter) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.FILEUPLOADADAPTER_KEY, downloadFileAdapter);

        DownloadFileView downloadFileView = new DownloadFileView();
        downloadFileView.setArguments(bundle);

        return downloadFileView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.download_file_view, container, false);

        ButterKnife.bind(DownloadFileView.this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");

        /* Setup recyclerview */
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRvDownloadFiles.setLayoutManager(linearLayoutManager);

        final Bundle bundle = getArguments();
        mDownloadFileAdapter = bundle.getParcelable(MainActivity.FILEUPLOADADAPTER_KEY);
        mRvDownloadFiles.setAdapter(mDownloadFileAdapter);

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
    public void onDownloadStarted(DownloadFile downloadFile) {
        /* Add the filename to the recycler list */
        int index = mDownloadFileAdapter.addFileName(downloadFile);
        Log.d(TAG, "onDownloadStarted addded: " + index);
    }

    @Override
    public void onDownloadFailed(DownloadFile downloadFile, String errMessage) {
        Toast.makeText(getActivity(), "Download failed: " + errMessage, Toast.LENGTH_LONG).show();
        /* Remove the file if it doesn't already exist */
        if(downloadFile != null) {
            int index = mDownloadFileAdapter.removeFileName(downloadFile);
            Log.d(TAG, "onDownloadFailed removed: " + index);
        }
    }

    @Override
    public void onDownloadSuccess(DownloadFile downloadFile) {
    //    Toast.makeText(getActivity(), "Download Success: " + downloadFile.getmFilepath(), Toast.LENGTH_SHORT).show();

        if(getActivity() != null) {
            Toast.makeText(getActivity(), "Download Success: " + downloadFile.getmFilepath(), Toast.LENGTH_SHORT).show();
        }

        /* Remove the file from the adapter as it has already finished */
        int index = mDownloadFileAdapter.removeFileName(downloadFile);
        Log.d(TAG, "onDownloadSuccess removed: " + index);

        if(mCbOpenfile.isChecked()) {
            openDownloadedFile(downloadFile.getmFilepath());
        }
    }

    /**
     * Open the file using the content provider as the files are stored on internal storage.
     * Only the app may access them. Because we are opening using an external app the content provider
     * will be able to share this file.
     * @param filename the filename that you want to open
     * @return nothing
     */
    private void openDownloadedFile(final String filename) {
        Log.d(TAG, "" + getActivity().getApplication().getPackageName());
        String packageName = getActivity().getApplication().getPackageName();

        /* Because we specified the .debug postfix in the build.gradle file */
        if(packageName.contains(".debug")) {
            /* Remove the .debug part */
            packageName = packageName.replace(".debug", "");
        }

        Uri uri = Uri.parse("content://" + packageName + "/" + filename);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach Activity");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }
}
