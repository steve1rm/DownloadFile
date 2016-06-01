package com.sunsystem.downloadfilechatapp.downloader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunsystem.downloadfilechatapp.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve on 6/1/16.
 */
public class DownloadFileAdapter extends RecyclerView.Adapter<DownloadFileAdapter.DownloadFileViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mFileNameList = Collections.emptyList();

    public DownloadFileAdapter(Context context, List<String> fileNameList) {
        mLayoutInflater = LayoutInflater.from(context);
        mFileNameList = fileNameList;
    }

    @Override
    public DownloadFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.download_file_row, parent, false);

        return new DownloadFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadFileViewHolder holder, int position) {
        holder.mTvFileName.setText(mFileNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFileNameList.size();
    }

    static class DownloadFileViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFileName) TextView mTvFileName;

        public DownloadFileViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(itemView);
        }
    }
}
