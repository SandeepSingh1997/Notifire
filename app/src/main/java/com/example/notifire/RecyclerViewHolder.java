package com.example.notifire;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    //the text to be changed
    private TextView textView;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        //Find the views to be populated with data
        textView = itemView.findViewById(R.id.randomText);
    }

    //return the View which is to be populated with data here
    public TextView getView() {
        return textView;
    }
}
