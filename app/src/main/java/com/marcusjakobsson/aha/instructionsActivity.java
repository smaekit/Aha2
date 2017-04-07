package com.marcusjakobsson.aha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class instructionsActivity extends AppCompatActivity {

    String name;
    VideoView videoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        //btn_NextActivity = (ImageButton)findViewById(R.id.btn_nextActivity);
        //Intent intent = getIntent(); //faf
        //name = intent.getStringExtra("name");
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", ""); //finns inget värde att hämta blir det default värde ""
        TextView textView =(TextView)findViewById(R.id.message);
        textView.setText("Hej "+name+ "!\nhär är en instruktionsvideo, starta den genom att trycka på play");

        startVideo();
    }

    private void startVideo(){
        videoV = (VideoView)findViewById(R.id.videoView);
        videoV.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.normal);

        MediaController mediaController = new MediaController(this);
        videoV.setMediaController(mediaController);
        mediaController.setAnchorView(videoV);
        videoV.start();
    }

    public void button_next(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}