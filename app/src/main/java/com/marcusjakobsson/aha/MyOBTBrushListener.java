package com.marcusjakobsson.aha;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.oralb.sdk.OBTBrush;
import com.oralb.sdk.OBTBrushListener;
import com.oralb.sdk.OBTSDK;

import java.util.List;

/**
 * Created by konstantin_ay on 2017-04-25.
 */

public class MyOBTBrushListener extends AppCompatActivity implements OBTBrushListener {

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
        Log.i("Found brush", "" + nearbyBrushes.size());
        //Toast.makeText(this, "Found a brush!", Toast.LENGTH_SHORT).show();
        if (!nearbyBrushes.isEmpty()){
            // Connect to first Oral-B Toothbrush
            try{
                Log.i("Hejsan", "TJABBA");
                OBTSDK.connectToothbrush(nearbyBrushes.get(0), false);

            }
            catch(Exception e)
            {
                //Toast.makeText(this, "Kunde inte ansluta tandborsten!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBluetoothError() {
        Toast.makeText(this, "Ett bluetooth problem uppstod, var vänlig starta om bluetooth och försök igen", Toast.LENGTH_LONG).show();
        FinalActivity.catStatus("FAILED");
        FinalActivity.showButton();
    }

    @Override
    public void onBrushDisconnected() {
        Log.i("BRUSH", "Brush disconnected");
        //OBTSDK.authorizeApplication(userAuthListener);
    }

    @Override
    public void onBrushConnected() {
        Toast.makeText(this, "Tandborsten är ansluten", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = Constants.getSharedPreferences();
        sharedPreferences.edit().putBoolean("brushHasConnected", true).apply();
        FinalActivity.catStatus("CONNECTED");
        FinalActivity.hideButton();
        //TODO lägga till en datavy vid connect som visar pressure, tid, mode. Kan vara gömd i kattvyn

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
}
