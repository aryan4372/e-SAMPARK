package com.example.user.e_sampark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        mToolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("e-Sampark");


    }

    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent startIntent=new Intent(this,SignUpActivity.class);
            startActivity(startIntent);
            finish();
        }
        else
        {
            String uid=currentUser.getUid();
            if(uid.equals("LjyemkeOSSNfBwGrLq0y1DfbWm82"))
            {
                Intent healthIntent=new Intent(this,Health.class);
                startActivity(healthIntent);
                finish();
            }

            else if(uid.equals("eajEq6tag6QizGC1BEXc9nbDrWu1"))
            {
                Intent educationIntent=new Intent(this,Education.class);
                startActivity(educationIntent);
                finish();
            }

            else if(uid.equals("6LoIFdoNDCRRmooILXDjHPDlTow1"))
            {
                Intent educationIntent=new Intent(this,Security.class);
                startActivity(educationIntent);
                finish();
            }
            else if(uid.equals("watN76oueOQGhNVEoB3X4jXEHHh1"))
            {
                Intent educationIntent=new Intent(this,RootAdmin.class);
                startActivity(educationIntent);
                finish();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.main_logout_button)
        {
            FirebaseAuth.getInstance().signOut();
            Intent in=new Intent(this,SignUpActivity.class);
            startActivity(in);
            finish();
        }

        else if(item.getItemId()==R.id.main_account)
        {
            Intent in=new Intent(this,Account.class);
            startActivity(in);
        }

        else if(item.getItemId()==R.id.main_dm)
        {
            Intent in=new Intent(this,DM.class);
            startActivity(in);
        }
        else if(item.getItemId()==R.id.main_complaints)
        {
            Intent in=new Intent(this,Complaints.class);
            startActivity(in);
        }

        return true;
    }

    public void listColleges(View v)
    {
        Intent in=new Intent(this,Colleges.class);
        startActivity(in);
    }

    public void listHospitals(View v)
    {
        Intent in=new Intent(this,Hospitals.class);
        startActivity(in);
    }
}
