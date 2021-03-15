package com.example.notifire;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private MyOnClickListener onClickListener;

    public RVAdapter(MyOnClickListener onClickListener){
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return 5;
    }

    //THE VIEW HOLDER INNER CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {

        //the text to be changed
        private View view;
        MyOnClickListener myOnClickListener;

        public ViewHolder(@NonNull View itemView, MyOnClickListener onClickListener) {
            super(itemView);
            //Find the views to be populated with data
            view = itemView.findViewById(R.id.board_view);
            myOnClickListener = onClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        //return the View which is to be populated with data here
        public View getView() {
            return view;
        }
    }

    //OnClickListener INTERFACE
    public interface MyOnClickListener{
        void onClick( int position);
        void onLongClick(int position);
    }

}
