package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.DateFormat;

public class NoticeListRVAdapter extends FirestoreRecyclerAdapter<Notice, NoticeListRVAdapter.ViewHolder> {

    public NoticeListRVAdapter(@NonNull FirestoreRecyclerOptions<Notice> options) {
        super(options);
    }

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Notice model) {
        holder.noticeTitle.setText(model.getTitle());
        holder.noticeSubject.setText(model.getSubject());
        //holder.noticeDesc.setText(model.getDescription());
        holder.noticeDate.setText(DateFormat.getDateInstance().format(model.getPublishDate()));
    }

    //The View Holder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        //the text to be changed
        private TextView noticeTitle;
        private TextView noticeSubject;
        //private TextView noticeDesc;
        private TextView noticeDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.notice_title);
            noticeSubject = itemView.findViewById(R.id.notice_subject);
            //noticeDesc = itemView.findViewById(R.id.notice_desc);
            noticeDate = itemView.findViewById(R.id.notice_date);
        }
    }
}

