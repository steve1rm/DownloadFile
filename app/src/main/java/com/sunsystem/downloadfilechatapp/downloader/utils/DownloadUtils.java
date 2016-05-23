package com.sunsystem.downloadfilechatapp.downloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by steve on 5/23/16.
 * final to prevent subclasses
 */
final public class DownloadUtils {
    private static final String TAG = DownloadUtils.class.getSimpleName();

    /* Utilities always have private constructors to prevent creating an object */
    private DownloadUtils() {
    }

    /**
     * Download an image file from the URL provided by the user and
     * decode into a Bitmap.
     *
     * @param url The url where a bitmap image is located
     *
     * @return the image bitmap or null if there was an error
     */
    public static Bitmap downloadAndDecodeImage(String url) {
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try {
            // Connect to a remote server, download the contents of
            // the image, and provide access to it via an Input
            // Stream.
            inputStream = (InputStream)new URL(url).getContent();

            // Decode an InputStream into a Bitmap, checks for null inputstream.
            Log.d(TAG, "Downloading image");
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d(TAG, "Download completed");
        }
        catch(IOException e) {
            Log.e(TAG, "Error downloading file: " + e.getMessage());
        }
        finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            }
            catch(IOException e) {
                Log.w(TAG, "Failed to close inputstream may produce memory leak: " + e.getMessage());
            }
        }

        return bitmap;
    }

    /* Helpers */
    public static String isValidUrl(String url) {
        String message = "";

        if(!url.isEmpty()) {
            try {
                new URI(url);
            }
            catch(URISyntaxException e) {
                message = e.getReason();
            }
        }
        else {
            message = "No url has been entered";
        }

        return message;
    }
}
