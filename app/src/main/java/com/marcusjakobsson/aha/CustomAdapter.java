package com.marcusjakobsson.aha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by konstantin_ay on 2017-04-06.
 * Används för att placera in strängar från en stringarray
 * till en textView
 */

public class CustomAdapter extends ArrayAdapter<String>{

    CustomAdapter(Context context, String[] time) {
        super(context, R.layout.time_row , time);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //TODO byt ut parent i inflater mot converterView
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.time_row, parent, false);

        String time = getItem(position);
        TextView timeTextView = (TextView) customView.findViewById(R.id.time_text);
        timeTextView.setText(time);

        return customView;
    }//End getView
}//End CustomAdapter
