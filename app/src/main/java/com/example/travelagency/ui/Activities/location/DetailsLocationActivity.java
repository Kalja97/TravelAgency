package com.example.travelagency.ui.Activities.location;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.viewmodel.location.LocationViewModel;

public class DetailsLocationActivity extends AppCompatActivity {

    //Text views of the layout
    private TextView tvCountryName;
    private TextView tvLanguage;
    private TextView tvInhabitant;
    private TextView tvDescription;

    //other attributs
    private Location location;
    private LocationRepository repository;
    private LocationViewModel viewModel;

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_location);

        //countryname from the list view clicker
        String loc = getIntent().getStringExtra("countryName");

        initiateView();

        //initialize repositoty
        repository = new LocationRepository();

        //Get data from database
        LocationViewModel.Factory factory = new LocationViewModel.Factory(getApplication(), loc, repository);
        viewModel = ViewModelProviders.of(this, factory).get(LocationViewModel.class);
        viewModel.getLocation().observe(this, locationEntity -> {
            if (locationEntity != null) {
                location = locationEntity;
                updateContent();
            }
        });
    }

    //initialize the text views
    private void initiateView() {
        tvCountryName = findViewById(R.id.countryname);
        tvLanguage = findViewById(R.id.language);
        tvInhabitant = findViewById(R.id.inhabitants);
        tvDescription = findViewById(R.id.description);
    }

    //update text in the view
    private void updateContent() {
        if (location != null) {
            tvCountryName.setText(location.getCountryName());
            tvLanguage.setText(location.getLanguage());
            tvInhabitant.setText(String.valueOf(location.getInhabitants()));
            tvDescription.setText(location.getDescription());
        }
    }
}
