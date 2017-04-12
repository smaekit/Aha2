package com.marcusjakobsson.aha;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.util.Log;

/**
 * Created by konstantin_ay on 2017-04-10.
 */

class MyAlarmManager{
    private final PendingIntent pendingIntent;
    private AlarmManager alarm;
    private int hours;
    private int minutes;

    private Context myContext;
    private int myId;




    MyAlarmManager(Context context, String time, int id){
        alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intentRecv = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, id, intentRecv, 0);
        myContext = context;
        myId = id;
        toIntTime(time);

        setUpAlarm();
    }//End of onCreate




    /**
     * Ställer in alarmet utifrån de privata medlemmarnas data
     * och skapar ett Calendar-objekt i tidszonen GMT (Europa/Stockholm) för att inte få tidsförskjutning
     */
    private void setUpAlarm(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Stockholm"));

        //Kontrollerar att tiden inte har passerat den angivna tiden.
        Log.i("HOUR", String.valueOf(hours));
        if( hours == 24 || cal.get(Calendar.HOUR_OF_DAY) > hours)
            cal.add(Calendar.DAY_OF_YEAR, 1);
        else if(cal.get(Calendar.HOUR_OF_DAY) == hours)
        {
            if(cal.get(Calendar.MINUTE) > minutes)
                cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        Log.i("Alarm set to"+myId, cal.getTime().toString());
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Intent intentFinal = new Intent(myContext,FinalActivity.class);
        myContext.startActivity(intentFinal);
    }//End of setUpAlarm




    /**
     * Tar in en sträng i formatet "HH:MM" och bryter ut timmar samt minuter
     * och placerar dessa i de privata variablerna hours och minutes
     */
    private void toIntTime(String time) {
        String [] timeArr = time.split(":");
        hours = Integer.parseInt(timeArr[0]);
        minutes = Integer.parseInt(timeArr[1]);
    }//End of toIntTime




    void stopAlarm(){
        alarm.cancel(pendingIntent);
    }//End of stopAlarm
}//End MyAlarmManager
