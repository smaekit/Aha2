package com.marcusjakobsson.aha;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.oralb.sdk.OBTBrush;
import com.oralb.sdk.OBTBrushListener;
import com.oralb.sdk.OBTSDK;

import java.util.List;

public class AlertActivity extends AppCompatActivity implements OBTBrushListener{

    private static CountDownTimer timer;
    private static CountDownTimer timerBrush;
    private final long fifteenMin = 15*60*1000;
    private final long halfHour = 30*60*1000;
    private boolean doneBrushing = false;
    private long validBrushingSession = 30;

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

        timerBrush = new CountDownTimer(60000, 10000) {
            @Override
            public void onTick(long millisUntilFinished) {
                AlarmReceiver.startRingtone();
            }

            @Override
            public void onFinish() {
                stopAlarm();
            }
        };

        //30000 ms är en felmarginal för att det sista ticket ska låta
        timer = new CountDownTimer(10000, 1000)
        {

            @Override
            public void onTick(long millisUntilFinished) //Var 15:e minut kommer följande exekveras
            {
                Log.i("TICK", "Tick "+millisUntilFinished);
                //AlarmReceiver.startRingtone();
            } //Kan användas för att skriva ut varje tick

            public void onFinish() //När timern är färdig kommer följande exekveras
            {
                Log.i("TICK", "Finished!");
                setContentView(R.layout.activity_alert);
                timerBrush.start();
            }

        }.start();
    }//End onCreate



    public void stopAlarm()
    {
        AlarmReceiver.stopRingtone();
        timer.cancel();
        timerBrush.cancel();

        Intent intent = new Intent(getApplicationContext(), FinalActivity.class);
        startActivity(intent);
    }




    @Override
    public void onResume() {
        super.onResume();
        // Set this activity as OBTBrushListener
        OBTSDK.setOBTBrushListener(this);
    }




    @Override
    protected void onPause() {
        super.onPause();
        // Remove the OBTBrushListener
        OBTSDK.setOBTBrushListener(null);
    }




    @Override
    public void onNearbyBrushesFoundOrUpdated(List<OBTBrush> nearbyBrushes) {
        Log.i("found brush", "" + nearbyBrushes.size());
        Toast.makeText(this, "Found a brush!", Toast.LENGTH_SHORT).show();
        if (!nearbyBrushes.isEmpty()){
            // Connect to first Oral-B Toothbrush
            try{
                Log.i("Hejsan", "TJABBA");
                OBTSDK.connectToothbrush(nearbyBrushes.get(0), false);

            }
            catch(Exception e)
            {
                Toast.makeText(this, "Kunde inte ansluta tandborsten!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBluetoothError() {
        Toast.makeText(this, "Ett bluetooth problem uppstod, var vänlig star ta om bluetooth och försök igen", Toast.LENGTH_LONG).show();


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
            //TODO visa detta på skärmen.
        }

        //OBTSDK.authorizeApplication(userAuthListener);

    }

    @Override
    public void onBrushConnected() {
        Toast.makeText(this, "Tandborsten är ansluten", Toast.LENGTH_SHORT).show();
        //TODO lägga till en datavy vid connect som visar pressure, tid, mode. Kan vara gömd i kattvyn

    }

    @Override
    public void onBrushConnecting() {
        Log.i("BRUSH", "Brush connecting");
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
    public void onBrushingModeChanged(int i) {

    }

    @Override
    public void onBrushStateChanged(int i) {

    }

    @Override
    public void onRSSIChanged(int i) {

    }

    @Override
    public void onBatteryLevelChanged(float v) {

    }

    @Override
    public void onSectorChanged(int i) {

    }

    @Override
    public void onHighPressureChanged(boolean b) {

    }




    //Anropas när användaren trycker på tandborsten och kommer omdirigera användare till "sovande katt"-vyn
    public void button_stopAlarm(View view)
    {
        stopAlarm();
    }




    @Override
    public void onBackPressed() {
    }
}//End AlertActivity
