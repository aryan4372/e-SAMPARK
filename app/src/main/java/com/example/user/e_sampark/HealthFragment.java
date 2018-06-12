package com.example.user.e_sampark;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HealthFragment extends Fragment {

    private View mMainView;
    private RecyclerView list;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersDatabase;

    public HealthFragment()
    {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mMainView = inflater.inflate(R.layout.fragment_health, container, false);
        mUsersDatabase=FirebaseDatabase.getInstance().getReference().child("Users");
        list=(RecyclerView)mMainView.findViewById(R.id.list);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Complaints").child("Health");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    public static class ComplaintsViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public ComplaintsViewHolder(View v)
        {
            super(v);
            mView=v;
        }

        public void setName(String s)
        {
            TextView name=(TextView)mView.findViewById(R.id.user_name);
            name.setText(s);
        }

        public void setComplain(String s)
        {
            TextView complain=(TextView)mView.findViewById(R.id.user_complain);
            complain.setText(s);
        }

        public void setImage(String thumb_image, Context ctx)
        {
            CircleImageView userImageView=(CircleImageView)mView.findViewById(R.id.users_image);
            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.images).into(userImageView);
        }

    }

    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Citizen,HealthFragment.ComplaintsViewHolder> complaintRecyclerViewAdapter=new FirebaseRecyclerAdapter<Citizen, ComplaintsViewHolder>(Citizen.class,R.layout.user_single_layout,HealthFragment.ComplaintsViewHolder.class,mDatabase) {
            @Override
            protected void populateViewHolder(final ComplaintsViewHolder viewHolder, Citizen model, int position) {
                final String user_id=getRef(position).getKey();
                mDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //String from=dataSnapshot.child("From").getValue().toString();
                        String message=dataSnapshot.child("Message").getValue().toString();
                        mUsersDatabase.child(user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String fname=dataSnapshot.child("First_Name").getValue().toString();
                                String lname=dataSnapshot.child("Last_Name").getValue().toString();
                                String image=dataSnapshot.child("image").getValue().toString();
                                String name=fname+" "+lname;
                                viewHolder.setName(name);
                                viewHolder.setImage(image,getContext());

                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Intent chatIntent=new Intent(getContext(),ChatActivity.class);
                                        chatIntent.putExtra("sender","Health");
                                        chatIntent.putExtra("reciever",user_id);
                                        startActivity(chatIntent);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        viewHolder.setComplain(message);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        list.setAdapter(complaintRecyclerViewAdapter);
    }

}
