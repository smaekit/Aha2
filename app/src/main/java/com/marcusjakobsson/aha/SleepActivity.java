package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SleepActivity extends AppCompatActivity implements TimeTables{

    private SharedPreferences sharedPreferences;
    private ListView sleepTimeTableListView;
    private String sleepTime;
    private Button buttonNext;
    private int savedPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        sleepTimeTableListView = (ListView) findViewById(R.id.sleepTimeTableListView);
        sharedPreferences = Constants.getSharedPreferences();
        savedPosition = sharedPreferences.getInt("sSavedP", -10);


        createList();
    }//End of onCreate




    /**
     * createList() kommer att lägga till samtliga element ifrån sleepTimeTable arrayen till
     * listView:n i aktiviteten activity_sleep.
     */
    @Override
    public void createList(){
        String[] sleepTimeTable = {"17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "24:00"};
        final CustomAdapter arrayAdapter = new CustomAdapter(this, stringArrToTextList(sleepTimeTable));
        sleepTimeTableListView.setAdapter(arrayAdapter);

        if(savedPosition != -10){
            arrayAdapter.setSelectedIndex(savedPosition);
            buttonNext.setEnabled(true);
            sleepTime = arrayAdapter.getItem(savedPosition).getText().toString();
        }

        sleepTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                    sleepTime = "";
                }
                else
                {
                    savedPosition = position;
                    arrayAdapter.setSelectedIndex(position);
                    buttonNext.setEnabled(true);
                }

                sleepTime = String.valueOf(arrayAdapter.getItem(position).getText());
            }
        });
    }//End of createList




    public void button_next(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("sleepTime", sleepTime).apply(); //Sparar permanent i variablen sleepTime
        sharedPreferences.edit().putInt("sSavedP", savedPosition).apply();
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_next




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_back




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




    @Override
    public void onBackPressed() {
    }
}
