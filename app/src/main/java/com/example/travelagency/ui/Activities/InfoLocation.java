package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.travelagency.Entities.Location;
import com.example.travelagency.R;
import com.example.travelagency.Repository.LocationRepository;
import com.example.travelagency.viewmodel.LocationViewModel;

public class InfoLocation extends AppCompatActivity {

    private TextView tvCountryName;
    private TextView tvLanguage;
    private TextView tvInhabitant;
    private TextView tvDescription;

    private Location location;
    private LocationRepository repository;
    private LocationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_location);

        String loc = getIntent().getStringExtra("countryName");

        initiateView();

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

    private void initiateView() {
        tvCountryName = findViewById(R.id.countryname);
        tvLanguage = findViewById(R.id.language);
        tvInhabitant = findViewById(R.id.inhabitants);
        tvDescription = findViewById(R.id.description);
    }

    private void updateContent() {
        if (location != null) {
            tvCountryName.setText(location.getCountryName());
            tvLanguage.setText(location.getLanguage());
            tvInhabitant.setText(String.valueOf(location.getInhabitants()));
            tvDescription.setText(location.getDescription());
        }
    }
}
