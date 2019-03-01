package com.example.travelagency;

import android.content.DialogInterface;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TripActivityEdit extends AppCompatActivity {

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


        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int i){
                country.setText(editText.getText());
                }
        });

       country.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                editText.setText(country.getText());
                dialog.show();
            }
        });





    }

}
