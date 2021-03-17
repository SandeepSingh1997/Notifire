package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;

public class CreateNewBoard extends AppCompatActivity {

    private Button createNewButton;
    private EditText titleEText, descEText, contactEText;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_board);

        createNewButton = (Button) findViewById(R.id.create_new_board_button);
        titleEText = (EditText)findViewById(R.id.board_title);
        descEText = (EditText)findViewById(R.id.board_description);
        contactEText = (EditText)findViewById(R.id.board_contact);

        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do things to make a new board
                db = FirebaseFirestore.getInstance();

                String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String title = titleEText.getText().toString().trim();
                String description = descEText.getText().toString().trim();
                String contact = contactEText.getText().toString().trim();
                if (title.isEmpty() || description.isEmpty() || contact.isEmpty()){
                    contactEText.setError("Any field must not be empty");
                    return;
                }

                Board board = new Board(title, description, contact, uID);
                db.collection("boards").add(board).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //adding the board ID in user's ownedBoards field
                        db.collection("users").document(uID).update("ownedBoardsID",
                                FieldValue.arrayUnion(documentReference.getId())).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateNewBoard.this, "ownedlist updated", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateNewBoard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(CreateNewBoard.this, "The board has been added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewBoard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}