package com.example.travelagency.ui.Activities.trip;

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

    private String countryName;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        initializeForm();

        //Button cancel
        Button cancel = findViewById(R.id.btncancel);

        countryName = getIntent().getStringExtra("countryName");

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

        //check if url is empty or not, if empty set default url
        if(etImageUrl.getText().toString().equalsIgnoreCase("")){
            imageUrl = "https://img.buzzfeed.com/buzzfeed-static/static/2014-11/20/2/campaign_images/webdr04/the-22-stages-of-going-on-a-road-trip-in-australia-2-18049-1416470126-1_dblbig.jpg";
        } else {
            imageUrl = etImageUrl.getText().toString();
        }

        //Button add and call mathod save
        Button saveBtn = findViewById(R.id.btnaddtrip);
        saveBtn.setOnClickListener(view -> saveChanges(
                ettripname.getText().toString(),
                etduration.getText().toString(),
                etdate.getText().toString(),
                etprice.getText().toString(),
                etdescription.getText().toString(),
                imageUrl,
                rbratingbar.getRating()
        ));
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
