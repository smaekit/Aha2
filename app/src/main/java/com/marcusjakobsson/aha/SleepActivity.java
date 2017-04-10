package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    View activeRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);

        createList();
    }

    private void createList(){
        String[] sleepTimeTable = {"17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "20:30", "21:00", "21:30", "22:00"};
        sleepTimeTableListView = (ListView) findViewById(R.id.sleepTimeTableListView);
        ArrayAdapter arrayAdapter = new CustomAdapter(this, sleepTimeTable);
        sleepTimeTableListView.setAdapter(arrayAdapter);

        sleepTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.i("Item","Item clicked "+view);
                if(view == activeRow)
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
                    unSelectList(activeRow);
                    buttonNext.setEnabled(true);
                }
                sleepTime = String.valueOf(parent.getItemAtPosition(position));
            }
        });
    }

    public void button_next(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("sleepTime", sleepTime).apply(); //Sparar permanent i variablen sleepTime
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
    }

    private void unSelectList(View view){
        int count = sleepTimeTableListView.getChildCount();

        for(int i = 0; i < count; i++){
            View row = sleepTimeTableListView.getChildAt(i);
            if(row != view)
                row.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
        }
    }
}
