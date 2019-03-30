package com.example.travelagency.ui.Activities.trip;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.travelagency.Entities.Trip;
import com.example.travelagency.R;
import com.example.travelagency.ui.Activities.SettingsActivity;
import com.example.travelagency.viewmodel.trip.TripListViewModel;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity {

    //Attributs
    private ListView listview;
    private List<Trip> tripList;
    private TripListViewModel viewModel;
    private String countryName;

    //create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_trips, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Actions für Actionbar
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intentHome = new Intent(this, AddTripActivity.class);
                intentHome.putExtra("countryName", countryName);
                startActivity(intentHome);
                return true;

            case R.id.action_settings:
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Trips");

        countryName = getIntent().getStringExtra("countryName");

        //Create listview
        listview = findViewById(R.id.listview);
        tripList = new ArrayList<>();

        //Array adapter
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        TripListViewModel.Factory factory = new TripListViewModel.Factory(getApplication(), countryName);

        //get data from database
        viewModel = ViewModelProviders.of(this, factory).get(TripListViewModel.class);
        viewModel.getTrips().observe(this, tripEntities -> {
            if (tripEntities != null) {
                tripList = tripEntities;
                adapter.clear();
                adapter.addAll(tripList);
            }
        });

        listview.setAdapter(adapter);

        //onclicklistener for listview
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), DetailsTripActivity.class);

                intent.putExtra("tripName", tripList.get(position).getTripname());
                intent.putExtra("countryName", tripList.get(position).getCountryName());
                startActivity(intent);
            }
        });
    }
}
