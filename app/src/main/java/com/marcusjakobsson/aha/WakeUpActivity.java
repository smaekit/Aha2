package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WakeUpActivity extends AppCompatActivity {

    ListView wakeUpTimeTableListView;
    Button buttonNext;
    String wakeUptime;
    View activeRow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);

        createList();
    }//End of onCreate




    /**
     * createList() kommer att lägga till samtliga element ifrån wakeUpTimeTable arrayen till
     * listView:n i aktiviteten activity_wake_up.
     */
    private void createList()
    {
        final String[] wakeUpTimeTable = {"06:00", "06:30", "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00"};

        ArrayAdapter arrayAdapter = new CustomAdapter(this, wakeUpTimeTable);
        wakeUpTimeTableListView.setAdapter(arrayAdapter);

        wakeUpTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Item","Item clicked "+view);
                if(view == activeRow) //Villkor för att avmarkera en rad om man trycker på den två gånger
                {
                    Log.i("Row","Same row clicked "+activeRow);
                    buttonNext.setEnabled(false);
                    activeRow.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
                    activeRow = null;
                }
                else
                {
                    activeRow = view;
                    activeRow.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                    unSelectList(activeRow); //Om man trycker på en rad kommer denna anropas för att avmarkera andra markerade rader.
                    buttonNext.setEnabled(true);
                }

                wakeUptime = String.valueOf(parent.getItemAtPosition(position));

            }

        });
    }//End of createList




    public void button_next(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("wakeUpTime", wakeUptime).apply(); //Sparar permanent i variablen wakeUpTime
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }//End of button_next




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
        startActivity(intent);
    }//End of button_back




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
    }//End of unSelectedList
}
