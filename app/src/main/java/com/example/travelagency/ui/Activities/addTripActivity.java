package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.travelagency.AppDatabase;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.async.CreateTrip;
import com.example.travelagency.util.OnAsyncEventListener;

public class addTripActivity extends AppCompatActivity {

    private static final String TAG = "addTripActivity";

    private Toast toast;

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

        //Buttons
        Button cancel = findViewById(R.id.btncancel);

        countryName = getIntent().getStringExtra("countryName");

        //cancel inputs and go back to the tripsActivity page
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent(addTripActivity.this, Trips2Activity.class);
                intentCancel.putExtra("countryName", countryName);
                startActivity(intentCancel);
            }
        });

        //Show toast
        toast = Toast.makeText(this, "new Trip in DataBase", Toast.LENGTH_LONG);
    }

    private void initializeForm() {

        ettripname = findViewById(R.id.tripname);
        etduration = findViewById(R.id.duration);
        etdate = findViewById(R.id.date);
        etprice = findViewById(R.id.price);
        etdescription = findViewById(R.id.description);
        etImageUrl = findViewById(R.id.imageUrl);
        rbratingbar = findViewById(R.id.ratingBar);

        //Save button and onclicklistener

        if(etImageUrl.getText().toString().equalsIgnoreCase("")){
            imageUrl = "https://img.buzzfeed.com/buzzfeed-static/static/2014-11/20/2/campaign_images/webdr04/the-22-stages-of-going-on-a-road-trip-in-australia-2-18049-1416470126-1_dblbig.jpg";
        } else {
            imageUrl = etImageUrl.getText().toString();
        }

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

        Trip newTrip = new Trip(countryName, tripname, duration, date, price, description, imageUrl, rating);

        new CreateTrip(getApplication(), new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "createTrip: success");
                Intent intent = new Intent(addTripActivity.this, Trips2Activity.class);
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
