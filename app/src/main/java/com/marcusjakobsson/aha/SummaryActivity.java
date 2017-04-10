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
    private static PendingIntent pendingIntent;
    SharedPreferences sharedPreferences;
    TextView name;
    TextView wakeUpTime;
    TextView sleepTime;
    private static AlarmManager alarm;
    Calendar cal;
    int hours;
    int minutes;

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
        String time = sharedPreferences.getString("wakeUpTime", "");
        intTime(time);

        setUpAlarm();
    }

    private void setUpAlarm() {
        alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intentRec = new Intent(SummaryActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(SummaryActivity.this, 0, intentRec, 0);

        cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        Log.i("Alarm set to", cal.getTime().toString());
        //alarm.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 60*1000, pendingIntent);
        Intent intentFin = new Intent(getApplicationContext(),FinalActivity.class);
        startActivity(intentFin);
    }

    public static void stopAlarm(){
        alarm.cancel(pendingIntent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }

    public void intTime(String intTime) {
        String [] time = intTime.split(":");
        hours = Integer.parseInt(time[0]);
        minutes = Integer.parseInt(time[1]);
    }
}
