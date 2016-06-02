package com.sunsystem.downloadfilechatapp.downloader.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by steve on 6/1/16.
 */
public class DownloadFile implements Parcelable {
    private String mFilename;
    private UUID mId;
    private String url;
    private String mFilepath;

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

    public String getmFilepath() {
        return mFilepath;
    }

    public void setmFilepath(String mFilepath) {
        this.mFilepath = mFilepath;
    }

    protected DownloadFile(Parcel in) {
        mFilename = in.readString();
        mId = (UUID) in.readValue(UUID.class.getClassLoader());
        url = in.readString();
        mFilepath = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFilename);
        dest.writeValue(mId);
        dest.writeString(url);
        dest.writeString(mFilepath);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DownloadFile> CREATOR = new Parcelable.Creator<DownloadFile>() {
        @Override
        public DownloadFile createFromParcel(Parcel in) {
            return new DownloadFile(in);
        }

        @Override
        public DownloadFile[] newArray(int size) {
            return new DownloadFile[size];
        }
    };
}
