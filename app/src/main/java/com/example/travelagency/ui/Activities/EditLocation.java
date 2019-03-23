package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.viewmodel.LocationViewModel;

public class EditLocation extends AppCompatActivity {

    private EditText etLanguage;
    private EditText etInhabitants;
    private EditText etDescription;

    private LocationViewModel viewModel;

    private Location location;

    private LocationRepository repository;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_locations, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
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
            etInhabitants.setText(location.getInhabitants());
            etDescription.setText(location.getDescription());
        }
    }

}
