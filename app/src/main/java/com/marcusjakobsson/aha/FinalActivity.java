package com.marcusjakobsson.aha;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.i("Time", "Tick");
            }

            public void onFinish() {
                setContentView(R.layout.activity_waiting);
            }

        }.start();
    }

    public void button_back(View view){
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }
}
