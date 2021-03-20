package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword,inputCPassword, inputName;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private ProgressBar signUpPbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //setting up firebase auth and db
        auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnSignUp = (Button) findViewById(R.id.signup_signup_button);
        inputName = (EditText) findViewById(R.id.signup_name);
        inputEmail = (EditText) findViewById(R.id.signup_username);
        inputPassword = (EditText) findViewById(R.id.signup_password);
        inputCPassword = (EditText) findViewById(R.id.signup_confirm_password);
        signUpPbar = (ProgressBar) findViewById(R.id.signup_progress);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString().trim();
                String email=inputEmail.getText().toString().trim();
                String password=inputPassword.getText().toString().trim();
                String cpassword=inputCPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    inputName.setError("Name cannot be blank !");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    inputEmail.setError("Username cannot be Blank !");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    inputPassword.setError("password cannot be Blank !");
                    return;
                }
                if(password.length()<6){
                    inputPassword.setError(" passwords must be atleast 6 characters long");
                    return;
                }
                if(!password.equals(cpassword)){
                    inputPassword.setError("passwords are not the same");
                    return;
                }
                signUpPbar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this,"createUserWithEmail:onComplete:"+task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                if(!task.isSuccessful()){
                                    signUpPbar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(SignupActivity.this,"Sign Up Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    //adding user document in firestore
                                    String uID = task.getResult().getUser().getUid();
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name",name);
                                    db.collection("users").document(uID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SignupActivity.this, "SignUp Succesful",Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignupActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    startActivity(new Intent(SignupActivity.this,SplashScreen.class));
                                    finish();
                                }
                            }
                        });
            }
        });


    }
}