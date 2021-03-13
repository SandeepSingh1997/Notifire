package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeListRVAdapter extends RecyclerView.Adapter<NoticeListRVHolder> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.notification_layout;
    }

    @NonNull
    @Override
    public NoticeListRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new NoticeListRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeListRVHolder holder, int position) {
        holder.getView().setText("hello");
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}

