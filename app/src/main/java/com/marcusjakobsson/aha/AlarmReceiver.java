package com.marcusjakobsson.aha;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by konstantin_ay on 2017-04-10.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    private static Ringtone ringtone = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//Sätter en URI till ett notisljud.
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        Intent i = new Intent();
        i.setClassName("com.marcusjakobsson.aha", "com.marcusjakobsson.aha.AlertActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("Alarm", context.toString());

        context.startActivity(i);
    }




    /**
     * Anropas för att stoppa ringsignalen utan att avbryta alarm.
     */
    public static void stopRingtone(){
        ringtone.stop();
    }
}
