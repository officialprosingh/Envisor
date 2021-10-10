package com.example.planner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {


    TextView titlepage, subtitlepage;

    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ////Login///
        mAuth = FirebaseAuth.getInstance();
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(ViewActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };


        mAuth = FirebaseAuth.getInstance();
        if (user == null) {
            finish();
            startActivity(new Intent(ViewActivity.this, LoginActivity.class));
        }

        // Working with data
        ourdoes = findViewById(R.id.ourdoes);
        ourdoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDoes>();

        // Get data from firebase
        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // set code to retrive data and replace layout
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);
                    list.add(p);
                }
                doesAdapter = new DoesAdapter(ViewActivity.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });


    }



}