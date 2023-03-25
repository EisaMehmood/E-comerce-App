package com.example.e_cart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Activity.ComplaintSeen;
import com.example.e_cart.Model.ComplaintModel;
import com.example.e_cart.R;
import com.google.firebase.auth.internal.RecaptchaActivity;

import java.util.ArrayList;

public class Complaint extends RecyclerView.Adapter<Complaint.complaintView> {
    Context context;
    ArrayList<ComplaintModel> list;

    public Complaint(Context context, ArrayList<ComplaintModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public complaintView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.complaint_row,parent,false);
        return new complaintView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull complaintView holder, int position) {
        ComplaintModel complaintModel = list.get(position);
        holder.ticketno.setText(complaintModel.getTicket());
        holder.subject.setText(complaintModel.getSubject());
        String uid = complaintModel.getUid();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, ComplaintSeen.class);
                intent.putExtra("uid",uid);
               context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class complaintView extends RecyclerView.ViewHolder {
        TextView ticketno , subject;
        public complaintView(@NonNull View itemView) {
            super(itemView);
            ticketno= itemView.findViewById(R.id.ticket);
            subject= itemView.findViewById(R.id.textView);
        }
    }
}
