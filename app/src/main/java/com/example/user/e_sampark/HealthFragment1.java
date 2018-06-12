package com.example.user.e_sampark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HealthFragment1 extends Fragment {

    private View mMainView;
    private TextView mTextView;
    private Button mButton;
    private DatabaseReference mDatabase;
    private String current_user_id;
    private FirebaseAuth mAuth;
    private Button mDelete;
    private DatabaseReference mMessages;

    public HealthFragment1() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_health_fragment1, container, false);

        mTextView=(TextView)mMainView.findViewById(R.id.your_initial_complain);
        mButton=(Button)mMainView.findViewById(R.id.chat);
        mDelete=(Button)mMainView.findViewById(R.id.delete);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Complaints");
        mMessages=FirebaseDatabase.getInstance().getReference().child("Messages");
        mAuth=FirebaseAuth.getInstance();
        current_user_id=mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(current_user_id))
                {
                    mDatabase.child(current_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("Health"))
                            {
                                String message=dataSnapshot.child("Health").child("Message").getValue().toString();
                                mTextView.setText(message);
                                mButton.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                mTextView.setText("You Have Not Filed Any Complaints Against Health Sector Yet");
                                mButton.setVisibility(View.GONE);
                                mDelete.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    mTextView.setText("You Have Not Filed Any Complaints Against Health Sector Yet");
                    mButton.setVisibility(View.GONE);
                    mDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat();
            }
        });


        return mMainView;
    }

    private void chat() {
        Intent in=new Intent(getContext(),ChatActivity.class);
        in.putExtra("sender",current_user_id);
        in.putExtra("reciever","Health");
        startActivity(in);
    }

    public void delete()
    {
        mDatabase.child(current_user_id).child("Health").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabase.child("Health").child(current_user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mMessages.child(current_user_id).child("Health").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mMessages.child("Health").child(current_user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(),"Complain CLosed",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

    }
}
