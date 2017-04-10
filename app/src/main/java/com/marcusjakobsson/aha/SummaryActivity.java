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

    TextView name;
    TextView wakeUpTime;
    TextView sleepTime;
    AlarmManager alarm;
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


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name",""));
        wakeUpTime.setText(sharedPreferences.getString("wakeUpTime",""));
        sleepTime.setText(sharedPreferences.getString("sleepTime",""));
    }
    public void button_next(View view)
    {
        /*Calendar cal = Calendar.getInstance();
        TimeZone timeZone = TimeZone.getTimeZone("GMT+00");
        cal.setTimeZone(timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(timeZone);

        try {
            cal.setTime(sdf.parse(wakeUpTime.getText().toString()));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("Time", cal.getTime().toString());*/

        alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(SummaryActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SummaryActivity.this, 0, intent, 0);

        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 33);
        Log.i("Alarm set to", cal.getTime().toString());
        alarm.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        Intent intent2 = new Intent(getApplicationContext(),FinalActivity.class);
        startActivity(intent2);
    }



    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
    }
}
