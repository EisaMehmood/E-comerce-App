package com.example.e_cart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Activity.Chat;
import com.example.e_cart.Model.ModelChat;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.viewholder> {
    public  static  final  int MES_TYPE_LEFT =0;
    public  static  final  int MES_TYPE_RIGHT =1;
  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Context context ;
    ArrayList<ModelChat> list;

    public ChatsAdapter(Context context, ArrayList<ModelChat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType == MES_TYPE_RIGHT){

           View view = LayoutInflater.from(context).inflate(R.layout.chatleft,parent,false);
           return  new viewholder(view);
       }
       else {
           View view1 = LayoutInflater.from(context).inflate(R.layout.chatright,parent,false);
           return  new viewholder(view1);

       }

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        ModelChat modelChat =  list.get(position);
        holder.showmessage.setText(modelChat.getMessage());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView showmessage;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            showmessage=itemView.findViewById(R.id.showmessage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getRecevier().equals(firebaseAuth.getUid())) {
             return MES_TYPE_RIGHT;
        }else {
            return  MES_TYPE_LEFT;
        }
    }

}
