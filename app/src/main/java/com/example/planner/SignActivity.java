package com.example.planner;

import android.content.ContentValues;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class
SignActivity extends AppCompatActivity  {

    private EditText username_sign_up , password_sign_up , name_sign_up , number_sign_up  ;
    private Button login_signup_button , signup;
    private ProgressBar signup_progress ;
    private ImageButton arrow_sign;
    private EditText reward;
    private static final int Money = 0;


    private FirebaseAuth mAuth;

    DatabaseReference databaseUsers , databaseRewards;

    public static final int amount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        mAuth = FirebaseAuth.getInstance();


        username_sign_up = (EditText) findViewById(R.id.username_sign_up);
        password_sign_up = (EditText) findViewById(R.id.password_sign_up);
        signup_progress = (ProgressBar) findViewById(R.id.sign_up_progress);
        number_sign_up = (EditText) findViewById(R.id.phone_sign_up);
        name_sign_up = (EditText) findViewById(R.id.name_sign_up) ;
        reward = (EditText) findViewById(R.id.reward_signup);


        databaseUsers = FirebaseDatabase.getInstance().getReference("New Users");
        databaseRewards = FirebaseDatabase.getInstance().getReference("Rewards");


        login_signup_button = (Button) findViewById(R.id.login_sign_up);
        login_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignActivity.this, LoginActivity.class));
            }
        });

        arrow_sign = (ImageButton) findViewById(R.id.sign_up_arrow);
        arrow_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        signup = (Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();


            }
        });


    }

    private void Login(){
        final String email = username_sign_up.getText().toString().trim();
        final String password = password_sign_up.getText().toString().trim();
        final String name = name_sign_up.getText().toString().trim();
        final String snumber = number_sign_up.getText().toString().trim();
        final String money = "10";




        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username_sign_up.setError("Please Enter a Valid Email");
            username_sign_up.requestFocus();
            return;
        }

        if (email.isEmpty()){
            username_sign_up.setError("Email is Required");
            username_sign_up.requestFocus();
            return;
        }

        if (name.isEmpty()){
            username_sign_up.setError("Name is Required");
            username_sign_up.requestFocus();
            return;
        }

        if (snumber.isEmpty()){
            username_sign_up.setError("Number is Required");
            username_sign_up.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_sign_up.setError("Password is Required");
            password_sign_up.requestFocus();
            return;
        }

        if (password.length()<6){
            password_sign_up.setError("Maximum Length of Password Should be 6");
            password_sign_up.requestFocus();
            return;
        }

        signup_progress.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signup_progress.setVisibility(View.GONE);
                if (task.isSuccessful()){



                    String id = databaseUsers.push().getKey();
                    User user = new User(name , email , snumber , password, money);
                    String userId = mAuth.getUid();



                    databaseUsers.child(userId).setValue(user);

                    Intent intent = new Intent(SignActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Email is Already Registered ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
