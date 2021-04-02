package com.example.notifire;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

public class SearchRVAdapter extends RecyclerView.Adapter<SearchRVAdapter.ViewHolder>{

    ArrayList<Board> boardArrayList;
    MyClickListener mylistener;

    public SearchRVAdapter(MyClickListener listener, ArrayList<Board> boardArrayList){
        this.boardArrayList = boardArrayList;
        mylistener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.search_result_tile;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new ViewHolder(view, mylistener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Board board = boardArrayList.get(position);
        holder.board = board;
        holder.title.setText(board.getTitle());
        holder.desc.setText(board.getDescription());
    }

    @Override
    public int getItemCount() {
        return boardArrayList.size();
    }

    //The View Holder class
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title, desc;
        public Board board;
        MyClickListener myClickListener;

        public ViewHolder(@NonNull View itemView, MyClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.search_tile_title);
            desc = itemView.findViewById(R.id.search_tile_desc);
            myClickListener = listener;
            itemView.setOnClickListener(v -> {
                myClickListener.onMyClick(getAdapterPosition(), board);
            });
        }
    }

    public interface MyClickListener {
         void onMyClick(int position, Board board);
    }

}
