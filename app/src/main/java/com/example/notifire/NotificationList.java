package com.example.notifire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationList extends AppCompatActivity{

    private TextView publishNewButton;
    private String boardID;
    private NoticeListRVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        boardID = getIntent().getStringExtra("boardID");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query = db.collection("boards").document(boardID).collection("notices");

        FirestoreRecyclerOptions<Notice> options = new FirestoreRecyclerOptions.Builder<Notice>().
                setQuery(query, Notice.class).build();

        adapter = new NoticeListRVAdapter( options);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notice_list_RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //checking from which fragment activity started
        boolean isFromOwnedActivity = getIntent().getBooleanExtra("isFromOwned",false);
        if(isFromOwnedActivity){
            publishNewButton = (TextView) findViewById(R.id.publish_new_button);
            publishNewButton.setVisibility(View.VISIBLE);
            publishNewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NotificationList.this, PublishNewNotice.class);
                    intent.putExtra("boardID", boardID);
                    startActivity(intent);
                }
            });
        }
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


}