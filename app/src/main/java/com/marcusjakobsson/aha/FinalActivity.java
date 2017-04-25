package com.marcusjakobsson.aha;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.oralb.sdk.OBTSDK;

public class FinalActivity extends MyOBTBrushListener{

    //TODO: Vid fel under uppkoppling till ORAL-Bs SDK, visa felmeddelande till användare
    //TODO: Erbjud möjligheten att försöka ansluta till ORAL-B på nytt


    //TODO: Berätta för anv. att ansluta tandborste (Vy)
    //TODO: Kolla om Wi-fi är på
    //TODO: Kolla om Bluetooth är på
    //TODO: Kolla om Tandborsten har kopplats, om den inte är så ska man via ett knapptryck koppla om och om igen
    //TODO: Om tandborste har kopplats sen tidigare, stoppa scan och starta vid larm

    //TODO: Gå tillbaka till föregående vyer för att ändra tid osv.

    MyOBTSdkAuthListener authListener;
    AlertActivity alertActivity;
    ImageView catImage;
    private static ImageButton refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        catImage = (ImageView)findViewById(R.id.cat_image);
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




    public void button_Refresh(View view)
    {
        OBTSDK.disconnectToothbrush();
        authorizeSDK();
    }



    public void button_back(View view){
        OBTSDK.disconnectToothbrush();
        Intent intent = new Intent(getApplicationContext(),SummaryActivity.class);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
    }

} //End FinalActivity
