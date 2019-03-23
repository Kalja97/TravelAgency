package com.example.travelagency.ui.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.example.travelagency.viewmodel.TripListViewModel;

import java.util.ArrayList;
import java.util.List;

public class Trips2Activity extends AppCompatActivity {

    private ListView listview;
    private List<Trip> tripList;
    private TripListViewModel viewModel;
    private String countryName;

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
                Intent intentHome = new Intent(this, addTripActivity.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryName = getIntent().getStringExtra("countryName");

        /*
            listview = (ListView) findViewById(R.id.listview);
            String[] shows = getResources().getStringArray(R.array.shows_array);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, shows);
            listview.setAdapter(adapter);
        */

        listview = findViewById(R.id.listview);
        tripList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        TripListViewModel.Factory factory = new TripListViewModel.Factory(getApplication(), countryName);

        viewModel = ViewModelProviders.of(this, factory).get(TripListViewModel.class);
        viewModel.getTrips().observe(this, showEntities -> {
            if (showEntities != null) {
                tripList = showEntities;
                adapter.addAll(tripList);
            }
        });

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), TripActivity.class);
        /*
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                        Intent.FLAG_ACTIVITY_NO_HISTORY
                );
        */
                intent.putExtra("tripName", tripList.get(position).getTripname());
                intent.putExtra("countryName", tripList.get(position).getCountryName());
                startActivity(intent);
            }
        });
    }
}
