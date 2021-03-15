package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeListRVAdapter extends RecyclerView.Adapter<NoticeListRVAdapter.ViewHolder> {

    @Override
    public int getItemViewType(int position) {
        return R.layout.notification_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.getView().setText("hello");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    //The View Holder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        //the text to be changed
        private TextView noticeTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.notice_title);
        }

        //return the View which is to be populated with data here
        public TextView getView() {
            return noticeTitle;
        }
    }
}

