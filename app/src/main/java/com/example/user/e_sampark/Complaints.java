package com.example.user.e_sampark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Complaints extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView details;
    private TextView already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        details=(TextView)findViewById(R.id.details);
        already=(TextView)findViewById(R.id.already);
        details.setVisibility(View.INVISIBLE);
        already.setVisibility(View.INVISIBLE);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void newComplain(View v)
    {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String completed=dataSnapshot.child("Completed").getValue().toString();
                if(completed.equals("Yes"))
                {
                    String Filed_A_Complain=dataSnapshot.child("Filed_A_Complain").getValue().toString();
                    //if(Filed_A_Complain.equals("No"))
                    //{
                        Intent in = new Intent(Complaints.this, FileComplain.class);
                        startActivity(in);
                   // }
                    //else
                   // {
                        //already.setVisibility(View.VISIBLE);
                    //}
                }
                else
                {
                    details.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void check(View v)
    {
        Intent in=new Intent(Complaints.this,FiledComplaints.class);
        startActivity(in);
    }
}
