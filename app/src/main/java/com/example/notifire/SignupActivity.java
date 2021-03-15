package com.example.notifire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword,inputCPassword;
    private Button btnSignUp;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.signup_signup_button);
        inputEmail = (EditText) findViewById(R.id.signup_username);
        inputPassword = (EditText) findViewById(R.id.signup_password);
        inputCPassword = (EditText) findViewById(R.id.signup_confirm_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=inputEmail.getText().toString().trim();
                String password=inputPassword.getText().toString().trim();
                String cpassword=inputCPassword.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Username cannot be Blank !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){

                }
                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password too short !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(cpassword)){
                    Toast.makeText(getApplicationContext(),"Confirm Password is not same as Password!",Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this,"createUserWithEmail:onComplete:"+task.isSuccessful(),Toast.LENGTH_SHORT).show();
                                if(!task.isSuccessful()){
                                    Toast.makeText(SignupActivity.this,"Authentication Failed"+task.getException(),Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    startActivity(new Intent(SignupActivity.this,JoinedOwned.class));
                                    finish();
                                }
                            }
                        });
            }
        });



    }
}