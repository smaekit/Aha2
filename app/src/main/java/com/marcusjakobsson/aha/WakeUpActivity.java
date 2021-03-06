package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class WakeUpActivity extends AppCompatActivity implements TimeTables{

    private SharedPreferences sharedPreferences;
    private ListView wakeUpTimeTableListView;
    private Button buttonNext;
    private String wakeUptime;
    private int savedPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);
        sharedPreferences = Constants.getSharedPreferences();
        savedPosition = sharedPreferences.getInt("wSavedP", -10);

        createList();
    }//End of onCreate




    /**
     * createList() kommer att lägga till samtliga element ifrån wakeUpTimeTable arrayen till
     * listView:n i aktiviteten activity_wake_up.
     */
    @Override
    public void createList()
    {
        final String[] wakeUpTimeTable = {"05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00"};

        final CustomAdapter arrayAdapter = new CustomAdapter(this, stringArrToTextList(wakeUpTimeTable));
        wakeUpTimeTableListView.setAdapter(arrayAdapter);

        if(savedPosition != -10){
            arrayAdapter.setSelectedIndex(savedPosition);
            buttonNext.setEnabled(true);
            wakeUptime = arrayAdapter.getItem(savedPosition).getText().toString();
        }

        wakeUpTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Item","Item clicked "+view);
                if(savedPosition == position) //Villkor för att avmarkera en rad om man trycker på den två gånger
                {
                    Log.i("Row","Same row clicked");
                    buttonNext.setEnabled(false);
                    arrayAdapter.setSelectedIndex(-1);
                    savedPosition = -10;
                }
                else
                {
                    savedPosition = position;
                    arrayAdapter.setSelectedIndex(position);
                    buttonNext.setEnabled(true);
                }

                wakeUptime = String.valueOf(arrayAdapter.getItem(position).getText());

            }

        });

    }//End of createList




    /**
     * stringArrToTextList(timeTable) tar in en string array och returnerar
     * en lista av TextViews innehållandes alla strings i arrayen.
     */
    @Override
    public ArrayList<TextView> stringArrToTextList(String[] timeTable) {
        ArrayList<TextView> tList = new ArrayList<>();

        for (String aTimeTable : timeTable) {
            TextView t = new TextView(this);
            t.setText(aTimeTable);
            tList.add(t);
        }

        return tList;
    }




    public void button_next(View view)
    {
        sharedPreferences.edit().putString("wakeUpTime", wakeUptime).apply(); //Sparar permanent i variablen wakeUpTime
        sharedPreferences.edit().putInt("wSavedP", savedPosition).apply();
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_next




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_back




    @Override
    public void onBackPressed() {
    }
}
