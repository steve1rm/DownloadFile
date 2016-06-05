package com.sunsystem.downloadfilechatapp.downloader;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.R;

import com.sunsystem.downloadfilechatapp.downloader.adapters.DownloadFileAdapter;
import com.sunsystem.downloadfilechatapp.downloader.utils.RetainedFragmentManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CLASS = TAG;
    private static final String SAVEINSTANCESTATE_KEY = MainActivity.class.getSimpleName() + "_key";
    public static final String FILEUPLOADADAPTER_KEY = "fileUploadAdapter_key";

    /* Get a new instance of the retainedFragmentManager -
       handles state when the activity is destroyed and re-created */
    private RetainedFragmentManager mRetainedFragmentManager =
            RetainedFragmentManager.getNewInstance(getFragmentManager(), TAG_CLASS);

   // private DownloadFileAdapter mDownloadFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

  //      configurationChanged(savedInstanceState);

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, DownloadFileView.getNewInstance(), "DownloadFileView")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

/*
        if(mDownloadFileAdapter != null) {
            outState.putParcelable(SAVEINSTANCESTATE_KEY, mDownloadFileAdapter);
        }
*/
    }
}
