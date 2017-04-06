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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WakeUpActivity extends AppCompatActivity {

    ListView wakeUpTimeTableListView;
    Button buttonNext;
    String wakeuptime;
    View activeRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);
        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setEnabled(false);

        createList();
    }

    private void createList()
    {
        String[] wakeUpTimeTable = {"06.00", "06.30", "07.00", "07.30", "08.00", "08.30", "09.00", "09.30", "10.00", "10.30", "11.00", "11.30", "12.00"};
        wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);
        ArrayAdapter arrayAdapter = new CustomAdapter(this, wakeUpTimeTable);
        wakeUpTimeTableListView.setAdapter(arrayAdapter);

        wakeUpTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
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

                wakeuptime = String.valueOf(parent.getItemAtPosition(position));

            }
        });
    }

    public void button_next(View view)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("wakeUpTime", wakeuptime).apply(); //Sparar permanent i variablen wakeUpTime
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),instructionsActivity.class);
        startActivity(intent);
    }

    private void unSelectList(View view){
        int count = wakeUpTimeTableListView.getChildCount();

        for(int i = 0; i < count; i++){
            View row = wakeUpTimeTableListView.getChildAt(i);
            if(row != view)
                row.setBackgroundColor(getResources().getColor(R.color.colorDefaultRow));
        }
    }
}
