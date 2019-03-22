package com.example.travelagency.ui.Activities;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelagency.AppDatabase;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.LocationViewModel;

public class addLocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationDetails";
    private Location location;
    private LocationViewModel viewModel;

    String countryName;
    String language;
    String description;
    int inhabitants;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        EditText etcountryName = (EditText) findViewById(R.id.countryname);
        EditText etlanguage = (EditText) findViewById(R.id.language);
        EditText etinhabitant = (EditText) findViewById(R.id.inhabitant);
        EditText etdescription = (EditText) findViewById(R.id.description);
        Button add = findViewById(R.id.btnaddlocation);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"travel_database").allowMainThreadQueries().build();

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                countryName = etcountryName.getText().toString();
                language = etlanguage.getText().toString();
                inhabitants = Integer.valueOf(etinhabitant.getText().toString());
                description = etdescription.getText().toString();

                Location location = new Location(countryName, inhabitants, description, language);

                appDatabase.locationDao().insert(location);
                gotoCountryActivity(v);
            }
        });
    }

    public void gotoCountryActivity (View view){
        Intent intent = new Intent (this, CountryActivity.class);
        startActivity(intent);
    }
}