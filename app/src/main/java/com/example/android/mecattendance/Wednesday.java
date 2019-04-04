package com.example.android.mecattendance;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Wednesday extends Fragment {


    public Wednesday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String timetable;
        View v = inflater.inflate(R.layout.fragment_wednesday, container, false);
        ArrayList<tsub> Subjects= new ArrayList<tsub>();
        SharedPreferences p=getActivity().getApplicationContext().getSharedPreferences("timetable", MODE_PRIVATE);
        for(int i=1;i<=6;i++)
        {
            timetable = p.getString(Integer.toString((4*10)+i),"NO DATA");
            Subjects.add(new tsub(timetable));
            Log.v("monday"+Integer.toString((4*10)+i),timetable);
        }
        tsubAdapter adapter = new tsubAdapter(getActivity(),Subjects);
        ListView listView = (ListView) v.findViewById(R.id.wednesday);
        listView.setAdapter(adapter);


        // Inflate the layout for this fragmentc
        return v;
    }

}
