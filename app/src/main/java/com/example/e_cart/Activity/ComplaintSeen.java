package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.e_cart.Model.ComplaintModel;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.atomic.DoubleAccumulator;

public class ComplaintSeen extends AppCompatActivity {
    FirebaseAuth  firebaseAuth;
    String uid;
   TextView Subject ,comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Subject= findViewById(R.id.Subject);
        comment = findViewById(R.id.Comment);
        setContentView(R.layout.activity_complaint_seen);
        uid =getIntent().getStringExtra("uid");
        firebaseAuth= FirebaseAuth.getInstance();


        loadDetail();

    }

    private void loadDetail() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Report");
        databaseReference.orderByChild(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String subject= ""+dataSnapshot.child("Subject").getValue();
                    String Comment = ""+dataSnapshot.child("Comment").getValue();
                    Subject.setText(subject);
                    comment.setText(Comment);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}