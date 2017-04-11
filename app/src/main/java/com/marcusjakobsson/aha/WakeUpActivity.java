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

public class WakeUpActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ListView wakeUpTimeTableListView;
    Button buttonNext;
    String wakeUptime;
    String time;
    int savedPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);
        sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        savedPosition = sharedPreferences.getInt("wSavedP", -10);

        createList();
    }//End of onCreate




    /**
     * createList() kommer att lägga till samtliga element ifrån wakeUpTimeTable arrayen till
     * listView:n i aktiviteten activity_wake_up.
     */
    private void createList()
    {
        final String[] wakeUpTimeTable = {"06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00"};

        final CustomAdapter arrayAdapter = new CustomAdapter(this, stringArrToTextList(wakeUpTimeTable));
        wakeUpTimeTableListView.setAdapter(arrayAdapter);

        if(savedPosition != -10){
            arrayAdapter.setSelectedIndex(savedPosition);
            buttonNext.setEnabled(true);
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
                    wakeUptime = "";
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

    private void updateList(CustomAdapter arrayAdapter) {

        for(int i = 0; i < arrayAdapter.getCount();i++)
        {
            TextView t = arrayAdapter.getItem(i);
            if(t.getText().toString().equals(time)){
                arrayAdapter.setSelectedIndex(i);
                break;
            }
        }
    }

    private ArrayList<TextView> stringArrToTextList(String[] timeTable) {
        ArrayList<TextView> tList = new ArrayList<>();

        for(int i = 0; i < timeTable.length;i++){
            TextView t = new TextView(this);
            t.setText(timeTable[i]);
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
    }//End of button_next




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
        startActivity(intent);
    }//End of button_back




    /*
    /**
     * Anropas för att iterera genom ListView:n och avmarkera de rader som är markerade.
     * Används vid de tillfällen då man inte vill ha två rader markerade i samma View.
     * Argumentet ska vara den rad som användaren har tryckt på.
     */
    private void unSelectList(View clickedRow){
        int numOfChildren = wakeUpTimeTableListView.getChildCount();
        Log.i("Count", String.valueOf(numOfChildren));

        for(int i = 0; i < numOfChildren; i++){
            View row = wakeUpTimeTableListView.getChildAt(i);

            //Kontrollerar om den nuvarande View:n är samma View som den användaren klickade på.
            if(row != clickedRow)
                row.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
        }
    }//End of unSelectedList*/
}
