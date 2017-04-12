package com.marcusjakobsson.aha;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class AlertActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        //Samtliga flaggor tillåter denna aktivitet att visas när skärmen är släckt i locked mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


    }//End onCreate



    //Anropas när användaren trycker på tandborsten och kommer omdirigera användare till "sovande katt"-vyn
    public void button_stopAlarm(View view)
    {
        AlarmReceiver.stopRingtone();

        Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
        startActivity(intent);
    }
}//End AlertActivity
