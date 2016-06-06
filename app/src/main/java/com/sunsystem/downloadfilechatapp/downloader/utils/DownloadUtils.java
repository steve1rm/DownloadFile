package com.sunsystem.downloadfilechatapp.downloader.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
    public static String downloadRequestedFile(Context context, String url) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            /* create the temporary file */
            final File file = getTemporaryFile(context, getFilename(url));

            /* Connect to a remote server, download the contents of the image, 
               and provide access to it via an Input Stream */
            inputStream = (InputStream)new URL(url).getContent();

        //    final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
       //     httpURLConnection.getContentLength()

            Log.d(TAG, "downloadRequestedFile: Download completed");

            /* Create new output stream with the temporary file location to copy the file to */
            outputStream = new FileOutputStream(file);

            /* Copy the contents of the downloaded image to the temp file */
            copy(inputStream, outputStream);
            
            Log.d(TAG, "file AbsolutePath: " + file.getAbsolutePath());
            
            /* Return the absolute path */
            return file.getAbsolutePath();
        }
        catch(FileNotFoundException ex) {
            Log.e(TAG, "Error downloading file: " + ex.getMessage() + " " + ex.getCause());
            return "";
        }
        catch(IOException ex) {
            Log.e(TAG, "Error downloading file: " + ex.getMessage() + " " + ex.getCause());
            return "";
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

            try {
                if(outputStream != null) {
                    outputStream.close();
                }
            }
            catch(IOException e) {
                Log.w(TAG, "Failed to close outputstream may produce memory leak: " + e.getMessage());
            }
        }
    }

    /*
     * Helpers functions
     */

    /**
     * Check that a valid url has been entered
     * @param url the url to download a file from
     * @return empty string for success or a error message for failure
     */
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

    /**
     * Determine the file extension
     * @param url the url to get the extension from
     * @return the file extension
     */
    public static FileExtensions getFileExtension(String url) {
        /* Get the extension */
        final int index = url.lastIndexOf('.');
        final String extension = url.substring(index + 1);
        Log.d(TAG, "extension: " + extension);

        if(extension.equalsIgnoreCase("jpg")) {
            return FileExtensions.JPG;
        }
        else if(extension.equalsIgnoreCase("png")) {
            return FileExtensions.PNG;
        }
        else if(extension.equalsIgnoreCase("mp3")) {
            return FileExtensions.MP3;
        }
        else if(extension.equalsIgnoreCase("mp4")) {
            return FileExtensions.MP4;
        }
        else if(extension.equalsIgnoreCase("pdf")) {
            return FileExtensions.PDF;
        }
        else {
            return FileExtensions.UNKNOWN;
        }
    }

    /**
     * The correct string that will be used for an app to open the file
     * @return the string representation of the file extension
     */
    public static String buildType(String filename) {
        String type;
        switch(getFileExtension(filename)) {
            case JPG:
            case GIF:
            case PNG:
                type = "image/*";
                break;

            case MP3:
                type = "audio/*";
                break;

            case MP4:
                type = "video/*";
                break;

            case PDF:
                type = "application/*";
                break;

            default:
                type = "";
                break;
        }

        return type;
    }

    /**
     * Return the complete file name of the download file
     * @param url the url to get the file name from
     * @return the file name extracted from the url or just return the url
     */
    public static String getFilename(String url) {
        final int index = url.lastIndexOf('/');
        if(index != -1) {
            final String filename = url.substring(index + 1);
            Log.d(TAG, "FileName: " + filename);
            return filename;
        }
        else {
            /* url doesn't contain any backslashes maybe just a normal file name */
            return url;
        }
    }

    /**
     * Enum of all the current file extension types
     */
    private enum FileExtensions {
        JPG,
        GIF,
        PNG,
        MP3,
        MP4,
        PDF,
        UNKNOWN
    }

    /**
     * Create a temp file to store the result of a download.
     *
     * @param context
     * @param url
     * @return
     * @throws IOException
     */
    private static File getTemporaryFile(final Context context, final String filename) throws IOException {
        /* get a unique temporary file name appended with current time in MS
         * so that the same file downloaded more than once will have their own unique names */
        final String timeMS = "_" + String.valueOf(System.currentTimeMillis());
        final int index = filename.lastIndexOf('.');

        StringBuilder sb = new StringBuilder(filename);
        sb.insert(index, timeMS);

        Log.d(TAG, "filename: " + sb.toString());

        return context.getFileStreamPath(sb.toString());
    }

    /**
     * Copy the contents of an InputStream into an OutputStream.
     *
     * @param in
     * @param out
     * @return
     * @throws IOException
     */
    public static int copy(final InputStream in, final OutputStream out) throws IOException {
        final int BUFFER_LENGTH = 4096;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        int totalRead = 0;
        int read;

        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
            totalRead += read;
        }

        return totalRead;
    }


}
