package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class instructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);


        Intent intent = getIntent();
        String msg = intent.getStringExtra("name");
        TextView textView =(TextView)findViewById(R.id.name_test);
        textView.setText(msg);
    }
}
