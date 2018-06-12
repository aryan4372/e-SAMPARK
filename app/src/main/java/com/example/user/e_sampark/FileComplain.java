package com.example.user.e_sampark;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileComplain extends AppCompatActivity {

    private int Selected_Sector=0;
    private EditText mText;
    private DatabaseReference mDatabase;
    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private String current_user;
    private DatabaseReference mRoot;
    private Button mButton;
    private RadioButton r1;
    private RadioButton r2;
    private RadioButton r3;
    private int x=0;
    private boolean hasHealth;
    private boolean hasEducation;
    private boolean hasSeurity;
    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_complain);
        mText=(EditText)findViewById(R.id.complain);
        mButton=(Button)findViewById(R.id.mButton);
        //mButton.is

        r1=(RadioButton)findViewById(R.id.radio_health);
        r2=(RadioButton)findViewById(R.id.radio_education);
        r3=(RadioButton)findViewById(R.id.radio_security);

        r1.setChecked(false);
        r2.setChecked(false);
        r3.setChecked(false);

        mAuth=FirebaseAuth.getInstance();
        current_user=mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Complaints");
        mUsersDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(current_user).child("Filed_A_Complain");
        mRoot=FirebaseDatabase.getInstance().getReference();
        mDatabase.child(current_user).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Health"))
                {
                    hasHealth=true;
                }
                if(dataSnapshot.hasChild("Education"))
                {
                    hasEducation=true;
                }
                if(dataSnapshot.hasChild("Security"))
                {
                    hasSeurity=true;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_health:
                if (checked)
                {
                    Selected_Sector=1;
                    if(hasHealth)
                    {
                        Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Health Sector", Toast.LENGTH_LONG).show();
                        mButton.setVisibility(View.GONE);
                    }
                    else
                    {
                        mButton.setVisibility(View.VISIBLE);
                    }
                    /*
                    mDatabase.child(current_user).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("Health"))
                            {
                                if(x==0) {
                                    Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Health Sector", Toast.LENGTH_LONG).show();
                                    mButton.setVisibility(View.GONE);
                                    r1.setChecked(false);
                                }
                            }
                            else
                            {
                                mButton.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    */
                }
                break;
            case R.id.radio_education:
                if (checked)
                {
                    Selected_Sector=2;
                    if(hasEducation)
                    {
                        Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Education Sector", Toast.LENGTH_LONG).show();
                        mButton.setVisibility(View.GONE);
                    }
                    else
                    {
                        mButton.setVisibility(View.VISIBLE);
                    }
                    /*
                    mDatabase.child(current_user).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("Education"))
                            {
                                if(x==0) {
                                    Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Education Sector", Toast.LENGTH_LONG).show();
                                    mButton.setVisibility(View.GONE);
                                    r2.setChecked(false);
                                }
                            }
                            else
                            {
                                mButton.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    */

                }
                break;
            case R.id.radio_security:
                if(checked)
                {
                    Selected_Sector=3;
                    if(hasSeurity)
                    {
                        Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Security Sector", Toast.LENGTH_LONG).show();
                        mButton.setVisibility(View.GONE);
                    }
                    else
                    {
                        mButton.setVisibility(View.VISIBLE);
                    }
                    /*
                    mDatabase.child(current_user).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("Security"))
                            {
                                if(x==0) {
                                    Toast.makeText(FileComplain.this, "You Already Have A Complain Filed Against Security Sector", Toast.LENGTH_LONG).show();
                                    mButton.setVisibility(View.GONE);
                                    r3.setChecked(false);
                                }
                            }
                            else
                            {
                                mButton.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    */

                }
                break;
        }
    }

    public void send(View v)
    {
        x=1;
        if(Selected_Sector==0)
        {
            Toast.makeText(FileComplain.this,"Please Select Any One Sector",Toast.LENGTH_LONG).show();
        }
        else if(mText.getText().toString().equals(""))
        {
            Toast.makeText(FileComplain.this,"An Empty Complain Can Not Be Filed",Toast.LENGTH_LONG).show();
        }
        else {
            final String complain = mText.getText().toString();
            mText.setText("");
            if (!complain.equals("") && Selected_Sector != 0) {
                if (Selected_Sector == 2) {
                                    String sender = "Messages/" + current_user + "/Education";
                                    String receiver = "Messages/Education/" + current_user;
                                    DatabaseReference user_message_push = mRoot.child("Messages").child(current_user).child("Education").push();
                                    String push_id = user_message_push.getKey();
                                    Map messageMap = new HashMap();
                                    messageMap.put("message", complain);
                                    messageMap.put("from", current_user);

                                    Map messageUserMap = new HashMap();
                                    messageUserMap.put(sender + "/" + push_id, messageMap);
                                    messageUserMap.put(receiver + "/" + push_id, messageMap);

                                    mRoot.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                mUsersDatabase.setValue("Yes");
                                                mDatabase.child(current_user).child("Education").child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mDatabase.child("Education").child(current_user).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(FileComplain.this, "Complain Filed", Toast.LENGTH_LONG).show();
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });

                } else if (Selected_Sector == 1) {
                /*
                DatabaseReference user_message_push=mDatabase.child("Health").push();
                final String push_id=user_message_push.getKey();
                mDatabase.child("Health").child(push_id).child("From").setValue(current_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabase.child("Health").child(push_id).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mUsersDatabase.setValue("Yes");
                                Toast.makeText(FileComplain.this,"Complain Filed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                */


                    String sender = "Messages/" + current_user + "/Health";
                    String receiver = "Messages/Health/" + current_user;
                    DatabaseReference user_message_push = mRoot.child("Messages").child(current_user).child("Health").push();
                    String push_id = user_message_push.getKey();
                    Map messageMap = new HashMap();
                    messageMap.put("message", complain);
                    messageMap.put("from", current_user);

                    Map messageUserMap = new HashMap();
                    messageUserMap.put(sender + "/" + push_id, messageMap);
                    messageUserMap.put(receiver + "/" + push_id, messageMap);

                    mRoot.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                mUsersDatabase.setValue("Yes");
                                mDatabase.child(current_user).child("Health").child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mDatabase.child("Health").child(current_user).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(FileComplain.this, "Complain Filed", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });



                    /*
                    DatabaseReference user_message_push = mDatabase.child("Health").push();
                    final String push_id = user_message_push.getKey();
                    mDatabase.child("Health").child(push_id).child("From").setValue(current_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabase.child("Health").child(push_id).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    String sender = "Messages/" + current_user + "/Health";
                                    String receiver = "Messages/Health/" + current_user;
                                    DatabaseReference user_message_push = mRoot.child("Messages").child(current_user).child("Health").push();
                                    String push_id = user_message_push.getKey();
                                    Map messageMap = new HashMap();
                                    messageMap.put("message", complain);
                                    messageMap.put("from", current_user);

                                    Map messageUserMap = new HashMap();
                                    messageUserMap.put(sender + "/" + push_id, messageMap);
                                    messageUserMap.put(receiver + "/" + push_id, messageMap);

                                    mRoot.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                mUsersDatabase.setValue("Yes");
                                                x=0;
                                                //Toast.makeText(FileComplain.this,"Complain Filed",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                }
                            });
                        }
                    });


                    mDatabase.child(current_user).child("Health").child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FileComplain.this, "Complain Filed", Toast.LENGTH_LONG).show();
                            x=0;
                        }
                    });

                    */


                } else if (Selected_Sector == 3) {
                /*
                DatabaseReference user_message_push=mDatabase.child("Security").push();
                final String push_id=user_message_push.getKey();
                mDatabase.child("Security").child(push_id).child("From").setValue(current_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mDatabase.child("Security").child(push_id).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                mUsersDatabase.setValue("Yes");
                                Toast.makeText(FileComplain.this,"Complain Filed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                */

                    String sender = "Messages/" + current_user + "/Security";
                    String receiver = "Messages/Security/" + current_user;
                    DatabaseReference user_message_push = mRoot.child("Messages").child(current_user).child("Security").push();
                    String push_id = user_message_push.getKey();
                    Map messageMap = new HashMap();
                    messageMap.put("message", complain);
                    messageMap.put("from", current_user);

                    Map messageUserMap = new HashMap();
                    messageUserMap.put(sender + "/" + push_id, messageMap);
                    messageUserMap.put(receiver + "/" + push_id, messageMap);

                    mRoot.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                mUsersDatabase.setValue("Yes");
                                mDatabase.child(current_user).child("Security").child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mDatabase.child("Security").child(current_user).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(FileComplain.this, "Complain Filed", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });



                /*

                    DatabaseReference user_message_push = mDatabase.child("Security").push();
                    final String push_id = user_message_push.getKey();
                    mDatabase.child("Security").child(push_id).child("From").setValue(current_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mDatabase.child("Security").child(push_id).child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    String sender = "Messages/" + current_user + "/Security";
                                    String receiver = "Messages/Security/" + current_user;
                                    DatabaseReference user_message_push = mRoot.child("Messages").child(current_user).child("Security").push();
                                    String push_id = user_message_push.getKey();
                                    Map messageMap = new HashMap();
                                    messageMap.put("message", complain);
                                    messageMap.put("from", current_user);

                                    Map messageUserMap = new HashMap();
                                    messageUserMap.put(sender + "/" + push_id, messageMap);
                                    messageUserMap.put(receiver + "/" + push_id, messageMap);

                                    mRoot.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if (databaseError == null) {
                                                mUsersDatabase.setValue("Yes");
                                                x=0;
                                                //Toast.makeText(FileComplain.this,"Complain Filed",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });


                                }
                            });
                        }
                    });


                    mDatabase.child(current_user).child("Security").child("Message").setValue(complain).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FileComplain.this, "Complain Filed", Toast.LENGTH_LONG).show();
                            x=0;
                        }
                    });
                */

                }
            }
        }
    }
}
