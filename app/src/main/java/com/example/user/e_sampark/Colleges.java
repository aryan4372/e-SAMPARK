package com.example.user.e_sampark;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Colleges extends AppCompatActivity {

    private Toolbar mToolbar;
    String []colleges;
    String []links;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colleges);

        mToolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Colleges");

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Colleges");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String total=dataSnapshot.child("Total").getValue().toString();
                int length=Integer.parseInt(total);
                colleges=new String[length];
                links=new String[length];
                complete();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void complete()
    {
        for(int i=1;i<=colleges.length;i++)
        {
            final int j=i;
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name=dataSnapshot.child(String.valueOf(j)).child("Name").getValue().toString();
                    colleges[j-1]=name;
                    String link=dataSnapshot.child(String.valueOf(j)).child("Link").getValue().toString();
                    links[j-1]=link;
                    if(j==colleges.length)
                    {
                        complete1();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void complete1()
    {
        ListAdapter myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,colleges);
        ListView myListView =(ListView)findViewById(R.id.myListView);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                String url=links[position];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }


}
