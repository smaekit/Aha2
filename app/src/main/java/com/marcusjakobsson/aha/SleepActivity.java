package com.marcusjakobsson.aha;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SleepActivity extends AppCompatActivity {

    ListView sleepTimeTableListView;
    String sleepTime;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        buttonNext = (Button) findViewById(R.id.button_next);

        final ArrayList<String> sleepTimeTable = new ArrayList<>();
        sleepTimeTableListView = (ListView) findViewById(R.id.sleepTimeTableListView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.time_row,R.id.time_text,sleepTimeTable);

        sleepTimeTable.add("17.00");
        sleepTimeTable.add("17.30");
        sleepTimeTable.add("18.00");
        sleepTimeTable.add("18.30");
        sleepTimeTable.add("19.00");
        sleepTimeTable.add("19.30");
        sleepTimeTable.add("20.00");
        sleepTimeTable.add("20.30");
        sleepTimeTable.add("21.00");
        sleepTimeTable.add("21.30");
        sleepTimeTable.add("22.00");
        sleepTimeTable.add("22.30");
        sleepTimeTable.add("23.00");
        sleepTimeTableListView.setAdapter(arrayAdapter);

        sleepTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                sleepTime = sleepTimeTable.get(position).toString();

            }
        });
    }

    public void button_next(View view)
    {
        /*Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        intent.putExtra("wakeuptime", wakeuptime);
        startActivity(intent);*/
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
    }

}
