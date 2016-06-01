package com.sunsystem.downloadfilechatapp.downloader.data;

import java.util.UUID;

/**
 * Created by steve on 6/1/16.
 */
public class DownloadFile {
    private String mFilename;
    private UUID mId;
    private String url;

    public DownloadFile(String mFilename, UUID mId, String url) {
        this.mFilename = mFilename;
        this.mId = mId;
        this.url = url;
    }

    public String getmFilename() {
        return mFilename;
    }

    public void setmFilename(String mFilename) {
        this.mFilename = mFilename;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
