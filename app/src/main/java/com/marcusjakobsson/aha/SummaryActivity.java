package com.marcusjakobsson.aha;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    private static MyAlarmManager wakeUpAlarm;
    private static MyAlarmManager sleepAlarm;
    SharedPreferences sharedPreferences;
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


        sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name",""));
        wakeUpTime.setText(sharedPreferences.getString("wakeUpTime",""));
        sleepTime.setText(sharedPreferences.getString("sleepTime",""));
    }




    public void button_next(View view)
    {
        setUpAlarm();
    }




    private void setUpAlarm() {

        String wTime = sharedPreferences.getString("wakeUpTime", "");
        String sTime = sharedPreferences.getString("sleepTime","");
        wakeUpAlarm = new MyAlarmManager(this, wTime, 0);
        sleepAlarm = new MyAlarmManager(this, sTime, 1);

        /*boolean alarmUpW = (PendingIntent.getBroadcast(this, 0, new Intent("com.marcusjakobsson.aha.SummaryActivity"), PendingIntent.FLAG_NO_CREATE) != null);
        boolean alarmUpS = (PendingIntent.getBroadcast(this, 1, new Intent("com.marcusjakobsson.aha.SummaryActivity"), PendingIntent.FLAG_NO_CREATE) != null);


        if(alarmUpW)
            Log.i("Alarms", "Alarm 0 is up!");
        else
            Log.i("Alarms", "Alarm 0 is not up!");

        if(alarmUpS)
            Log.i("Alarms", "Alarm 1 is up!");
        else
            Log.i("Alarms", "Alarm 1 is not up!");*/
    }




    public static void stopAlarm(){
        wakeUpAlarm.stopAlarm();
        sleepAlarm.stopAlarm();
    }




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }
}
