package com.example.android.mecattendance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by srj on 6/3/18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:return new Monday();
            case 1:return new Tuesday();
            case 2:return new Wednesday();
            case 3:return new Thursday();
            case 4:return new Friday();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
