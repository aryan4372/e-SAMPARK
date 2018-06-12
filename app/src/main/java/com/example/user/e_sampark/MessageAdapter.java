package com.example.user.e_sampark;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by USER on 26-02-2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Messages> mMessageList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public MessageAdapter(List<Messages> mMessageList)
    {
        this.mMessageList=mMessageList;
    }

    public MessageViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout,parent,false);
        return new MessageViewHolder(v);
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView messageText;
        public TextView userName;
        public CircleImageView userImage;


        public MessageViewHolder(View v)
        {
            super(v);
            messageText=(TextView)v.findViewById(R.id.message_text);
            userName=(TextView)v.findViewById(R.id.chat_user_name);
            userImage=(CircleImageView)v.findViewById(R.id.chat_user_image);
        }
    }

    public void onBindViewHolder(final MessageViewHolder viewHolder, int i)
    {
        final Messages c=mMessageList.get(i);
        mAuth=FirebaseAuth.getInstance();
        final String current_user=mAuth.getCurrentUser().getUid();
        final String from=c.getFrom();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        if(from.equals("Health") || from.equals("Education") || from.equals("Security"))
        {
            viewHolder.userName.setText(from+" Department");
            viewHolder.messageText.setText(c.getMessage());
            if(from.equals("Health"))
            {
                Picasso.with(viewHolder.userImage.getContext()).load(R.drawable.health).into(viewHolder.userImage);
            }
            else if(from.equals("Education"))
            {
                Picasso.with(viewHolder.userImage.getContext()).load(R.drawable.education).into(viewHolder.userImage);
            }
            else
            {
                Picasso.with(viewHolder.userImage.getContext()).load(R.drawable.security).into(viewHolder.userImage);
            }
        }
        else if(from.equals("DM"))
        {
            viewHolder.userName.setText(from);
            viewHolder.messageText.setText(c.getMessage());
            Picasso.with(viewHolder.userImage.getContext()).load(R.drawable.admin).into(viewHolder.userImage);
        }
        else {
            mDatabase.child("Users").child(from).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String image = dataSnapshot.child("image").getValue().toString();
                    if (from.equals(current_user)) {
                        viewHolder.userName.setText("You");
                    } else {
                        String name = dataSnapshot.child("First_Name").getValue().toString();
                        viewHolder.userName.setText(name);
                    }
                    Picasso.with(viewHolder.userImage.getContext()).load(image).placeholder(R.drawable.images).into(viewHolder.userImage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            viewHolder.messageText.setText(c.getMessage());
        }
    }

    public int getItemCount()
    {
        return mMessageList.size();
    }
}

