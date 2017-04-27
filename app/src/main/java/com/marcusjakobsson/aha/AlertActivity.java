package com.marcusjakobsson.aha;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


public class AlertActivity extends MyOBTBrushListener{

    private static CountDownTimer timer;
    private static CountDownTimer timerBrush;
    private final long tenMin = 15*60*1000;
    private final long halfHour = 30*60*1000;
    private final long hour = 60*60*1000;
    private boolean doneBrushing = false;
    private final long validBrushingSession = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        //Samtliga flaggor tillåter denna aktivitet att visas när skärmen är släckt i locked mode
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        timerBrush = new CountDownTimer(halfHour, tenMin - 30*60*1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Constants.getRingtone().play();
            }

            @Override
            public void onFinish() {
                stopAlarm();
            }
        };

        timer = new CountDownTimer(hour, halfHour)
        {

            @Override
            public void onTick(long millisUntilFinished) //Var 15:e minut kommer följande exekveras
            {
                Log.i("TICK", "Tick "+millisUntilFinished);
            } //Kan användas för att skriva ut varje tick

            public void onFinish() //När timern är färdig kommer följande exekveras
            {
                Log.i("TICK", "Finished!");
                setContentView(R.layout.activity_alert);
                timerBrush.start();
            }

        }.start();
    }//End onCreate



    private void stopAlarm()
    {
        Constants.getRingtone().stop();
        timer.cancel();
        timerBrush.cancel();

        Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
        startActivity(intent);
    }



    @Override
    public void onBrushDisconnected() {
        Log.i("BRUSH", "Brush disconnected");
        if(doneBrushing)
        {
            stopAlarm();
        }else
        {
            Toast.makeText(this, "Du uppnådde inte den rekommenderade tandborstningstiden", Toast.LENGTH_LONG).show();
        }

        //OBTSDK.authorizeApplication(userAuthListener);

    }


    @Override
    public void onBrushingTimeChanged(long l) {
        Log.i("TIME", String.valueOf(l/1000));
        //TODO ladda upp användarens ej slutgiltiga eller slutgiltiga tb-tider.
        if (l/1000 > validBrushingSession)
        {
            doneBrushing = true; //En godkänd tb-session
        }

    }




    @Override
    public void onBackPressed() {
    }
}//End AlertActivity
