package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SleepActivity extends AppCompatActivity {

    ListView sleepTimeTableListView;
    String sleepTime;
    Button buttonNext;
    View activeRow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        sleepTimeTableListView = (ListView) findViewById(R.id.sleepTimeTableListView);


        createList();
    }//End of onCreate




    /**
     * createList() kommer att lägga till samtliga element ifrån sleepTimeTable arrayen till
     * listView:n i aktiviteten activity_sleep.
     */
    private void createList(){
        String[] sleepTimeTable = {"17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "20:30", "21:00", "21:30", "22:00"};
        ArrayAdapter arrayAdapter = new CustomAdapter(this, sleepTimeTable);
        sleepTimeTableListView.setAdapter(arrayAdapter);

        sleepTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Item","Item clicked "+view);
                if(view == activeRow)//Villkor för att avmarkera en rad om man trycker på den två gånger
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
                sleepTime = String.valueOf(parent.getItemAtPosition(position));
            }
        });
    }//End of createList




    public void button_next(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("sleepTime", sleepTime).apply(); //Sparar permanent i variablen sleepTime
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }//End of button_next




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
    }//End of button_back




    /**
     * Anropas för att iterera genom ListView:n och avmarkera de rader som är markerade.
     * Används vid de tillfällen då man inte vill ha två rader markerade i samma View.
     * Argumentet ska vara den rad som användaren har tryckt på.
     */
    private void unSelectList(View view){
        int count = sleepTimeTableListView.getChildCount();

        for(int i = 0; i < count; i++){
            View row = sleepTimeTableListView.getChildAt(i);
            if(row != view)
                row.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
        }
    }//End of unSelectList
}
