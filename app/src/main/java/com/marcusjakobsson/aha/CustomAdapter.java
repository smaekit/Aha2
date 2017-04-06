package com.marcusjakobsson.aha;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by konstantin_ay on 2017-04-06.
 */

public class CustomAdapter extends ArrayAdapter<String>{

    CustomAdapter(Context context, String[] time) {
        super(context, R.layout.time_row , time);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.time_row, parent, false);

        String time = getItem(position);
        TextView timeTextView = (TextView) customView.findViewById(R.id.time_text);

        timeTextView.setText(time);
        return customView;
    }
}
