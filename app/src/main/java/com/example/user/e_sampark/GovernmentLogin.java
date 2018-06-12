package com.example.user.e_sampark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GovernmentLogin extends AppCompatActivity {

    private int Selected_Sector=0;
    private EditText text;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_login);

        text=(EditText)findViewById(R.id.admin);
        mAuth=FirebaseAuth.getInstance();
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_health:
                if (checked)
                {
                    Selected_Sector=1;
                }
                break;
            case R.id.radio_education:
                if (checked)
                {
                    Selected_Sector=2;
                }
                break;
            case R.id.radio_security:
                if(checked)
                {
                    Selected_Sector=3;
                }
                break;
        }
    }

    public void login(View v)
    {
        String password=text.getText().toString();
        if(Selected_Sector==0)
        {
            Toast.makeText(this,"No Sector Selected",Toast.LENGTH_LONG).show();
        }
        else if(!password.equals("admin"))
        {
            Toast.makeText(this,"Wrong Password",Toast.LENGTH_LONG).show();
        }
        else
        {
            mProgress=new ProgressDialog(GovernmentLogin.this);
            mProgress.setTitle("Official");
            mProgress.setMessage("Checking Credentials");
            mProgress.show();
            if (password.equals("admin") && Selected_Sector == 1) {
                String pass="HealthAdmin";
                String email="Health@gmail.com";
                mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mProgress.dismiss();
                        Intent in = new Intent(GovernmentLogin.this, Health.class);
                        startActivity(in);
                        finish();
                    }
                });
            }
            if (password.equals("admin") && Selected_Sector == 2) {
                String email="Education@gmail.com";
                String pass="EducationAdmin";
                mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mProgress.dismiss();
                        Intent in = new Intent(GovernmentLogin.this, Education.class);
                        startActivity(in);
                        finish();
                    }
                });
            }
            if (password.equals("admin") && Selected_Sector == 3) {
                String email="Security@gmail.com";
                String pass="SecurityAdmin";
                mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        mProgress.dismiss();
                        Intent in = new Intent(GovernmentLogin.this, Security.class);
                        startActivity(in);
                        finish();
                    }
                });
            }
        }
    }

    public void dm(View v)
    {
        Intent in=new Intent(this,DMLogin.class);
        startActivity(in);
        finish();
    }


}
