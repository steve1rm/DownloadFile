package com.sunsystem.downloadfilechatapp.downloader.adapters;

import android.content.Context;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve on 6/1/16.
 */
public class DownloadFileAdapter extends RecyclerView.Adapter<DownloadFileAdapter.DownloadFileViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<DownloadFile> mDownloadFileList = Collections.emptyList();

    public DownloadFileAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mDownloadFileList = new ArrayList<>();
    }

    @Override
    public DownloadFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.download_file_row, parent, false);

        return new DownloadFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadFileViewHolder holder, int position) {
        final String filename = mDownloadFileList.get(position).getmFilename();

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
