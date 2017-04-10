package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by konstantin_ay on 2017-04-10.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    private static Ringtone ringtone = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        Intent i = new Intent();
        i.setClassName("com.marcusjakobsson.aha", "com.marcusjakobsson.aha.FinalActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void stopRingtone(){
        ringtone.stop();
    }
}
