package com.marcusjakobsson.aha;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {
    private static PendingIntent wakeUpPendingIntent;
    private static AlarmManager wakeUpAlarm;
    private static PendingIntent sleepPendingIntent;
    private static AlarmManager sleepAlarm;
    SharedPreferences sharedPreferences;
    TextView name;
    TextView wakeUpTime;
    TextView sleepTime;
    Calendar wakeUpCal;
    Calendar sleepCal;
    int wakeUpHours;
    int wakeUpMinutes;
    int sleepHours;
    int sleepMinutes;




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
        String wTime = sharedPreferences.getString("wakeUpTime", "");
        String sTime = sharedPreferences.getString("sleepTime","");
        toIntTime(wTime, sTime);

        setUpAlarm();
    }




    private void setUpAlarm() {
        wakeUpAlarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent wakeUpIntentRecv = new Intent(SummaryActivity.this, AlarmReceiver.class);
        wakeUpPendingIntent = PendingIntent.getBroadcast(SummaryActivity.this, 0, wakeUpIntentRecv, 0);

        wakeUpCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        wakeUpCal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        wakeUpCal.set(Calendar.HOUR_OF_DAY, wakeUpHours);
        wakeUpCal.set(Calendar.MINUTE, wakeUpMinutes);
        Log.i("Wake up alarm set to", wakeUpCal.getTime().toString());
        wakeUpAlarm.setRepeating(AlarmManager.RTC_WAKEUP, wakeUpCal.getTimeInMillis(), 60*1000, wakeUpPendingIntent);
        Intent wakeUpIntentFinal = new Intent(getApplicationContext(),FinalActivity.class);
        startActivity(wakeUpIntentFinal);

        ///////////////////////////////////////////////
        sleepAlarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent sleepIntentRecv = new Intent(SummaryActivity.this, AlarmReceiver.class);
        sleepPendingIntent = PendingIntent.getBroadcast(SummaryActivity.this, 1, sleepIntentRecv, 0);

        sleepCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        sleepCal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        sleepCal.set(Calendar.HOUR_OF_DAY, 14);
        sleepCal.set(Calendar.MINUTE, 38);
        Log.i("Sleep alarm set to", sleepCal.getTime().toString());
        sleepAlarm.setRepeating(AlarmManager.RTC_WAKEUP, sleepCal.getTimeInMillis(), 60*1000, sleepPendingIntent);
        Intent sleepIntentFinal = new Intent(getApplicationContext(),FinalActivity.class);
        startActivity(sleepIntentFinal);
    }




    public static void stopAlarm(){
        wakeUpAlarm.cancel(wakeUpPendingIntent);
    }




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }




    public void toIntTime(String wakeUpTime, String sleepTime) {
        String [] wTime = wakeUpTime.split(":");
        wakeUpHours = Integer.parseInt(wTime[0]);
        wakeUpMinutes = Integer.parseInt(wTime[1]);

        String [] sTime = sleepTime.split(":");
        sleepHours = Integer.parseInt(sTime[0]);
        sleepMinutes = Integer.parseInt(sTime[1]);
    }
}
