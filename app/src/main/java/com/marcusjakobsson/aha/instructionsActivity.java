package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class instructionsActivity extends AppCompatActivity {

    ImageButton btn_NextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        //btn_NextActivity = (ImageButton)findViewById(R.id.btn_nextActivity);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("name");
        TextView textView =(TextView)findViewById(R.id.name_test);
        textView.setText("Hej "+msg+ "!\nhär är en instruktionsvideo, starta den genom att trycka på play");

        VideoView vv = (VideoView)findViewById(R.id.videoView);
        vv.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.normal);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(vv);
        mediaController.setPadding(0,0,0, 570);
        ImageView imageView;


        vv.setMediaController(mediaController);
        vv.start();
    }

    public void btn_nextActivity(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WakeUpActivity.class);
        startActivity(intent);
    }
}