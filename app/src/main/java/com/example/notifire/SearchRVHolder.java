package com.example.notifire;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchRVHolder extends RecyclerView.ViewHolder {

    TextView textView;
    public SearchRVHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.search_tile_title);
    }

    public TextView getView() {
        return textView;
    }
}
