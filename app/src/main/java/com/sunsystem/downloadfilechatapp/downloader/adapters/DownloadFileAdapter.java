package com.sunsystem.downloadfilechatapp.downloader.adapters;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunsystem.downloadfilechatapp.R;
import com.sunsystem.downloadfilechatapp.downloader.data.DownloadFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by steve on 6/1/16.
 */
@SuppressLint("ParcelCreator")
public class DownloadFileAdapter
        extends RecyclerView.Adapter<DownloadFileAdapter.DownloadFileViewHolder>
        implements Parcelable {
    private List<DownloadFile> mDownloadFileList = Collections.emptyList();

    public DownloadFileAdapter() {
        mDownloadFileList = new ArrayList<>();
    }

    @Override
    public DownloadFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_file_row, parent, false);

        return new DownloadFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadFileViewHolder holder, int position) {
        holder.mTvFileName.setText(mDownloadFileList.get(position).getmFilename());
    }

    @Override
    public int getItemCount() {
        return mDownloadFileList.size();
    }

    public int addFileName(final DownloadFile downloadFile) {
        mDownloadFileList.add(downloadFile);
        notifyItemInserted(mDownloadFileList.size() - 1);

        return mDownloadFileList.size();
    }

    public int removeFileName(final DownloadFile downloadFile) {
        final int index = mDownloadFileList.indexOf(downloadFile);

        final Iterator<DownloadFile> iterator = mDownloadFileList.iterator();
        while(iterator.hasNext()) {
            final DownloadFile file = iterator.next();
            if(file.getmId().equals(downloadFile.getmId())) {
                mDownloadFileList.remove(file);
                notifyDataSetChanged();
                break;
            }
        }

/*
        for(DownloadFile file : mDownloadFileList) {
            if(file.getmId().equals(downloadFile.getmId())) {
                mDownloadFileList.remove(file);
                notifyDataSetChanged();
            }
        }

        mDownloadFileList.remove(downloadFile);
        notifyItemRemoved(index);
*/

        return index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(mDownloadFileList == null) {
            dest.writeByte((byte) (0x00));
        }
        else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mDownloadFileList);
        }
    }

    static class DownloadFileViewHolder extends RecyclerView.ViewHolder {
       // @BindView(R.id.tvFileName) TextView mTvFileName;
        private TextView mTvFileName;

        public DownloadFileViewHolder(View itemView) {
            super(itemView);
            mTvFileName = (TextView)itemView.findViewById(R.id.tvFileName);

         //   ButterKnife.bind(itemView);
        }
    }
}
