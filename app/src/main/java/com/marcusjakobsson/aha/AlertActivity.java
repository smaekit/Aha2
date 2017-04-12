package com.marcusjakobsson.aha;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

public class AlertActivity extends AppCompatActivity
{
    CountDownTimer timer;
    private final long fifteenMin = 15*60*1000;
    private final long halfHour = 30*60*1000;

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


        timer = new CountDownTimer(halfHour, fifteenMin) {

            public void onTick(long millisUntilFinished) {AlarmReceiver.startRingtone();} //Kan användas för att skriva ut varje tick

            public void onFinish() {
                AlarmReceiver.startRingtone();
                stopAlarm();
            }

        }.start();
    }//End onCreate



    private void stopAlarm()
    {
        AlarmReceiver.stopRingtone();
        timer.cancel();

        Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
        startActivity(intent);
    }



    //Anropas när användaren trycker på tandborsten och kommer omdirigera användare till "sovande katt"-vyn
    public void button_stopAlarm(View view)
    {
        stopAlarm();
    }
}//End AlertActivity
