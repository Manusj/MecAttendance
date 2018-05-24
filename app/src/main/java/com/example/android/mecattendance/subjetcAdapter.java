package com.example.android.mecattendance;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by srj on 28/1/18.
 */
public class subjetcAdapter extends ArrayAdapter<Subject> {

    private static final String LOG_TAG = subjetcAdapter.class.getSimpleName();

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param words   A List of Word objects to display in a list
     */
    public subjetcAdapter(Activity context, ArrayList<Subject> words) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.subject, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final Subject subject=getItem(position);
        TextView subname=(TextView) listItemView.findViewById(R.id.subname);
        subname.setText(String.valueOf(subject.getSubject_name().trim()));
        subname=(TextView) listItemView.findViewById(R.id.bunkable);
        if(subject.getBunkable()<0)
            subname.setText(String.valueOf("Attend next "+(-subject.getBunkable())+" classes"));
        else
            subname.setText(String.valueOf("Can Bunk "+(subject.getBunkable())+" classes"));
        subname=(TextView) listItemView.findViewById(R.id.attn);
        if (subject.getAttnper()==0)
            subname.setText("---");
        else
            subname.setText(String.valueOf(Float.toString(subject.getAttnper())));

        if (subject.getAttnper()==0)
            subname.setTextColor(Color.parseColor("#00695c"));
        else if (subject.getAttnper()<75)
            subname.setTextColor(Color.parseColor("#c62828"));
        else
            subname.setTextColor(Color.parseColor("#00600f"));
        subname=(TextView) listItemView.findViewById(R.id.lastmodify);
        if (subject.getLastmodify().trim().equals("No entry yet"))
        {
            subname=(TextView) listItemView.findViewById(R.id.bunkable);
            subname.setText("---------------------------");
            subname=(TextView) listItemView.findViewById(R.id.lastmodify);
            subname.setText(subject.getLastmodify());
        }
        else
            subname.setText(String.valueOf("Last Modified on "+subject.getLastmodify()));

        return listItemView;
    }
}
