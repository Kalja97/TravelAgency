package com.example.travelagency.ui.Activities.trip;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.async.Trip.CreateTrip;
import com.example.travelagency.util.OnAsyncEventListener;

public class AddTripActivity extends AppCompatActivity {

    private static final String TAG = "AddTripActivity";

       //Attributs
    private EditText ettripname;
    private EditText etduration;
    private EditText etdate;
    private EditText etprice;
    private EditText etdescription;
    private EditText etImageUrl;
    private RatingBar rbratingbar;

    //Intent infos
    private String countryName;

    //Strings
    private String tripName;
    private String duration;
    private String date;
    private String price;
    private String description;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initializeForm();

        //Button cancel
        Button cancel = findViewById(R.id.btncancel);

        //Button add and call mathod save
        Button saveBtn = findViewById(R.id.btnaddtrip);

        countryName = getIntent().getStringExtra("countryName");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get text from inputs
                tripName = ettripname.getText().toString().trim();
                duration = etduration.getText().toString().trim();
                date = etdate.getText().toString().trim();
                price = etprice.getText().toString().trim();
                description = etdescription.getText().toString().trim();
                imageUrl = etImageUrl.getText().toString().trim();

                //call method for checking if required fields are filled
                if(checkInputField(tripName, duration,date,price,description)){
                    //Call method for setting default picture, if field for image-url is empty
                    setDefaultPicture();

                    //call method for saving trip
                    saveChanges(tripName, duration,date,price,description,imageUrl,rbratingbar.getRating());
                }
                return;

            }
        });

        //cancel inputs and go back to the tripsActivity page
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent(AddTripActivity.this, TripsActivity.class);
                intentCancel.putExtra("countryName", countryName);
                startActivity(intentCancel);
            }
        });
    }

    private void initializeForm() {

        //Initialize editText for input
        ettripname = findViewById(R.id.tripname);
        etduration = findViewById(R.id.duration);
        etdate = findViewById(R.id.date);
        etprice = findViewById(R.id.price);
        etdescription = findViewById(R.id.description);
        etImageUrl = findViewById(R.id.imageUrl);
        rbratingbar = findViewById(R.id.ratingBar);

    }

    private void setDefaultPicture(){
        //check if url is empty or not, if empty set default url
        if(imageUrl.isEmpty()){
            imageUrl = "https://img.buzzfeed.com/buzzfeed-static/static/2014-11/20/2/campaign_images/webdr04/the-22-stages-of-going-on-a-road-trip-in-australia-2-18049-1416470126-1_dblbig.jpg";
        } else {
            imageUrl = etImageUrl.getText().toString().trim();
        }
    }

    private boolean checkInputField(String tripName, String duration, String date, String price, String description){
        //Check if all filed are filled in
        if(tripName.isEmpty() || duration.isEmpty() || date.isEmpty() || price.isEmpty() || description.isEmpty()){
            final AlertDialog alertDialog = new AlertDialog.Builder(AddTripActivity.this).create();
            alertDialog.setTitle("Not all fields filled in");
            alertDialog.setCancelable(true);
            alertDialog.setMessage("Please fill in all fields first");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ok", (dialog, which) -> alertDialog.dismiss());
            alertDialog.show();
            return false;
        }
        return true;
    }



    //Method for saving
    private void saveChanges(String tripname, String duration, String date, String price, String description, String imageUrl, float rating) {

        //create new trip
        Trip newTrip = new Trip(countryName, tripname, duration, date, price, description, imageUrl, rating);

        //add trip
        new CreateTrip(getApplication(), new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "createTrip: success");
                Intent intent = new Intent(AddTripActivity.this, TripsActivity.class);
                intent.putExtra("countryName", countryName);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createTrip: failure", e);
            }
        }).execute(newTrip);
    }
}
