package com.marcusjakobsson.aha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.oralb.sdk.OBTCloudListener;
import com.oralb.sdk.OBTSession;
import com.oralb.sdk.OBTUserAuthorizationListener;

import java.util.List;

public class WaitActivity extends AppCompatActivity implements OBTCloudListener{
    OBTUserAuthorizationListener userAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        Log.i("WAIT", "Here");

        /*new CountDownTimer(900000, 120000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                OBTSDK.fetchUserSessions(0, WaitActivity.this);
            }

            @Override
            public void onFinish() {
                OBTSDK.fetchUserSessions(0, WaitActivity.this);
            }
        }.start();*/

    }//End of onCreate




    @Override
    public void onBackPressed() {

    }

    @Override
    public void onSessionsLoaded(List<OBTSession> list) {
        if(!list.isEmpty())
        {
            Log.i("No. of sessions", String.valueOf(list.size()));
            for(OBTSession oSession: list){
                Log.i("BRUSHING TIME", String.valueOf(oSession.getBrushingTime()));
                Log.i("HANDLE ID", String.valueOf(oSession.getHandleId()));
                Log.i("HANDLE SESSION ID", String.valueOf(oSession.getHandleSessionId()));
                Log.i("SESSION ID", String.valueOf(oSession.getId()));
                Log.i("START", String.valueOf(oSession.getStart()));
                Log.i("END", String.valueOf(oSession.getEnd()));
            }
        }
        else
            Log.i("BRUSHING TIME", "No sessions found");
    }

    @Override
    public void onFailure(int i) {
        Log.i("FAILURE", String.valueOf(i));
    }
}
