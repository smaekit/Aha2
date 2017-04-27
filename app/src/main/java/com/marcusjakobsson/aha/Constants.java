package com.marcusjakobsson.aha;

import android.content.SharedPreferences;
import android.media.Ringtone;

/**
 * Created by konstantin_ay on 2017-04-27.
 */

public class Constants {
    private static SharedPreferences sharedPreferences;
    private static MyAlarmManager wakeUpAlarm;
    private static MyAlarmManager sleepAlarm;
    private static Ringtone ringtone;
    private static boolean initialized = false;




    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }




    public static void setSharedPreferences(SharedPreferences sP){
        sharedPreferences = sP;
    }




    public static MyAlarmManager getWakeUpAlarm(){
        return wakeUpAlarm;
    }




    public static void setWakeUpAlarm(MyAlarmManager alarm){
        wakeUpAlarm = alarm;
    }




    public static MyAlarmManager getSleepAlarm(){
        return sleepAlarm;
    }




    public static void setSleepAlarm(MyAlarmManager alarm){
        sleepAlarm = alarm;
    }




    public static Ringtone getRingtone(){
        return ringtone;
    }




    public static void setRingtone(Ringtone rT){
        ringtone = rT;
    }




    public static boolean isInitialized(){
        return initialized;
    }




    public static void setInitialized(boolean ok){
        initialized = ok;
    }
}
