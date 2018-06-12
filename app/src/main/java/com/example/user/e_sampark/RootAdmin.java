package com.example.user.e_sampark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class RootAdmin extends AppCompatActivity {

    private ViewPager mViewPager;
    private SectionsPagerAdapter1 mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filed_complaints);

        mToolbar=(Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Admin");

        mViewPager=(ViewPager)findViewById(R.id.tab_pager);
        mSectionsPagerAdapter=new SectionsPagerAdapter1(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout=(TabLayout)findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu1,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_button) {
            FirebaseAuth.getInstance().signOut();
            Intent in = new Intent(this, SignUpActivity.class);
            startActivity(in);
            finish();
        }
        return true;
    }
}
