package com.marcusjakobsson.aha;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    //Variabler som behövs globalt
    protected static final int RESULT_SPEECH = 1;
    ImageButton button_Speak;               //Mikrofon knappen
    RelativeLayout relativeLayout_main;     //för att kunna göra Bakgrunden klickbar
    EditText editText_enterName;
    ImageButton button_ok;
    ImageButton button_erase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_Speak = (ImageButton) findViewById(R.id.button_mic);
        relativeLayout_main = (RelativeLayout)findViewById(R.id.relativeLayout_main);
        relativeLayout_main.setOnClickListener(this);
        editText_enterName = (EditText)findViewById(R.id.editText_EnterName);
        editText_enterName.setOnKeyListener(this);
        button_ok = (ImageButton)findViewById(R.id.button_ok);
        button_erase = (ImageButton)findViewById(R.id.button_cross);


        //TODO check if internetConnection annars funkar ej mikrofonknappen Toast/alpha
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
            }
        });

    } //End onCreate


    //RegEx för namn tar in ett namn som en sträng
    private boolean isNameValid(String name)
    {
        boolean isValid = false;
        Pattern p = Pattern.compile("[a-zåäöü]+",Pattern.CASE_INSENSITIVE);
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
                //Skapar ett lokalt storage i appen.
                SharedPreferences sharedPreferences = this.getSharedPreferences("com.marcusjakobsson.aha", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("name", name).apply(); //Sparar permanent i variablen namn
                Intent intent = new Intent(getApplicationContext(),InstructionsActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(this, "Ej ett gilltigt namn får endast innehålla bokstäver", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Var vänlig skriv in ditt namn.", Toast.LENGTH_SHORT).show();

    }//End button_OK



    //TODO skapa en show hide alt alpha för knappen som är gömd när inget är inskrivet i namn rutan.
    //Rensar inputFönstret för namnrutan
    public void button_erase_name(View view)
    {
        editText_enterName.setText(null);
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
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0); //Gömmer tangetbordet, 0 är flagga 0.
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
} //End MainActivity

