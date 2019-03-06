package com.example.travelagency.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelagency.R;

public class TripActivityEdit extends AppCompatActivity implements View.OnClickListener {

    //-----------------
    TextView country;
    TextView city;
    TextView days;
    TextView price;
    TextView date;
    TextView description;
    AlertDialog dialog;
    EditText editText;
    //------------------

    //To put the symbols to the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();


        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_edit);

        //--------------------
        country = (TextView) findViewById(R.id.country);
        city = (TextView) findViewById(R.id.trip);
        days = (TextView) findViewById(R.id.duration);
        price = (TextView) findViewById(R.id.price);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);

        dialog = new AlertDialog.Builder(this).create();
        editText = new EditText(this);

        dialog.setTitle("Edit");
        dialog.setView(editText);

/*
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){
                //country.setText(editText.getText());
                }
        });


       country.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                editText.setText(country.getText());
                dialog.show();
            }
        });*/


        country.setOnClickListener(this);
        city.setOnClickListener(this);
        days.setOnClickListener(this);
        price.setOnClickListener(this);
        date.setOnClickListener(this);
        description.setOnClickListener(this);



      }


    //TextViews auswählen und dialogbox zum Text ändern anzeigen
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.country:
                editText.setText(country.getText());
                dialog.show();
                country.setText(editText.getText());

                break;

            case R.id.trip:
                editText.setText(city.getText());
                dialog.show();
                city.setText(editText.getText());
                break;

            case R.id.duration:
                editText.setText(days.getText());
                dialog.show();
                break;

            case R.id.price:
                editText.setText(price.getText());
                dialog.show();
                break;

            case R.id.date:
                editText.setText(date.getText());
                dialog.show();
                break;

            case R.id.description:
                editText.setText(description.getText());
                dialog.show();
                break;


        }
    }
}
