package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.util.OnAsyncEventListener;
import com.example.travelagency.viewmodel.LocationViewModel;

public class EditLocation extends AppCompatActivity {

    private static final String TAG = "EditLocation";

    private EditText etLanguage;
    private EditText etInhabitants;
    private EditText etDescription;

    private LocationViewModel viewModel;
    private Location location;
    private LocationRepository repository;

    private String language;
    private String description;
    private String  inhabitant;
    private int inhabitants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_location);

        String loc = getIntent().getStringExtra("countryName");

        initiateView();

        repository = new LocationRepository();

        LocationViewModel.Factory factory = new LocationViewModel.Factory(getApplication(),loc, repository);
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel.class);
        viewModel.getLocation().observe(this, locationEntity -> {
            if (locationEntity != null) {
                location = locationEntity;
                updateContent();
            }
        });
    }

    private void saving(){
        inhabitant = "" + etInhabitants.getText();
        language = "" + etLanguage.getText();
        description = "" + etDescription.getText();
        inhabitants = Integer.parseInt(inhabitant);

        saveChanges(inhabitants,language, description);
    }

    private void saveChanges(int inhabitants, String language, String description) {

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_save:
                saving();
                Intent intentTrip = new Intent(this, MainActivity.class);
                startActivity(intentTrip);
                return true;
            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initiateView() {
        etLanguage = findViewById(R.id.change_language);
        etInhabitants = findViewById(R.id.change_inhabitants);
        etDescription = findViewById(R.id.change_description);
    }

    private void updateContent() {
        if (location != null) {
            etLanguage.setText(location.getLanguage());
            etInhabitants.setText(String.valueOf(location.getInhabitants()));
            etDescription.setText(location.getDescription());
        }
    }
}
