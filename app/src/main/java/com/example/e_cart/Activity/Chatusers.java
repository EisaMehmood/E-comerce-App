package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.e_cart.Adapters.ChatsAdapter;
import com.example.e_cart.Adapters.Chatuser;
import com.example.e_cart.Model.ModelChat;
import com.example.e_cart.Model.Modelchatuser;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class Chatusers extends AppCompatActivity {
    Chatuser chatuser;
    Modelshop modelshop ;
    RecyclerView recyclerView;
    ArrayList<Modelchatuser>list;
    ArrayList<Modelshop> userlist;
    ImageButton back;


    FirebaseAuth firebaseAuth;
     String sender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatuser);
        recyclerView=findViewById(R.id.recyclerofchat1);
        back =findViewById(R.id.back);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();
        //
       recievemsg();

//
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private  void recievemsg(){
        list = new ArrayList<>();
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("Chatlist");
        databaseReference.orderByChild(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                   sender = ""+dataSnapshot.child("id").getValue();
                   Modelchatuser modelchatuser = dataSnapshot.getValue(Modelchatuser.class);
                    list.add(modelchatuser);
                }

                detail();

                }


            @Override

            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void detail(){
        userlist = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                 Modelshop modelshop =dataSnapshot.getValue(Modelshop.class);
                 for (Modelchatuser  modelchatuser : list){
                     if(modelshop.getUid().equals(modelchatuser.getId())){
                         userlist.add(modelshop);

                     }
                 }
                }
                chatuser= new Chatuser(Chatusers.this,userlist);
                recyclerView.setAdapter(chatuser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }}