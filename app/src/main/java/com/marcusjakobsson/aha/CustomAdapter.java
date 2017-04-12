package com.marcusjakobsson.aha;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by konstantin_ay on 2017-04-06.
 * Används för att placera in strängar från en stringarray
 * till en textView
 */

public class CustomAdapter extends BaseAdapter{

    private final Context context;
    private final ArrayList<TextView> testList;
    private int selectedIndex;
    private final int selectedColor = Color.parseColor("#B5ca814d");
    private final int defaultColor = Color.parseColor("#30FFFFFF");

    CustomAdapter(Context ctx, ArrayList<TextView> text) {
        this.context = ctx;
        this.testList = text;
        selectedIndex = -1;
    }

    /**
     * Kommer att "markera" en angiven rad, dvs en rad på positionen ind
     */
    public void setSelectedIndex(int ind)
    {
        selectedIndex = ind;
        notifyDataSetChanged();
    }//End of setSelectedIndex




    @Override
    public int getCount()
    {
        return testList.size();
    }




    @Override
    public TextView getItem(int position)
    {
        return testList.get(position);
    }//End of getItem




    @Override
    public long getItemId(int position)
    {
        return position;
    }//End of getItemId




    /**
     * Privat klass med syftet att hålla samtliga beståndsdelar i vyn time_row
     */
    private class ViewHolder
    {
        TextView tv;
    }//End of ViewHolder




    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        ViewHolder holder;
        if(convertView == null)
        {
            vi = LayoutInflater.from(context).inflate(R.layout.time_row, null);
            holder = new ViewHolder();

            holder.tv = (TextView) vi.findViewById(R.id.time_text);

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) vi.getTag();
        }

        //Sätter bakgrundsfärgen till antingen vald/ovald utifrån ett angivet positionsindex
        if(selectedIndex != -1 && position == selectedIndex)
        {
            holder.tv.setBackgroundColor(selectedColor);
        }
        else
        {
            holder.tv.setBackgroundColor(defaultColor);
        }
        holder.tv.setText(testList.get(position).getText()); //Sätter texten till respektive text

        return vi;
    }//End of getView




}//End CustomAdapter
