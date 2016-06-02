package com.sunsystem.downloadfilechatapp.downloader;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.R;

import com.sunsystem.downloadfilechatapp.downloader.adapters.DownloadFileAdapter;
import com.sunsystem.downloadfilechatapp.downloader.utils.RetainedFragmentManager;

public class MainActivity extends AppCompatActivity implements RetainedFragmentManager.handleConfigurationChanges {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CLASS = TAG;
    private static final String SAVEINSTANCESTATE_KEY = MainActivity.class.getSimpleName() + "_key";
    public static final String FILEUPLOADADAPTER_KEY = "fileUploadAdapter_key";

    /* Get a new instance of the retainedFragmentManager -
       handles state when the activity is destroyed and re-created */
    private RetainedFragmentManager mRetainedFragmentManager =
            RetainedFragmentManager.getNewInstance(getFragmentManager(), TAG_CLASS);

    private DownloadFileAdapter mDownloadFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurationChanged(savedInstanceState);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, DownloadFileView.getNewInstance(mDownloadFileAdapter), "DownloadFileView")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mDownloadFileAdapter != null) {
            outState.putParcelable(SAVEINSTANCESTATE_KEY, mDownloadFileAdapter);
        }
    }

    @Override
    public void configurationChanged(final Bundle savedInstanceState) {
        if(mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "mRetainedFragmentManager.firstTimeIn()");

            mDownloadFileAdapter = new DownloadFileAdapter();

            /* Store the class object */
            mRetainedFragmentManager.put(FILEUPLOADADAPTER_KEY, mDownloadFileAdapter);
        }
        else {
            Log.d(TAG, "mRetainedFragmentManager subsequent times");
            mDownloadFileAdapter = mRetainedFragmentManager.get(FILEUPLOADADAPTER_KEY);

            if(mDownloadFileAdapter == null) {
                mDownloadFileAdapter = savedInstanceState.getParcelable(SAVEINSTANCESTATE_KEY);
                if(mDownloadFileAdapter == null) {
                    mDownloadFileAdapter = new DownloadFileAdapter();
                }
                else {
                    Log.d(TAG, "Get item count: " + mDownloadFileAdapter.getItemCount());
                }
            }
            else {
                mDownloadFileAdapter = new DownloadFileAdapter();
            }

            /* Store the class object */
            mRetainedFragmentManager.put(FILEUPLOADADAPTER_KEY, mDownloadFileAdapter);
        }
    }
}
