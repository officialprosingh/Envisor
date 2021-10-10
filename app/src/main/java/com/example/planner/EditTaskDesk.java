package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTaskDesk extends AppCompatActivity {

    EditText titleDoes, descDoes, dateDoes;
    Button btnSaveUpdate, btnDelete;
    DatabaseReference reference;

    Dialog dialog;

    private View bottom_nav;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);


        dialog = new Dialog(EditTaskDesk.this);
        dialog.setContentView(R.layout.dialog_completed_theme);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        Button okay = dialog.findViewById(R.id.closeButton);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditTaskDesk.this, MainActivity.class));
                dialog.dismiss();
            }
        });



        fab = findViewById(R.id.btnCompleted);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            dialog.show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        titleDoes = findViewById(R.id.titledoes);
        descDoes = findViewById(R.id.descdoes);
        dateDoes = findViewById(R.id.datedoes);

        btnSaveUpdate = findViewById(R.id.btnSaveUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        dateDoes.setText(getIntent().getStringExtra("datedoes"));

        final String keykeyDoes = getIntent().getStringExtra("keydoes");

        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp").
                child("Does" + keykeyDoes);

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titleDoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descDoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(dateDoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keykeyDoes);


                        Intent a = new Intent(EditTaskDesk.this,MainActivity.class);
                        startActivity(a);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent a = new Intent(EditTaskDesk.this, MainActivity.class);
                            startActivity(a);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }
}
