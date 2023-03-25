package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_cart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;

public class ComplaintActivitySend extends AppCompatActivity {
    EditText subject , comment;
    Button reprot;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_send);
        subject = findViewById(R.id.Subject);
        comment = findViewById(R.id.comment);
        reprot = findViewById(R.id.Report);
       //
        firebaseAuth = FirebaseAuth.getInstance();
        //
        reprot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendreport();
            }
        });


    }
     // method for sending Report
    private void sendreport() {
        String timestemp = ""+System.currentTimeMillis();
        HashMap<String,Object> hashMap =  new HashMap<>();
        hashMap.put("Uid",firebaseAuth.getUid());
        hashMap.put("Ticket",""+timestemp);
        hashMap.put("Subject",""+ subject.getText().toString());
        hashMap.put("Comment",""+comment.getText().toString());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Report");
        databaseReference.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(ComplaintActivitySend.this);
                builder.setTitle("NOTE");
                builder.setMessage("Your Report is send We are soon taking action & soon CatchUp you");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ComplaintActivitySend.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });




    }

}