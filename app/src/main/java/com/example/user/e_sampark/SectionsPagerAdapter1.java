package com.example.user.e_sampark;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

    public SectionsPagerAdapter1(FragmentManager fm)
    {
        super(fm);
    }

    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0:
                HealthFragment healthFragment=new HealthFragment();
                return healthFragment;
            case 1:
                EducationFragment1 educationFragment=new EducationFragment1();
                return educationFragment;
            default:
                SecurityFragment1 securityFragment=new SecurityFragment1();
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
