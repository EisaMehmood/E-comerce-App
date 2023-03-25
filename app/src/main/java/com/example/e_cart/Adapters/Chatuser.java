package com.example.e_cart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_cart.Activity.Chat;
import com.example.e_cart.Activity.Order_Detail_Shop;
import com.example.e_cart.Model.ModelChat;
import com.example.e_cart.Model.Modelchatuser;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chatuser extends RecyclerView.Adapter<Chatuser.chatholder> {
   FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Context context ;
    ArrayList<Modelshop>list;


    public Chatuser( Context context, ArrayList<Modelshop> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public chatholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatuser,parent,false);
        return new chatholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatholder holder, int position) {
        Modelshop modelshop = list.get(position);
        holder.name.setText(modelshop.getName());
        try {
            Glide.with(context)
                    .load(modelshop.getProfileimage()).placeholder(R.drawable.imageholder).
                   into(holder.image);
        }catch (Exception e){}
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(context, Chat.class);
                 intent.putExtra("name",modelshop.getName());
                 intent.putExtra("orderby",modelshop.getUid());
                  context.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class chatholder extends RecyclerView.ViewHolder {
        TextView name, message;
        ImageView image;
        public chatholder(@NonNull View itemView) {
            super(itemView);
              name = itemView.findViewById(R.id.textView2);
              image= itemView.findViewById(R.id.imageView);

        }
    }
}
