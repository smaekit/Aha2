package com.marcusjakobsson.aha;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.oralb.sdk.OBTBrush;
import com.oralb.sdk.OBTBrushListener;
import com.oralb.sdk.OBTSDK;
import com.oralb.sdk.OBTSdkAuthorizationListener;
import com.oralb.sdk.OBTSession;
import com.oralb.sdk.OBTUserAuthorizationListener;

import java.util.List;

public class FinalActivity extends AppCompatActivity implements OBTBrushListener {

    //TODO: Vid fel under uppkoppling till ORAL-Bs SDK, visa felmeddelande till användare
    //TODO: Erbjud möjligheten att försöka ansluta till ORAL-B på nytt
    //TODO: Visa att man måste vara uppkopplad till Wi-fi

    private CountDownTimer timer;
    MyOBTSdkAuthListener1 authListener;
    MyOBTUserAuthorizationListener userAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        authListener = new MyOBTSdkAuthListener1();

        userAuthListener = new MyOBTUserAuthorizationListener();


        timer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {} //Kan användas för att skriva ut varje tick

            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),WaitActivity.class);
                startActivity(intent);
            }

        };

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        try {
            //Call to initialize the OBTSDK
            OBTSDK.initialize(this);
            Log.i("main", "OBT SDK initialized");
            OBTSDK.authorizeSdk(authListener);

            OBTSDK.setOBTBrushListener(this);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } //End onCreate





    public void button_back(View view){
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        timer.cancel();
        startActivity(intent);
    }




    @Override
    public void onResume() {
        super.onResume();
        // Set this activity as OBTBrushListener

        OBTSDK.setOBTBrushListener(this);
    }




    @Override
    protected void onPause() {
        super.onPause();
        // Remove the OBTBrushListener
        OBTSDK.setOBTBrushListener(null);
    }




    @Override
    public void onNearbyBrushesFoundOrUpdated(List<OBTBrush> nearbyBrushes) {
        Log.i("found brush", "" + nearbyBrushes.size());
        Toast.makeText(this, "Found a brush!", Toast.LENGTH_SHORT).show();
        if (!nearbyBrushes.isEmpty()){
            // Connect to first Oral-B Toothbrush
            try{
                Log.i("Hejsan", "TJABBA");
                OBTSDK.connectToothbrush(nearbyBrushes.get(0), false);

            }
            catch(Exception e)
            {
                Toast.makeText(this, "Could not connect!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBluetoothError() {

    }

    @Override
    public void onBrushDisconnected() {
        Log.i("BRUSH", "Brush disconnected");
        OBTSDK.authorizeApplication(userAuthListener);

    }

    @Override
    public void onBrushConnected() {
        Toast.makeText(this, "Brush is connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBrushConnecting() {
        Log.i("BRUSH", "Brush connecting");
    }

    @Override
    public void onBrushingTimeChanged(long l) {
        Log.i("TIME", String.valueOf(l/1000));

    }

    @Override
    public void onBrushingModeChanged(int i) {

    }

    @Override
    public void onBrushStateChanged(int i) {

    }

    @Override
    public void onRSSIChanged(int i) {

    }

    @Override
    public void onBatteryLevelChanged(float v) {

    }

    @Override
    public void onSectorChanged(int i) {

    }

    @Override
    public void onHighPressureChanged(boolean b) {

    }




    @Override
    public void onBackPressed() {
    }

    private class MyOBTSdkAuthListener1 implements OBTSdkAuthorizationListener {
        @Override
        public void onSdkAuthorizationSuccess() {
            Log.i("MyOBTSdkAuthListener", "success");

            try {
                OBTSDK.startScanning();
                Log.i("Scan", "Scanning...");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Scan", "Not scanning...");
            }
        }

        @Override
        public void onSdkAuthorizationFailed(int i) {

            if(i==2){
                Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
                startActivity(intent);
            }
            Log.i("MyOBTSdkAuthListener", "failed"+i);
        }
    }

    private class MyOBTUserAuthorizationListener implements OBTUserAuthorizationListener{
        @Override
        public void onUserAuthorizationSuccess() {
            Log.i("SUCCESS", "Successfully authorized");
            timer.start();
        }

        @Override
        public void onUserAuthorizationFailed(int i) {
            Log.i("FAIL", String.valueOf(i));
        }
    }
} //End FinalActivity
