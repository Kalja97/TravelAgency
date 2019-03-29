package com.example.travelagency.ui.Activities.location;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.ui.Activities.SettingsActivity;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.location.LocationViewModel;

public class EditLocationActivity extends AppCompatActivity {

    private static final String TAG = "EditLocationActivity";

    //Edit texts of the layout
    private EditText etLanguage;
    private EditText etInhabitants;
    private EditText etDescription;
    private EditText etCountryname;

    //location attributes
    private String countryname;
    private String language;
    private String description;
    private String  inhabitant;
    private int inhabitants;

    private LocationViewModel viewModel;
    private Location location;
    private LocationRepository repository;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location);

        String loc = getIntent().getStringExtra("countryName");

        initiateView();

        //initialize repositoty
        repository = new LocationRepository();

        //Get data from database
        LocationViewModel.Factory factory = new LocationViewModel.Factory(getApplication(),loc, repository);
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel.class);
        viewModel.getLocation().observe(this, locationEntity -> {
            if (locationEntity != null) {
                location = locationEntity;
                updateContent();
            }
        });
    }

    //call method saveChanges for saving changes in the DB
    private void saving(){

        //Get text from inputs
        language = etLanguage.getText().toString().trim();
        inhabitant = etInhabitants.getText().toString().trim();
        description = etDescription.getText().toString().trim();

        //Check if all filed are filled in
        if(language.isEmpty() || inhabitant.isEmpty() || description.isEmpty()){
            final AlertDialog alertDialog = new AlertDialog.Builder(EditLocationActivity.this).create();
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

        if(!etInhabitants.getText().toString().trim().matches(regexStr))
        {
            etInhabitants.setError(getString(R.string.error_invalid_inhabitant));
            etInhabitants.requestFocus();
            return;
        }

        inhabitants = Integer.parseInt(inhabitant);

        //call method saveChanges
        saveChanges(countryname, inhabitants, language, description);

    }

    //Saving changes into database
    private void saveChanges(String countryname, int inhabitants, String language, String description) {
        location.setCountryName(countryname);
        location.setInhabitants(inhabitants);
        location.setLanguage(language);
        location.setDescription(description);

        viewModel.updateLocation(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "updateLocation: success");
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "updateLocation: failure", e);
            }
        });
        backToMain();
    }

    //Delete location
    private void delete(){
        viewModel.deleteLocation(location, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Delete trip: success");
                backToMain();
            }

            @Override
            public void onFailure(Exception e) {}
        });
    }

    //create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions for actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                saving();
                return true;
            case R.id.action_delete:
                delete();
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //to start the intent -> back to main
    public void backToMain(){
        Intent intentTrip = new Intent(this, LocationsActivity.class);
        startActivity(intentTrip);
    }

    //initialize the edit texts
    private void initiateView() {
        etCountryname = findViewById(R.id.change_countryname);
        etLanguage = findViewById(R.id.change_language);
        etInhabitants = findViewById(R.id.change_inhabitants);
        etDescription = findViewById(R.id.change_description);

        etCountryname.setEnabled(false);
    }

    //update text in the view
    private void updateContent() {
        if (location != null) {
            etCountryname.setText(location.getCountryName());
            etLanguage.setText(location.getLanguage());
            etInhabitants.setText(String.valueOf(location.getInhabitants()));
            etDescription.setText(location.getDescription());
        }
    }
}
