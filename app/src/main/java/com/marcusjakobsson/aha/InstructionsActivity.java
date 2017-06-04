package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class InstructionsActivity extends AppCompatActivity
{

    private ImageButton imageButton_PlayVideo;
    private VideoView videoV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        //SharedPreferences sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        //String name = sharedPreferences.getString("name", ""); //finns inget värde att hämta blir det default värde ""
        imageButton_PlayVideo = (ImageButton)findViewById(R.id.imageButton_Play);

        //TextView textView =(TextView)findViewById(R.id.textView_InfoMessage);
        //textView.setText("Hej "+name+"!\nStarta videoklippet ovan genom att trycka på start-knappen.");
        initiateVideoPlayer();

    } //End onCreate



    //TODO skapa video för appen
    private void initiateVideoPlayer()
    {
        videoV = (VideoView)findViewById(R.id.videoView);
        videoV.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.info);

        MediaController mediaController = new MediaController(this);
        videoV.setMediaController(mediaController);
        mediaController.setAnchorView(videoV);
        videoV.seekTo(500);
    }



    public void button_PlayVideo(View view)
    {
        imageButton_PlayVideo.setVisibility(View.GONE);
        videoV.start();
    }



    public void button_next(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }




    public void button_back(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }




    @Override
    public void onBackPressed() {
    }
} //End InstructionsActivity