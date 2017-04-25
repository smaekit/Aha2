package com.marcusjakobsson.aha;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.oralb.sdk.OBTBrushListener;
import com.oralb.sdk.OBTSDK;
import com.oralb.sdk.OBTSdkAuthorizationListener;

public class FinalActivity extends MyOBTBrushListener{

    //TODO: Vid fel under uppkoppling till ORAL-Bs SDK, visa felmeddelande till användare
    //TODO: Erbjud möjligheten att försöka ansluta till ORAL-B på nytt
    //TODO: Visa att man måste vara uppkopplad till Wi-fi


    //TODO: Berätta för anv. att ansluta tandborste
    //TODO: Kolla om Wi-fi är på
    //TODO: Kolla om Bluetooth är på
    //TODO: Kolla om Tandborsten har kopplats, om den inte är så ska man via ett knapptryck koppla om och om igen
    //TODO: Om tandborste har kopplats sen tidigare, stoppa scan och starta vid larm

    MyOBTSdkAuthListener authListener;
    AlertActivity alertActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        authListener = new MyOBTSdkAuthListener();
        alertActivity = new AlertActivity();

        //Call to initialize the OBTSDK
        Log.i("main", "OBT SDK initialized");
        OBTSDK.authorizeSdk(authListener);
    } //End onCreate





    public void button_back(View view){
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
    }

} //End FinalActivity
