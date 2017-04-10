package com.marcusjakobsson.aha;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class AlertActivity extends AppCompatActivity {
    ImageButton stopAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        stopAlarm = (ImageButton) findViewById(R.id.button_stopAlarm);
    }


    /**
     * Anropas när användaren trycker på tandborsten och kommer omdirigera användare
     * till "sovande katt"-vyn
     */
    public void button_stopAlarm(View view){

        AlarmReceiver.stopRingtone();
        SummaryActivity.stopAlarm();

        Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
        startActivity(intent);
    }
}
