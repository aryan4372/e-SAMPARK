package com.example.user.e_sampark;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private String sender;
    private String reciever;
    private EditText mMessage;
    private ImageButton mSend;

    private RecyclerView mMessageList;
    private final List<Messages> messagesList=new ArrayList();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter mAdapter;
    private DatabaseReference mRoot;
    private FirebaseAuth mAuth;
    private String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mToolbar=(Toolbar)findViewById(R.id.chat_app_bar);
        setSupportActionBar(mToolbar);

        mMessage=(EditText)findViewById(R.id.mMessage);
        mSend=(ImageButton)findViewById(R.id.mSend);

        mAuth=FirebaseAuth.getInstance();
        current_user=mAuth.getCurrentUser().getUid();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        sender=getIntent().getStringExtra("sender");
        reciever=getIntent().getStringExtra("reciever");

        mAdapter=new MessageAdapter(messagesList);
        mMessageList=(RecyclerView)findViewById(R.id.chat_screen);
        mLinearLayout=new LinearLayoutManager(this);
        mMessageList.setHasFixedSize(true);
        mMessageList.setLayoutManager(mLinearLayout);
        mMessageList.setAdapter(mAdapter);
        mRoot= FirebaseDatabase.getInstance().getReference();
        loadMessages();
    }

    private void loadMessages() {
        DatabaseReference mRef=mRoot.child("Messages").child(sender).child(reciever);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages message=dataSnapshot.getValue(Messages.class);
                String from=message.getFrom();
                if(current_user.equals("LjyemkeOSSNfBwGrLq0y1DfbWm82") || current_user.equals("eajEq6tag6QizGC1BEXc9nbDrWu1") || current_user.equals("6LoIFdoNDCRRmooILXDjHPDlTow1") || current_user.equals("watN76oueOQGhNVEoB3X4jXEHHh1"))
                {
                    messagesList.add(message);
                    mAdapter.notifyDataSetChanged();
                    mMessageList.scrollToPosition(messagesList.size()-1);
                }
                else
                {
                    if(!from.equals("DM"))
                    {
                        messagesList.add(message);
                        mAdapter.notifyDataSetChanged();
                        mMessageList.scrollToPosition(messagesList.size()-1);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void send(View v)
    {
        String message=mMessage.getText().toString();
        mMessage.setText("");
        if(message.length()>0)
        {
            String sender1="Messages/"+sender+"/"+reciever;
            String receiver="Messages/"+reciever+"/"+sender;
            DatabaseReference user_message_push=mRoot.child("Messages").child(sender).child(reciever).push();
            String push_id=user_message_push.getKey();
            Map messageMap=new HashMap();
            messageMap.put("message",message);
            if(current_user.equals("watN76oueOQGhNVEoB3X4jXEHHh1"))
            {
                messageMap.put("from","DM");
            }
            else
            {
                messageMap.put("from", sender);
            }
            Map messageUserMap=new HashMap();
            messageUserMap.put(sender1+"/"+push_id,messageMap);
            messageUserMap.put(receiver+"/"+push_id,messageMap);

            mRoot.updateChildren(messageUserMap,new DatabaseReference.CompletionListener()
                    {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError!=null)
                            {
                                Toast.makeText(ChatActivity.this,"ERROR",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );



        }

    }

}
