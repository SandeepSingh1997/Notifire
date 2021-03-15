package com.example.notifire;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Joined extends Fragment implements RVAdapter.MyOnClickListener  {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Joined() {
        // Required empty public constructor
    }

    public static Joined newInstance(String param1, String param2) {
        Joined fragment = new Joined();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       }


       private FloatingActionButton joinNewButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_joined, container, false);

        //Setting the recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.joined_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new RVAdapter(this));

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

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(getActivity(), NotificationList.class);
        startActivity(intent);
    }

    @Override
    public void onLongClick(int position) {
        Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();
    }

}