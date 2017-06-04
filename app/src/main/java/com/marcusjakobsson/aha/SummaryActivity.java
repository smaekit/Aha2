package com.marcusjakobsson.aha;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class SummaryActivity extends AppCompatActivity {
    //private static MyAlarmManager wakeUpAlarm;
    //private static MyAlarmManager sleepAlarm;
    private SharedPreferences sharedPreferences;
    private TextView name;
    private TextView wakeUpTime;
    private TextView sleepTime;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        name = (TextView) findViewById(R.id.text_name);
        wakeUpTime = (TextView) findViewById(R.id.text_wakeup);
        sleepTime = (TextView) findViewById(R.id.text_sleep);

        //Initierar samtliga textViews så de visar inmatad information på skärmen
        sharedPreferences = Constants.getSharedPreferences();
        name.setText(sharedPreferences.getString("name",""));
        wakeUpTime.setText(sharedPreferences.getString("wakeUpTime",""));
        sleepTime.setText(sharedPreferences.getString("sleepTime",""));
    }//End of onCreate



    /**
     * Vid bekräftelse av information kommer applikationen att skapa ett alarm
     * utifrån angiven tid.
     */
    public void button_next(View view)
    {
        setUpAlarm();
    }//End of button_next




    /**
     * Ställer in alarmen utifrån angiven morgontid samt kvällstid.
     */
    private void setUpAlarm() {

        String wTime = sharedPreferences.getString("wakeUpTime", "");
        String sTime = sharedPreferences.getString("sleepTime","");
        Constants.setWakeUpAlarm(new MyAlarmManager(this, wTime, 0));
        Constants.setSleepAlarm(new MyAlarmManager(this, sTime, 1));

    }//End of setUpAlarm




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SleepActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }//End of button_back




    @Override
    public void onBackPressed() {
    }
}
