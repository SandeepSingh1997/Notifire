package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class PublishNewNotice extends AppCompatActivity {

    private Button publishNewNoticeButton;
    private EditText titleEtext, subjectEtext, descEtext;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_new_notice);

        //Toast.makeText(PublishNewNotice.this, getIntent().getStringExtra("boardID"),Toast.LENGTH_SHORT).show();
        publishNewNoticeButton = (Button) findViewById(R.id.publish_notice_button);
        titleEtext = (EditText)findViewById(R.id.publish_title);
        subjectEtext = (EditText)findViewById(R.id.publish_subject);
        descEtext = (EditText)findViewById(R.id.publish_description);

        publishNewNoticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                String boardID = getIntent().getStringExtra("boardID");

                Date publishDate = new Date();
                String title = titleEtext.getText().toString();
                String subject = subjectEtext.getText().toString();
                String description = descEtext.getText().toString();
                if (title.isEmpty() || subject.isEmpty() ){
                    titleEtext.setError("Only optional field can be left empty");
                    return;
                }

                Notice notice = new Notice(title, subject, description, publishDate);
                db.collection("boards").document(boardID).collection("notices").add(notice).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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