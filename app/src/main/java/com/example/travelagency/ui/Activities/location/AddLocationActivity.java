package com.example.travelagency.ui.Activities.location;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.travelagency.AppDatabase;
import com.example.travelagency.Entities.Location;
import com.example.travelagency.Entities.LocationF;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepositoryF;
import com.example.travelagency.async.Location.CreateLocation;
import com.example.travelagency.ui.Activities.trip.DetailsTripActivity;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.location.LocationViewModelF;

public class AddLocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationDetails";

    private LocationViewModelF viewModel;
    private LocationRepositoryF repository;

    //Attributs
    String countryName;
    String language;
    String description;
    int inhabitants;
    String inhabitant;

    AppDatabase appDatabase;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize viewmodel
        repository = new LocationRepositoryF();
        String loc = "";
        LocationViewModelF.Factory factory = new LocationViewModelF.Factory(getApplication(), loc, repository);
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModelF.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        //change title in actionbar
        setTitle("Add country");

        //Initialize editText for input
        EditText etcountryName = (EditText) findViewById(R.id.countryname);
        EditText etlanguage = (EditText) findViewById(R.id.language);
        EditText etinhabitant = (EditText) findViewById(R.id.inhabitant);
        EditText etdescription = (EditText) findViewById(R.id.description);

        //Buttons
        Button add = findViewById(R.id.btnaddlocation);
        Button cancel = findViewById(R.id.btncancel);

        //cancel inputs and go back to the mainpage
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCancel = new Intent(AddLocationActivity.this, LocationsActivity.class);
                startActivity(intentCancel);
            }
        });

        //Database builder
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"travel_database").allowMainThreadQueries().build();

        //Add location to the database
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Get text from inputs
                countryName = etcountryName.getText().toString().trim();
                language = etlanguage.getText().toString().trim();
                inhabitant = etinhabitant.getText().toString().trim();
                description = etdescription.getText().toString().trim();

                //Check if all filed are filled in
                if(countryName.isEmpty() || language.isEmpty() || inhabitant.isEmpty() || description.isEmpty()){
                    final AlertDialog alertDialog = new AlertDialog.Builder(AddLocationActivity.this).create();
                    alertDialog.setTitle("Not all fields filled in");
                    alertDialog.setCancelable(true);
                    alertDialog.setMessage("Please fill in all fields first");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ok", (dialog, which) -> alertDialog.dismiss());
                    alertDialog.show();
                    return;
                }

                //Check if inhabitant input is a number
                //if not set error
                String regexStr = "^[0-9]*$";

                if(!etinhabitant.getText().toString().trim().matches(regexStr))
                {
                    etinhabitant.setError(getString(R.string.error_invalid_inhabitant));
                    etinhabitant.requestFocus();
                    return;
                }

                //cast String in int
                inhabitants = Integer.valueOf(etinhabitant.getText().toString().trim());

                //create new location object
                LocationF location = new LocationF();
                location.setCountryName(countryName);
                location.setInhabitants(inhabitants);
                location.setDescription(description);
                location.setLanguage(language);

                //add to firebase
                viewModel.createLocation(location, new OnAsyncEventListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "create location: success");
                        Intent intent = new Intent(AddLocationActivity.this, LocationsActivity.class);

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d(TAG, "create location: failure", e);
                        final AlertDialog alertDialog = new AlertDialog.Builder(AddLocationActivity.this).create();
                        alertDialog.setTitle("Can not save");
                        alertDialog.setCancelable(true);
                        alertDialog.setMessage("This Countryname is already in the Database");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ok", (dialog, which) -> alertDialog.dismiss());
                        alertDialog.show();
                    }
                });
            }
        });
    }
}