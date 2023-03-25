package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.e_cart.Adapters.Complaint;
import com.example.e_cart.Adapters.Shopadapter;
import com.example.e_cart.Model.ComplaintModel;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Complaints extends AppCompatActivity {
    RecyclerView recyclerView;
    Complaint complaints;
    ArrayList<ComplaintModel>list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        //
        recyclerView= findViewById(R.id.Recycler1);
    //
    loadAllcomplainst();
    }

    private void loadAllcomplainst() {
        list = new ArrayList<>();
       recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Complaints.this));
        complaints= new Complaint(Complaints.this,list);
        recyclerView.setAdapter(complaints);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Report");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ComplaintModel complaintModel =  dataSnapshot.getValue(ComplaintModel.class);
                    list.add(complaintModel);
                }
                complaints.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}