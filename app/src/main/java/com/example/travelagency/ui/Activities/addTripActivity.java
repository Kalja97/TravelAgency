package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        initializeForm();
        toast = Toast.makeText(this, "new Trip in DataBase", Toast.LENGTH_LONG);
    }

    private void initializeForm() {

        ettripname = findViewById(R.id.tripname);
        etduration = findViewById(R.id.duration);
        etdate = findViewById(R.id.date);
        etprice = findViewById(R.id.price);
        etdescription = findViewById(R.id.description);
        etImageUrl = findViewById(R.id.imageUrl);
        Button saveBtn = findViewById(R.id.btnaddtrip);
        saveBtn.setOnClickListener(view -> saveChanges(
                ettripname.getText().toString(),
                etduration.getText().toString(),
                etdate.getText().toString(),
                etprice.getText().toString(),
                etdescription.getText().toString(),
                etImageUrl.getText().toString()
        ));
    }

    private void saveChanges(String tripname, String duration, String date, String price, String description, String imageUrl) {

        String countryName = getIntent().getStringExtra("countryName");
        Trip newTrip = new Trip(countryName, tripname, duration, date, price, description, imageUrl);

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
