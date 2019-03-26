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

    private TextView tvLanguage;
    private TextView tvInhabitant;
    private EditText tvDescription;



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
        tvLanguage = findViewById(R.id.languages);
        tvInhabitant = findViewById(R.id.inhabitant);
        tvDescription = findViewById(R.id.text_field);


    }

    private void updateContent() {
        if (location != null) {
            tvLanguage.setText(location.getLanguage());
            tvInhabitant.setText(String.valueOf(location.getInhabitants()));
            tvDescription.setText(location.getDescription());
        }
    }
}
