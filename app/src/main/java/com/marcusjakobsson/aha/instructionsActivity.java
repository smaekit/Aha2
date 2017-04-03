package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class instructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        Intent intent = getIntent();
        String msg = intent.getStringExtra("name");
        TextView textView =(TextView)findViewById(R.id.name_test);
        textView.setText("Hej "+msg+ "!\nhär är en instruktionsvideo, starta den genom att trycka på play");

        VideoView vv = (VideoView)findViewById(R.id.videoView);
        vv.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.normal);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vv);
        ImageView imageView;


        vv.setMediaController(mediaController);
        vv.start();
    }
}