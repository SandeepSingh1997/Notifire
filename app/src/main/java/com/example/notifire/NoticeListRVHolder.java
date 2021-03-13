package com.example.notifire;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeListRVHolder extends RecyclerView.ViewHolder {

    //the text to be changed
    private TextView noticeTitle;

    public NoticeListRVHolder(@NonNull View itemView) {
        super(itemView);
        noticeTitle = itemView.findViewById(R.id.notice_title);
    }

    //return the View which is to be populated with data here
    public TextView getView() {
        return noticeTitle;
    }
}