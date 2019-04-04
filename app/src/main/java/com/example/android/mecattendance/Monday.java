package com.example.android.mecattendance;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.name;
import static android.content.Context.MODE_PRIVATE;
import static com.example.android.mecattendance.R.layout.subject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Monday extends Fragment {

    ArrayAdapter<String> adapter;
    public Monday() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String timetable;
        View v = inflater.inflate(R.layout.fragment_monday, container, false);
        ArrayList<tsub> Subjects= new ArrayList<tsub>();
        SharedPreferences p=getActivity().getApplicationContext().getSharedPreferences("timetable", MODE_PRIVATE);
        for(int i=1;i<=6;i++)
        {
            timetable = p.getString(Integer.toString((2*10)+i),"NO DATA");
            Subjects.add(new tsub(timetable));
            Log.v("monday"+Integer.toString((2*10)+i),timetable);
        }
        tsubAdapter adapter = new tsubAdapter(getActivity(),Subjects);
        ListView listView = (ListView) v.findViewById(R.id.moday);
        listView.setAdapter(adapter);


        // Inflate the layout for this fragmentc
        return v;

    }

}
