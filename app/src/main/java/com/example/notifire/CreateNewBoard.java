package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class CreateNewBoard extends AppCompatActivity {

    private Button createNewButton, selectImageButton;
    private EditText titleEText, descEText, contactEText;
    private ImageView imageView;
    private int PICK_IMG = 100;
    private FirebaseFirestore db;
    String boardID;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_board);

        createNewButton = (Button) findViewById(R.id.create_new_board_button);
        selectImageButton = (Button) findViewById(R.id.select_image_button);
        imageView = (ImageView) findViewById(R.id.board_image);
        titleEText = (EditText) findViewById(R.id.board_title);
        descEText = (EditText) findViewById(R.id.board_description);
        contactEText = (EditText) findViewById(R.id.board_contact);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallaryIntent, PICK_IMG);
            }
        });

        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do things to make a new board
                db = FirebaseFirestore.getInstance();

                String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String title = titleEText.getText().toString().trim();
                String description = descEText.getText().toString().trim();
                String contact = contactEText.getText().toString().trim();
                if (title.isEmpty() || description.isEmpty() || contact.isEmpty()) {
                    contactEText.setError("Any field must not be empty");
                    return;
                }

                Board board = new Board(title, description, contact, uID);
                db.collection("boards").add(board).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        boardID = documentReference.getId();
                        documentReference.update("boardID", boardID);

                        Board userBoard = new Board(boardID, title, description, contact, uID);
                        //adding the board ID in user's ownedBoards field
                        db.collection("users").document(uID).collection("ownedBoards").document(boardID).set(userBoard);
                        Toast.makeText(CreateNewBoard.this, "The board has been added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMG) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

}