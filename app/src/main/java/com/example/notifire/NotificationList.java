package com.example.notifire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationList extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton publishNewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        //Setting up the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.notice_list_RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new NoticeListRVAdapter());

        boolean isFromOwnedActivity = getIntent().getBooleanExtra("isFromOwned",false);
        if(isFromOwnedActivity){
            publishNewButton = (FloatingActionButton) findViewById(R.id.publish_new_button);
            publishNewButton.setVisibility(View.VISIBLE);
            publishNewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String boardID = getIntent().getStringExtra("boardID");
                    Intent intent = new Intent(NotificationList.this, PublishNewNotice.class);
                    intent.putExtra("boardID", boardID);
                    startActivity(intent);
                }
            });
        }

    }
}