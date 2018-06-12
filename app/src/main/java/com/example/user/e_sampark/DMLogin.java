package com.example.user.e_sampark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DMLogin extends AppCompatActivity {

    private EditText password;
    private Button btn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmlogin);

        mAuth=FirebaseAuth.getInstance();

        password=(EditText)findViewById(R.id.admin);
        btn=(Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=password.getText().toString();
                password.setText("");
                if(s.equals("rootadmin"))
                {
                    mProgress=new ProgressDialog(DMLogin.this);
                    mProgress.setTitle("Official");
                    mProgress.setMessage("Checking Credentials");
                    mProgress.show();
                    String email="dm@gmail.com";

                    mAuth.signInWithEmailAndPassword(email,s).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult)
                        {
                            mProgress.dismiss();
                            Intent in = new Intent(DMLogin.this, RootAdmin.class);
                            startActivity(in);
                            finish();
                        }
                    });

                }
                else
                {
                    mProgress.dismiss();
                    Toast.makeText(DMLogin.this,"wrong password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
