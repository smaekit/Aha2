package com.marcusjakobsson.aha;


import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by konstantin_ay on 2017-04-10.
 * Invokeras när ett alarm ska utlösas
 */

public class AlarmReceiver extends WakefulBroadcastReceiver
{
    private static Ringtone ringtone = null;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//Sätter en URI till ett notisljud.
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        Intent i = new Intent();
        i.setClassName("com.marcusjakobsson.aha", "com.marcusjakobsson.aha.AlertActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }//End onReceive


    //Anropas för att stoppa ringsignalen utan att avbryta alarm.
    public static void stopRingtone(){
        ringtone.stop();
    }

    //Anropas för att starta igång ringsignalen utan att avbryta alarmet.
    public static void startRingtone() { ringtone.play(); }

}//End AlarmReceiver
