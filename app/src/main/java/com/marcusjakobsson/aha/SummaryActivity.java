package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SummaryActivity extends AppCompatActivity {

    TextView name;
    TextView wakeUpTime;
    TextView sleepTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        name = (TextView) findViewById(R.id.text_name);
        wakeUpTime = (TextView) findViewById(R.id.text_wakeup);
        sleepTime = (TextView) findViewById(R.id.text_sleep);


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name",""));
        wakeUpTime.setText(sharedPreferences.getString("wakeUpTime",""));
        sleepTime.setText(sharedPreferences.getString("sleepTime",""));
    }

    public void button_next(View view)
    {
        //TODO: När denna knapp har klickats skall samtlig sparad data i SharedPreferences lagras
        Intent intent = new Intent(getApplicationContext(),FinalActivity.class);
        startActivity(intent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }
}
