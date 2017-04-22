package com.marcusjakobsson.aha;

import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.oralb.sdk.OBTCloudListener;
import com.oralb.sdk.OBTSDK;
import com.oralb.sdk.OBTSdkAuthorizationListener;
import com.oralb.sdk.OBTSession;
import com.oralb.sdk.OBTUserAuthorizationListener;

import java.util.List;
import java.util.Locale;

public class WaitActivity extends AppCompatActivity implements OBTCloudListener{
    //TODO: Implementera funktioner som hämtar användardata/sessionsdata från ORAL-B
    MyOBTSdkAuthListener authListener;
    OBTUserAuthorizationListener userAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        authListener = new MyOBTSdkAuthListener();
        userAuthListener = new OBTUserAuthorizationListener() {
            @Override
            public void onUserAuthorizationSuccess() {
                Log.i("SUCCESS", "Successfully authorized");
                OBTSDK.fetchUserSessions(243255525, WaitActivity.this);
                //OBTSDK.fetchUserSessions(1,2,WaitActivity.this);
            }

            @Override
            public void onUserAuthorizationFailed(int i) {
                Log.i("FAIL", String.valueOf(i));
            }
        };

        //OBTSDK.authorizeApplication(userAuthListener);
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
                Log.i("BRUSHING TIME", oSession.getBrushingTime()+" "+oSession.getCreateDate());
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
