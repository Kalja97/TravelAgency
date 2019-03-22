package com.example.travelagency.ui.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelagency.AppDatabase;
import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.async.CreateTrip;
import com.example.travelagency.util.OnAsyncEventListener;

public class addTripActivity extends AppCompatActivity {

    private static final String TAG = "addTripActivity";


    private EditText ettripname;
    private EditText etduration;
    private EditText etdate;
    private EditText etprice;
    private EditText etdescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        initializeForm();
    }

    private void initializeForm() {

        ettripname = findViewById(R.id.tripname);
        etduration = findViewById(R.id.duration);
        etdate = findViewById(R.id.date);
        etprice = findViewById(R.id.price);
        etdescription = findViewById(R.id.description);
        Button saveBtn = findViewById(R.id.btnaddtrip);
        saveBtn.setOnClickListener(view -> saveChanges(
                ettripname.getText().toString(),
                etduration.getText().toString(),
                etdate.getText().toString(),
                etprice.getText().toString(),
                etdescription.getText().toString()
        ));
    }

    private void saveChanges(String tripname, String duration, String date, String price, String description) {

        Trip newTrip = new Trip("Deutschland", tripname, duration, date, price, description);

        new CreateTrip(getApplication(), new OnAsyncEventListener() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "createTrip: success");
                Intent intent = new Intent(addTripActivity.this, Trips2Activity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createTrip: failure", e);
            }
        }).execute(newTrip);
    }


}
