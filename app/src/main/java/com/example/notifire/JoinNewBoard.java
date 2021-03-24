package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class JoinNewBoard extends AppCompatActivity implements SearchRVAdapter.MyClickListener {
    private EditText searchByLinkEText, searchByNameEText;
    private Button searchButton;
    public ArrayList<Board> boardArrayList;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_new_board);

        db = FirebaseFirestore.getInstance();

        searchByLinkEText = (EditText) findViewById(R.id.search_by_id);
        searchByNameEText = (EditText) findViewById(R.id.search_by_name);
        searchButton = (Button) findViewById(R.id.join_search_button);
        boardArrayList = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.join_new_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SearchRVAdapter adapter = new SearchRVAdapter(this, boardArrayList);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchLink = searchByLinkEText.getText().toString().trim();
                String searchName = searchByNameEText.getText().toString().trim();
                if (!searchLink.isEmpty()) {
                    db.collection("boards").whereEqualTo("__name__", searchLink).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boardArrayList.add(document.toObject(Board.class));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                } else if (!searchName.isEmpty()) {
                    db.collection("boards").whereEqualTo("title", searchName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    boardArrayList.add(document.toObject(Board.class));
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }else {
                    searchByNameEText.setError("Please enter something to search!");
                }
            }
        });
    }

    @Override
    public void onMyClick(int position, String boardID) {
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("joinedBoardsID", FieldValue.arrayUnion(boardID)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(JoinNewBoard.this, "You joined the board", Toast.LENGTH_SHORT).show();
                SplashScreen.joinedBoardsList.add(boardID);
            }
        });
    }

}