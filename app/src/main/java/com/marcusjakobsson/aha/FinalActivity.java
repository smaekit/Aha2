package com.marcusjakobsson.aha;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.oralb.sdk.OBTSDK;

public class FinalActivity extends MyOBTBrushListener{

    //TODO: Gå tillbaka till föregående vyer för att ändra tid osv.

    MyOBTSdkAuthListener authListener;
    AlertActivity alertActivity;
    SharedPreferences sharedPreferences;
    private static ImageView catImage;
    private static ImageButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        catImage = (ImageView)findViewById(R.id.cat_image);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }

        sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);

        if(!(sharedPreferences.getBoolean("brushHasConnected", false))){
            Log.i("Has Connected", "FIRST TIME");
            catStatus("FIRST_TIME");
        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!wifi.isConnected()) {
            Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
            startActivity(intent);
        }

        refreshButton = (ImageButton)findViewById(R.id.button_refresh);

        authListener = new MyOBTSdkAuthListener();
        alertActivity = new AlertActivity();

        //Call to initialize the OBTSDK
        Log.i("main", "OBT SDK initialized");
        authorizeSDK();
    } //End onCreate



    private void authorizeSDK()
    {
        OBTSDK.authorizeSdk(authListener);
    }




    public static void showButton(){
        refreshButton.setVisibility(View.VISIBLE);
    }




    public static void hideButton(){
        refreshButton.setVisibility(View.GONE);
    }




    public static void catStatus(String status){
        switch (status){
            case "FAILED":
                catImage.setImageResource(R.drawable.cat_failed);
                break;
            case "FIRST_TIME":
                catImage.setImageResource(R.drawable.cat_brush);
                break;
            default:
                catImage.setImageResource(R.drawable.cat);
                break;
        }
    }




    public void button_Refresh(View view)
    {
        OBTSDK.disconnectToothbrush();
        authorizeSDK();
    }




    /*public void button_back(View view){
        OBTSDK.disconnectToothbrush();
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }*/






    @Override
    public void onBackPressed() {
    }

} //End FinalActivity
