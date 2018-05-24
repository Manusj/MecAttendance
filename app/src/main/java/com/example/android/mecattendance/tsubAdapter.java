package com.example.android.mecattendance;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srj on 7/3/18.
 */

public class tsubAdapter extends ArrayAdapter<tsub> {

    private static final String LOG_TAG = tsubAdapter.class.getSimpleName();

    public tsubAdapter(Activity context, ArrayList<tsub> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context,0,words);
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.timetable_sub, parent, false);
        }
        final tsub sub=getItem(position);
        TextView subj=(TextView) listItemView.findViewById(R.id.tsub);
        subj.setText(sub.getSubject().trim());
        return listItemView;
    }
}
