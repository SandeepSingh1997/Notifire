package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BoardRVAdapter extends FirestoreRecyclerAdapter<Board, BoardRVAdapter.ViewHolder> {

    private MyOnClickListener onClickListener;

    public BoardRVAdapter(MyOnClickListener onClickListener, FirestoreRecyclerOptions<Board> options) {
        super(options);
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.board_layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onClickListener);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Board model) {
        holder.boardID = model.getBoardID();
        holder.boardTitle.setText(model.getTitle());
        holder.boardDesc.setText(model.getDescription());
        holder.boardContact.setText(model.getContact());
    }

    //THE VIEW HOLDER INNER CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView boardTitle, boardDesc, boardContact;
        private String boardID;
        MyOnClickListener myOnClickListener;

        public ViewHolder(@NonNull View itemView, MyOnClickListener onClickListener) {
            super(itemView);
            boardID = new String();
            boardTitle = itemView.findViewById(R.id.board_tile_title);
            boardDesc = itemView.findViewById(R.id.board_tile_desc);
            boardContact = itemView.findViewById(R.id.board_tile_contact);
            myOnClickListener = onClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition(), boardID);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnClickListener.onLongClick(getAdapterPosition(), boardID);
                    return true;
                }
            });
        }
    }

    //OnClickListener INTERFACE
    public interface MyOnClickListener {
        void onClick(int position, String boardID);

        void onLongClick(int position, String boardID);
    }

}
