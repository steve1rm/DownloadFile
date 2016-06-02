package com.sunsystem.downloadfilechatapp.downloader;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunsystem.downloadfilechatapp.R;

import com.sunsystem.downloadfilechatapp.downloader.utils.RetainedFragmentManager;

public class MainActivity extends AppCompatActivity implements RetainedFragmentManager.handleConfigurationChanges {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG_CLASS = TAG;

    /* Get a new instance of the retainedFragmentManager -
       handles state when the activity is destroyed and re-created */

    private RetainedFragmentManager mRetainedFragmentManager =
            RetainedFragmentManager.getNewInstance(getFragmentManager(), TAG_CLASS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configurationChanged(savedInstanceState);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void configurationChanged(Bundle savedInstanceState) {
        if(mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "mRetainedFragmentManager.firstTimeIn()");

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new DownloadFileView(), "DOWNLOADFILEVIEW")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        }
        else {
            Log.d(TAG, "mRetainedFragmentManager subsequent times");
        }
    }
}
