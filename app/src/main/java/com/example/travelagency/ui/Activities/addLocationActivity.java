package com.example.travelagency.ui.Activities;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelagency.AppDatabase;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.LocationViewModel;

public class addLocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationDetails";
    private Location location;
    private LocationViewModel viewModel;

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

         EditText countryName = (EditText) findViewById(R.id.countryname);
         EditText language = (EditText) findViewById(R.id.language);
         EditText inhabitant = (EditText) findViewById(R.id.inhabitant);
         EditText description = (EditText) findViewById(R.id.description);

        //String countryname = countryName.getText().toString();
        //String lang = language.getText().toString();
        //int inhab = Integer.parseInt(inhabitant.getText().toString());
        //String desc = description.getText().toString();



        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"Location").allowMainThreadQueries().build();
        Button add = findViewById(R.id.btnaddlocation);
        add.setOnClickListener(view ->{
            createLocation(
                    countryName.getText().toString(),
                    language.getText().toString(),
                    Integer.parseInt(inhabitant.getText().toString()),
                    description.getText().toString()
            );
        });


    }

    private void createLocation(String countryName, String language,
                                int inhabitant, String description) {

        //location = new Location(countryName, inhabitant, language, description);
        location = new Location();
        location.setCountryName(countryName);
        location.setInhabitants(inhabitant);
        location.setLanguage(language);
        location.setDescription(description);

        viewModel.createLocation(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createLocation: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createLocation: failure", e);
            }
        });
    }



}
