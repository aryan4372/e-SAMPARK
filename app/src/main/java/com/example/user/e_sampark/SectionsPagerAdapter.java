package com.example.user.e_sampark;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                HealthFragment1 healthFragment=new HealthFragment1();
                return healthFragment;
            case 1:
                EducationFragment educationFragment=new EducationFragment();
                return educationFragment;
            default:
                SecurityFragment securityFragment=new SecurityFragment();
                return securityFragment;
        }
    }

    public int getCount()
    {
        return 3;
    }

    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case 0:
                return "Health";
            case 1:
                return "Education";
            default:
                return "Security";
        }
    }

}
