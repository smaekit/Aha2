package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class WakeUpActivity extends AppCompatActivity {

    ListView wakeUpTimeTableListView;
    Button buttonNext;
    String wakeuptime;
    View activeRow;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);
        sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);
        activeRow = null;
        Log.i("WakeUp", "Creates...");

        createList();
    }

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
                    unSelectList(activeRow);
                    buttonNext.setEnabled(true);
                }

                wakeuptime = String.valueOf(parent.getItemAtPosition(position));

            }

        });
    }

    public void button_next(View view)
    {
        sharedPreferences.edit().putString("wakeUpTime", wakeuptime).apply(); //Sparar permanent i variablen wakeUpTime
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
        startActivity(intent);
    }

    private void unSelectList(View view){
        int count = wakeUpTimeTableListView.getChildCount();
        Log.i("Count", String.valueOf(count));

        for(int i = 0; i < count; i++){
            View row = wakeUpTimeTableListView.getChildAt(i);
            if(row != view)
                row.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
        }
    }
}
