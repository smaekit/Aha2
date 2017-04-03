package com.marcusjakobsson.aha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    ImageButton button_ok;
    ImageButton button_erase;
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
        button_ok = (ImageButton)findViewById(R.id.button_ok);
        button_erase = (ImageButton)findViewById(R.id.button_cross);

    }


    public void button_OK(View view) //Knappen next skickar en till nästa vy
    {
        Log.i("OK","OK pressed");
        Intent intent = new Intent(getApplicationContext(),instructionsActivity.class);
        intent.putExtra("name",editText_enterName.getText().toString()); //skickar med namnet till nästa vy
        startActivity(intent);
    }

    public void micButton(View view)
    {
        Log.i("Mic", "Mic pressed");
    }


    public void button_erase(View view)
    {
        Log.i("Erase", "Erase pressed");
        editText_enterName.setText(null);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event)
    {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) //Lyssnar efter enter tryck
        {
            button_OK(view);  //skickar en till nästa vy
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
