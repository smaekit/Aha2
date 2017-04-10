package com.marcusjakobsson.aha;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by konstantin_ay on 2017-04-10.
 */

public class MyAlarmManager{
    private final PendingIntent pendingIntent;
    private AlarmManager alarm;
    private int hours;
    private int minutes;

    public MyAlarmManager(Context context, String time, int id){
        toIntTime(time);
        alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intentRecv = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, id, intentRecv, 0);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));

        //Kontrollerar att tiden inte har passerat den angivna tiden.
        if(cal.get(Calendar.HOUR_OF_DAY) > hours)
        {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        else if(cal.get(Calendar.HOUR_OF_DAY) == hours)
        {
            if(cal.get(Calendar.MINUTE) > minutes)
                cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        Log.i("Alarm set to"+id, cal.getTime().toString());
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Intent intentFinal = new Intent(context,FinalActivity.class);
        context.startActivity(intentFinal);
    }




    private void toIntTime(String time) {
        String [] timeArr = time.split(":");
        hours = Integer.parseInt(timeArr[0]);
        minutes = Integer.parseInt(timeArr[1]);
    }




    public void stopAlarm(){
        alarm.cancel(pendingIntent);
    }
}
