package com.marcusjakobsson.aha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.oralb.sdk.OBTSDK;

public class SummaryActivity extends AppCompatActivity {
    //private static MyAlarmManager wakeUpAlarm;
    //private static MyAlarmManager sleepAlarm;
    private SharedPreferences sharedPreferences;
    private TextView name;
    private TextView wakeUpTime;
    private TextView sleepTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        name = (TextView) findViewById(R.id.text_name);
        wakeUpTime = (TextView) findViewById(R.id.text_wakeup);
        sleepTime = (TextView) findViewById(R.id.text_sleep);

        //Initierar samtliga textViews så de visar inmatad information på skärmen
        sharedPreferences = Constants.getSharedPreferences();
        name.setText(sharedPreferences.getString("name",""));
        wakeUpTime.setText(sharedPreferences.getString("wakeUpTime",""));
        sleepTime.setText(sharedPreferences.getString("sleepTime",""));
    }//End of onCreate



    /**
     * Vid bekräftelse av information kommer applikationen att skapa ett alarm
     * utifrån angiven tid.
     */
    public void button_next(View view)
    {
        setUpAlarm();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        try {
            OBTSDK.initialize(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }//End of button_next




    /**
     * Ställer in alarmen utifrån angiven morgontid samt kvällstid.
     */
    private void setUpAlarm() {

        String wTime = sharedPreferences.getString("wakeUpTime", "");
        String sTime = sharedPreferences.getString("sleepTime","");
        Constants.setWakeUpAlarm(new MyAlarmManager(this, wTime, 0));
        Constants.setSleepAlarm(new MyAlarmManager(this, sTime, 1));

    }//End of setUpAlarm




    /**
     * Stänger av båda alarmen
    public static void stopAlarm(){
        wakeUpAlarm.stopAlarm();
        sleepAlarm.stopAlarm();
    }//End of stopAlarm*/




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_back




    @Override
    public void onBackPressed() {
    }
}
