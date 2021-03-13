package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVHolder>{
    @Override
    public int getItemViewType(int position) {
        return R.layout.search_result_tile;
    }

    @NonNull
    @Override
    public SearchRVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new SearchRVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRVHolder holder, int position) {
        holder.getView().setText("hello");
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
