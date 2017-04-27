package com.marcusjakobsson.aha;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.oralb.sdk.OBTSDK;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    //Variabler som behövs globalt
    private static final int RESULT_SPEECH = 1;
    private ImageButton button_Speak;               //Mikrofon knappen
    private RelativeLayout relativeLayout_main;     //för att kunna göra Bakgrunden klickbar
    private EditText editText_enterName;
    private ImageButton button_ok;
    private ImageButton button_erase;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeSDK();

        button_Speak = (ImageButton) findViewById(R.id.button_mic);
        relativeLayout_main = (RelativeLayout)findViewById(R.id.relativeLayout_main);
        relativeLayout_main.setOnClickListener(this);
        editText_enterName = (EditText)findViewById(R.id.editText_EnterName);
        editText_enterName.setOnKeyListener(this);
        button_ok = (ImageButton)findViewById(R.id.button_ok);
        button_erase = (ImageButton)findViewById(R.id.button_cross);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //Skapar ett lokalt storage i appen.
        sharedPreferences = this.getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", ""); //finns inget värde att hämta blir det default värde ""
        if(!name.equals(""))
        {
            editText_enterName.setText(name);
            button_erase.setAlpha(1f);
            button_ok.setAlpha(1f);
        }

        //Ändrar transparens för button_OK/Erase när en korrekt input är inskrivet i namn rutan.
        editText_enterName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String name = editText_enterName.getText().toString();
                if(editText_enterName.getText().toString().isEmpty())
                {
                    button_erase.setAlpha(0.3f);
                    button_ok.setAlpha(0.3f);

                }else
                {
                    button_erase.setAlpha(1f);
                    if(isNameValid(name))
                    {
                        button_ok.setAlpha(1f);
                    }
                    else
                        button_ok.setAlpha(0.3f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        }); //End addTextChangedListener

        //När mikrofon knappen trycks ner så tillåter den användaren att prata in sitt namn
        button_Speak.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "sv-SE");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    editText_enterName.setText("");

                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Hoppsan! Din enhet stödjer inte Tal till Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }//End onClick

        });//End setOnClickListener

    } //End onCreate




    private void initializeSDK() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if(!OBTSDK.isOnline(this)){
            try {
                OBTSDK.initialize(this);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




    //RegEx för namn tar in ett namn som en sträng
    private boolean isNameValid(String name)
    {
        boolean isValid = false;
        Pattern p = Pattern.compile("[a-zåäöü]+-?[ ]?[a-zåäöü]+",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(name);
        if(m.matches())
            isValid = true;

        return isValid;
    }



    //Verifierar att användaren har skrivit in ett namn och skickar anv till nästa vy
    public void button_OK(View view)
    {
        if(!editText_enterName.getText().toString().equals(""))
        {
            String name = editText_enterName.getText().toString();
            if(isNameValid(name))
            {
                sharedPreferences.edit().putString("name", name).apply(); //Sparar permanent i variablen namn
                Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
            else
                Toast.makeText(this, "Ej ett gilltigt namn får endast innehålla bokstäver", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Var vänlig skriv in ditt namn.", Toast.LENGTH_SHORT).show();

    }//End button_OK



    //Rensar inputFönstret för namnrutan
    public void button_erase_name(View view)
    {
        editText_enterName.setText(null);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }



    //Lyssnar efter Enter tryck och skickar användaren till nästa vy om den skrivit in ett namn
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
            inputMethodManager.hideSoftInputFromWindow(editText_enterName.getWindowToken(),0); //Gömmer tangetbordet, 0 är flagga 0.
        }
    }



    //Resultatet från Tal till Text
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    editText_enterName.setText(text.get(0));
                }
                break;
            }

        }
    } //End onActivityResult




    @Override
    public void onBackPressed() {
    }
} //End MainActivity

