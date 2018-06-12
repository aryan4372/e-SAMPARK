package com.example.user.e_sampark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.TimeUnit;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {

    private EditText mPhoneNumber;
    private EditText mCodeText;
    private TextView mError;

    private int buttonType=0;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private String mVerificationId;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;

    private Button mButton;

    private ProgressDialog mProgress;
    private ProgressDialog mProgress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mPhoneNumber=(EditText)findViewById(R.id.mPhoneNumber);
        mCodeText=(EditText)findViewById(R.id.mCodeText);
        mError=(TextView) findViewById(R.id.error);

        mAuth=FirebaseAuth.getInstance();

        mButton=(Button)findViewById(R.id.mButton);

       mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
       {
           @Override
           public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
              mVerificationId=verificationId;
              mResendToken=token;
              mButton.setText("Verify Code");
              mButton.setEnabled(true);
              buttonType=1;
              mProgress.dismiss();
              mCodeText.setVisibility(View.VISIBLE);
              mPhoneNumber.setVisibility(View.INVISIBLE);
           }

           @Override
           public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
               mProgress.dismiss();
               signInWithPhoneAuthCredential(phoneAuthCredential);
               //Toast.makeText(SignUpActivity.this,phoneAuthCredential.,Toast.LENGTH_LONG).show();
           }

           @Override
           public void onVerificationFailed(FirebaseException e) {

               mError.setVisibility(View.VISIBLE);
           }
       };

    }

    public void gov(View v)
    {
        Intent in=new Intent(SignUpActivity.this,GovernmentLogin.class);
        startActivity(in);
        finish();
    }

    public void otp(View v)
    {
        if(buttonType==0)
        {
            mPhoneNumber.setEnabled(false);
            mButton.setEnabled(false);

            mProgress=new ProgressDialog(SignUpActivity.this);
            mProgress.setTitle("Sending OTP");
            mProgress.setMessage("Please Wait While OTP Is Being Sent");
            mProgress.show();

            String phone=mPhoneNumber.getText().toString();
            String phoneNumber="+91"+phone;
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,60, java.util.concurrent.TimeUnit.SECONDS,SignUpActivity.this,mCallbacks);

        }
        else
        {
            mButton.setEnabled(false);
            String verificationCode=mCodeText.getText().toString();
            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,verificationCode);
            mProgress1=new ProgressDialog(SignUpActivity.this);
            mProgress1.setTitle("Verifying OTP");
            mProgress1.setMessage("Please Wait While We Verify The OTP");
            mProgress1.show();
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
                    String cur_id=mCurrentUser.getUid();

                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(cur_id);
                    mDatabase1=FirebaseDatabase.getInstance().getReference().child("Users").child(cur_id);
                    mDatabase.child("Completed").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue()==null)
                            {
                                mDatabase1.child("Completed").setValue("No");
                                mDatabase1.child("Filed_A_Complain").setValue("No");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //FirebaseUser user=task.getResult().getUser();
                    //mProgress1.dismiss();
                    Intent mainIntent=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else
                {
                    mProgress1.dismiss();
                    mError.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
