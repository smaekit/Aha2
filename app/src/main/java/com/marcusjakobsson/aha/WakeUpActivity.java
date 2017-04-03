package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class WakeUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_up);

        final ArrayList<String> wakeUpTimeTable = new ArrayList<String>();
        ListView wakeUpTimeTableListView = (ListView) findViewById(R.id.wakeUpTimeTableListView);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,wakeUpTimeTable);

        wakeUpTimeTable.add("06.00");
        wakeUpTimeTable.add("06.30");
        wakeUpTimeTable.add("07.00");
        wakeUpTimeTable.add("07.30");
        wakeUpTimeTable.add("08.00");
        wakeUpTimeTable.add("08.30");
        wakeUpTimeTable.add("09.00");
        wakeUpTimeTable.add("09.30");
        wakeUpTimeTable.add("10.00");
        wakeUpTimeTable.add("10.30");
        wakeUpTimeTable.add("11.00");
        wakeUpTimeTable.add("11.30");
        wakeUpTimeTable.add("12.00");
        wakeUpTimeTableListView.setAdapter(arrayAdapter);

        wakeUpTimeTableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(getApplicationContext(),GoToSleepActivity.class);
//                intent.putExtra("wakeuptime", wakeUpTimeTable.get(position).toString());
//                startActivity(intent);
            }
        });
    }
}
