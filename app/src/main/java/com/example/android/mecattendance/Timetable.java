package com.example.android.mecattendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.mecattendance.R.layout.subject;

/**
 * Created by srj on 6/3/18.
 */

public class Timetable extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Timetable.this,Attendence.class));
        overridePendingTransition(
                0,
                R.anim.play_panel_close_background
        );
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);
        getSupportActionBar().setTitle("Timetable");
        getSupportActionBar().setElevation(0);
        Calendar calendar = Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_WEEK);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("MON"));
        tabLayout.addTab(tabLayout.newTab().setText("TUE"));
        tabLayout.addTab(tabLayout.newTab().setText("WED"));
        tabLayout.addTab(tabLayout.newTab().setText("THU"));
        tabLayout.addTab(tabLayout.newTab().setText("FRI"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        switch (day)
        {
            case Calendar.MONDAY:viewPager.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:viewPager.setCurrentItem(1);
                break;
            case Calendar.WEDNESDAY:viewPager.setCurrentItem(2);
                break;
            case Calendar.THURSDAY:viewPager.setCurrentItem(3);
                break;
            case Calendar.FRIDAY:viewPager.setCurrentItem(5);
                break;
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

