package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cart.Adapters.ChatsAdapter;
import com.example.e_cart.Model.ModelChat;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat extends AppCompatActivity {
 ImageButton imageButton ,back;
 TextView name;
  EditText message ;
  FirebaseAuth fuser;
  String orderBy,Name;
  RecyclerView recyclerView;
  ArrayList<ModelChat>list;
  ChatsAdapter chatsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        name = findViewById(R.id.name);
        recyclerView=findViewById(R.id.recyclerofchat);
        imageButton =findViewById(R.id.enetr);
        message =    findViewById(R.id.message);
        back = findViewById(R.id.back);
        //
        fuser =FirebaseAuth.getInstance();
        orderBy = getIntent().getStringExtra("orderby");
        Name = getIntent().getStringExtra("name");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recievemsg(fuser.getCurrentUser().getUid(),orderBy);
    //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //

         imageButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String Message = message.getText().toString();
                 if(!(Message.equals(""))){
                     send_message(fuser.getUid(),orderBy, Message);
                 }else{
                     Toast.makeText(Chat.this, "Please enter message", Toast.LENGTH_SHORT).show();
                 }
                message.setText("");
             }
         });
         name.setText(Name);
    }
    //
    private void send_message(String sender ,String recevier , String messsage){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String timestemp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("recevier",recevier);
        hashMap.put("message", messsage);

        databaseReference.child("Chats").push().setValue(hashMap);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Chatlist").child(recevier);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             if(!snapshot.exists()){
                 database.child("id").setValue(sender);
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private  void recievemsg(String myid, String userid){
     list = new ArrayList<>();
    DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference("Chats");
    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            list.clear();
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                ModelChat modelChat = dataSnapshot.getValue(ModelChat.class);
                if(modelChat.getRecevier().equals(userid)&&modelChat.getSender().equals(myid)||modelChat.getRecevier()
                        .equals(myid)&&modelChat.getSender().equals(userid)){
                    list.add(modelChat);
                }
                chatsAdapter = new ChatsAdapter(Chat.this,list);;
                recyclerView.setAdapter(chatsAdapter);
            }
        }

        @Override

        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


 }

}
