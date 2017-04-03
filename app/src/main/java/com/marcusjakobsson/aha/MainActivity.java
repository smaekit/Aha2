package com.marcusjakobsson.aha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    //Variabler som behövs globalt
    ImageButton btn_Microphone;
    RelativeLayout relativeLayout_main;
    EditText editText_enterName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_Microphone = (ImageButton) findViewById(R.id.button_mic);
        relativeLayout_main = (RelativeLayout)findViewById(R.id.relativeLayout_main);
        relativeLayout_main.setOnClickListener(this);
        editText_enterName = (EditText)findViewById(R.id.editText_EnterName);
        editText_enterName.setOnKeyListener(this);
    }


    public void micButton(View view)
    {
        //HEJSAN!!
        Log.i("Mic", "Mic pressed");
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) //Lyssnar efter enter tryck
        {
            //nextView(view);
        }

        return false;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.relativeLayout_main) //Klickar man i bakgrunden så ska tangentbordet gömmas undan
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0); //Gömmer tangetbordet, 0 är flagga 0.
        }
    }
}
