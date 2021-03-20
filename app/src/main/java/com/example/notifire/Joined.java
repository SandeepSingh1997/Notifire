package com.example.notifire;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Joined extends Fragment implements BoardRVAdapter.MyOnClickListener {

    private String uId;

    public Joined() {
        this.uId = SplashScreen.uId;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FloatingActionButton joinNewButton;
    private FirebaseFirestore db;
    private BoardRVAdapter adapter;
    ArrayList<String> joinedList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined, container, false);

        db = FirebaseFirestore.getInstance();

        joinedList = SplashScreen.joinedBoardsList;
        Query query = db.collection("boards").whereIn("__name__", joinedList);

        FirestoreRecyclerOptions<Board> options = new FirestoreRecyclerOptions.Builder<Board>().
                setQuery(query, Board.class).build();

        adapter = new BoardRVAdapter(this, options);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.joined_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        //Setting up the join new button
        joinNewButton = (FloatingActionButton) view.findViewById(R.id.join_new_button);
        joinNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), JoinNewBoard.class);
                startActivity(intent);
            }
        });
        return view;
    }

    //To start and stop listening to the firebase
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(int position, String boardID) {
        Toast.makeText(getActivity(),boardID,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), NotificationList.class);
        intent.putExtra("boardID", boardID);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position, String boardID) {
        Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();
    }
}