package com.example.travelagency.ui.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.TripViewModel;

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

    private Trip trip;
    private TripViewModel viewModel;

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

                }
        });



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

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        country.setText(editText.getText());
                    }
                });


                break;

            case R.id.trip:
                editText.setText(city.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        city.setText(editText.getText());
                    }
                });
                break;

            case R.id.duration:
                editText.setText(days.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        days.setText(editText.getText());
                    }
                });
                break;

            case R.id.price:
                editText.setText(price.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        price.setText(editText.getText());
                    }
                });
                break;

            case R.id.date:
                editText.setText(date.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        date.setText(editText.getText());
                    }
                });
                break;

            case R.id.description:
                editText.setText(description.getText());
                dialog.show();

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Save", new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int i){
                        description.setText(editText.getText());

                    }
                });
                break;


        }

        String countryName = (String) country.getText();
        String trip = (String) city.getText();
        String day = (String) days.getText();
        String cost = (String) price.getText();
        String datum = (String) date.getText();
        String desc = (String) description.getText();

        saveChanges(countryName,trip, day, cost, datum, desc);
    }

    private void saveChanges(String country, String city, String days, String price, String date, String description) {

        trip.setCountryName(country);
        trip.setTripname(city);
        trip.setDuration(days);
        trip.setPrice(price);
        trip.setDate(date);
        trip.setDescription(description);

        viewModel.updateTrip(trip, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
//                setResponse(true);
            }

            @Override
            public void onFailure(Exception e) {
//                setResponse(false);
            }
        });
    }
}
