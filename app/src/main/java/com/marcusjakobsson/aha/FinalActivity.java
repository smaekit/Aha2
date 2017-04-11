package com.marcusjakobsson.aha;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {} //Kan användas för att skriva ut varje tick

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),WaitActivity.class);
                startActivity(intent);
            }

        }.start();
    } //End onCreate



    public void button_back(View view){
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }
} //End FinalActivity
