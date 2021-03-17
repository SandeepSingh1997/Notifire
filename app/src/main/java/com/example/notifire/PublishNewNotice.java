package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class PublishNewNotice extends AppCompatActivity {

    private Button publishNewNoticeButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_new_notice);

        Toast.makeText(PublishNewNotice.this, getIntent().getStringExtra("boardID"),Toast.LENGTH_SHORT).show();
        publishNewNoticeButton = (Button) findViewById(R.id.publish_notice_button);
        publishNewNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                String boardID = getIntent().getStringExtra("boardID");

                Date publishDate = new Date();
                String title = "DUCS";
                String subject = "www.ducs.in";
                String description = "Department of Computer Science";

                Notice notice = new Notice(title, subject, description, publishDate);
                db.collection("boards").document(boardID).collection("notice").add(notice).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PublishNewNotice.this, "The notice has been added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PublishNewNotice.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}