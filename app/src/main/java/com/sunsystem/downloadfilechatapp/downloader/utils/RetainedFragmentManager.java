package com.sunsystem.downloadfilechatapp.downloader.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by steve on 7/16/15.
 * Retains and manages state information between runtime configuration
 * changes to an Activity. 'Originator' memento design pattern.
 */
public class RetainedFragmentManager {
    /* Interface for handling configuration changes */
    public interface handleConfigurationChanges {
        void configurationChanged(Bundle savedInstanceState);
    }

    /**
     * Tag name of class for log statements
     */
    private static final String TAG = RetainedFragmentManager.class.getSimpleName();

    /**
     * TAG name used to identify the retainedFragment
     */
    private final String RETAINEDFRAGMENT_TAG;

    /**
     * WeakReference to the FragmentManager for to ensure garage collection
     */
    private final WeakReference<FragmentManager> mFragmentManager;

    /**
     * Reference to the RetainedFragment
     */
    private RetainedFragment mRetainedFragment;

    /**
     * Constructor that initializes class members
     */
    private RetainedFragmentManager(FragmentManager fragmentManager, String retainedFragmentTag) {
        Log.d(TAG, "RetainedFragmentManager");

        /* Store the weak reference to the fragmentManager */
        mFragmentManager = new WeakReference<>(fragmentManager);
        RETAINEDFRAGMENT_TAG = retainedFragmentTag;
    }

    /**
     * Factory method for returning a new instance of RetainedFragmentManager
     */
    public static RetainedFragmentManager getNewInstance(FragmentManager fragmentManager, String retainedFragmentTag) {
        return new RetainedFragmentManager(fragmentManager, retainedFragmentTag);
    }

    /**
     * Initializes the RetainedFragment the first time it's called
     * @return true if its teh first time the method's been called, else false
     */
    public boolean firstTimeIn() {
        boolean isFirstTimeIn;

        try {
            /* Find the RetainedFragment on Activity restarts. The RetainedFragment has no UI
               so has to be referenced via TAG name */
            mRetainedFragment = (RetainedFragment)mFragmentManager.get().findFragmentByTag(RETAINEDFRAGMENT_TAG);

            /* A value of null means it's the first time in, so need to do some initialization */
            if(mRetainedFragment == null) {
                Log.d(TAG, "firstTimeIn creating new RetainedFragment");

                /* Create a new RetainedFragment */
                mRetainedFragment = new RetainedFragment();

                /* Commit this RetainedFragment to the FragmentManager */
                mFragmentManager.get()
                        .beginTransaction()
                        .add(mRetainedFragment, RETAINEDFRAGMENT_TAG).commit();

                isFirstTimeIn = true;
            }
            else {
                /* A value of non-null means its not the first time */
                Log.d(TAG, "firstTimeIn Returning existing RetainedFragment");
                isFirstTimeIn = false;
            }
        }
        catch (NullPointerException e) {
            Log.e(TAG, "firstTimeIn: " + e.getMessage());
            isFirstTimeIn = false;
        }

        return isFirstTimeIn;
    }

    /**
     * Add the object with the key
     */
    public void put(String key, Object object) {
        mRetainedFragment.put(key, object);
    }

    /**
     * Add the object with its class name
     */
    public void put(Object object) {
        put(object.getClass().getName(), object);
    }

    /**
     *  Get the object with the key
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) mRetainedFragment.get(key);
    }

    /**
     * Return the Activity the RetainedFragment is attached to or null
     * if it's not currently attached
     */
    public Activity getAttachedActivity() {
        return mRetainedFragment.getActivity();
    }

    /**
     * 'Headless' (Fragment with no UI) Fragment that retains state information
     * between configuration changes. 'Memento' memento design pattern
     */
    public static class RetainedFragment extends Fragment {
        /**
         * Tag name of class for log statements
         */
        private static final String TAG = RetainedFragment.class.getSimpleName();

        /**
         * Maps keys to objects
         */
        private HashMap<String, Object> mData = new HashMap<>();

        /**
         * Hook method called when a new instance of Fragment is created.
         *
         * @param savedInstanceState object that contains saved state information
         */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            /* Ensure the data survives runtime configuration changes */
            setRetainInstance(true);

            if(savedInstanceState == null) {
                Log.d(TAG, "onCreate savedInstanceState == null");
             }
        }

        /**
         * Add the object with the key
         */
        public void put(String key, Object object) {
            mData.put(key, object);
        }

        /**
         * Add the object with its class name
         */
        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        /**
         * Get the object with the key
         */
        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) mData.get(key);
        }
    }
}
