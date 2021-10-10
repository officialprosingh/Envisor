package com.example.planner;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText username_login , password_login;
    private Button sign_up_button , sign_in_button;
    private ProgressBar login_progress;
    private ImageButton arrow_login;
    private Button help;
    String USER_TYPE_1 = "user";




    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final SharedPreferences  sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
// get editor to edit in file
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String name = USER_TYPE_1.getClass().toString();


        mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        help = (Button) findViewById(R.id.needhelp);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ContactActivity.class));
            }
        });

        username_login = (EditText) findViewById(R.id.login_username);
        password_login = (EditText) findViewById(R.id.login_password);
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
        arrow_login = (ImageButton) findViewById(R.id.login_in_arrow);

        arrow_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username_login.setError("Please Enter a Valid Email");
                    username_login.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    username_login.setError("Email is Required");
                    username_login.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    password_login.setError("Password is Required");
                    password_login.requestFocus();
                    return;
                }

                if (password.length()<6){
                    password_login.setError("Maximum Length of Password Should be 6");
                    password_login.requestFocus();
                    return;
                }

                login_progress.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        login_progress.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            editor.putBoolean("donator",true);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Authentication failed. Check Your Email or Password",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        sign_up_button = (Button) findViewById(R.id.sign_up_login);
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignActivity.class));
            }
        });

        sign_in_button = (Button) findViewById(R.id.sign_in_login);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = username_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    username_login.setError("Please Enter a Valid Email");
                    username_login.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    username_login.setError("Email is Required");
                    username_login.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    password_login.setError("Password is Required");
                    password_login.requestFocus();
                    return;
                }

                if (password.length()<6){
                    password_login.setError("Maximum Length of Password Should be 6");
                    password_login.requestFocus();
                    return;
                }

                login_progress.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        login_progress.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            editor.putBoolean("user",true);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        }
                        else {

                            Toast.makeText(LoginActivity.this, "Authentication failed. Check Your Email or Password",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
